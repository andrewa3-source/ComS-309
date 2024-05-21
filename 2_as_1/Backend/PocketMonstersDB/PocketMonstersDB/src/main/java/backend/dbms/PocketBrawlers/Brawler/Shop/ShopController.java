package backend.dbms.PocketBrawlers.Brawler.Shop;
import java.util.List;

import backend.dbms.PocketBrawlers.Account.Account;
import backend.dbms.PocketBrawlers.Account.AccountRepository;
import backend.dbms.PocketBrawlers.Brawler.Brawler;
import backend.dbms.PocketBrawlers.Brawler.BrawlerRepository;
import backend.dbms.PocketBrawlers.Brawler.User_Brawlers.UserRepository;
import backend.dbms.PocketBrawlers.Brawler.User_Brawlers.User_Brawlers;
import backend.dbms.PocketBrawlers.Services.ShopService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;

/**
 * @author Andrew Ahrenkiel
 */
@RestController
public class ShopController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BrawlerRepository brawlerRepository;

    @Autowired
    UserRepository userRepository;

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    /**
     * Get all brawlers that are currently stored in the table
     *
     * @return List of Brawlers
     */
    @GetMapping(value = "/getAllBrawlerData")
    public List<Brawler> getAllData() {
        return brawlerRepository.findAll();
    }


    /**
     * Add a brawler to the table
     * Subject to change
     *
     * @param b - JsonObject of a brawler using the brawler constructor information
     */
    @PostMapping(value = "/addBrawler")
    public void addBrawlerAt(@RequestBody Brawler b) {
        brawlerRepository.save(b);
    }

    /**
     * Return a JSON containing the needed information for shop brawlers
     * Returns a brawler acceptable with the current users turn
     *
     * @param id - current players account id
     * @return - JSON of brawler information
     */
    @GetMapping(value = "/getShopBrawlerWithTurn/{id}")
    public JSONObject getShopBrawlersBasedOnTurn(@PathVariable int id) {
        Brawler b = shopService.shopRandomizeWithTurn(id);

        JSONObject temp = new JSONObject();
        temp.put("id", b.getId());
        temp.put("health", b.getHealth());
        temp.put("damage", b.getDamage());
        temp.put("position", b.getPos());
        temp.put("a_time", b.getA_time());
        temp.put("a_id", b.getAttributeId());
        temp.put("url", b.getPircture_url());
        return temp;
    }

    /**
     * Get a random brawler from the repository
     *
     * @return - JSON of brawler information
     */
    @GetMapping(value = "/getShopBrawler")
    public JSONObject getShopBrawler() {
        Brawler b = shopService.shopRandomize();

        JSONObject temp = new JSONObject();
        temp.put("id", b.getId());
        temp.put("health", b.getHealth());
        temp.put("damage", b.getDamage());
        temp.put("position", b.getPos());
        temp.put("a_time", b.getA_time());
        temp.put("a_id", b.getAttributeId());
        temp.put("url", b.getPircture_url());
        return temp;
    }

    /**
     * Set the team line up for the given account id, stored in the user brawler table and used for multiplayer battle
     * @param j JSONObject array for the given team brawlers
     * @param id account id
     * @throws CloneNotSupportedException
     */
    @PostMapping(value = "/setTeam/{id}")
    public void setTeam(@RequestBody JSONObject[] j, @PathVariable int id) throws CloneNotSupportedException {
        Account a = accountRepository.findById(id);

        //remove current user_brawlers from table and account list
        shopService.removeTeamBrawlers(a);

        //Get all information from JSON and put into table and user lists
        for (int i = 0; i < j.length; ++i) {
            Brawler b = brawlerRepository.findById((int) j[i].get("id")).get();

            User_Brawlers temp = new User_Brawlers(a, b, (int) j[i].get("damage"), (int) j[i].get("health"), (int) j[i].get("position"));
            shopService.saveUserBrawlerEverywhere(temp, a, b);
        }
        accountRepository.save(a);
    }


    /**
     * Unformatted way to get all team information for any given account
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/getTeam/{id}")
    public List<User_Brawlers> getTeamfromId(@PathVariable int id) {
        Account a = accountRepository.findById(id);
        return a.getBrawlers();
    }

    /**
     * Used to get a JSONObject[] of the given account id's.
     * This should be what is used for battle calculations for both the given and enemy team.
     *
     * @param id - account id
     * @return JSONObject[] including id, position, damage, health, attribute time, and attribute id for each brawler on the team.
     */
    @GetMapping(value = "/getFormattedTeam/{id}")
    public JSONObject[] getFormattedTeam(@PathVariable int id) {
        Account a = accountRepository.findById(id);
        List<User_Brawlers> temp = a.getBrawlers();
        JSONObject[] team = new JSONObject[temp.size()];
        for (int i = 0; i < temp.size(); ++i) {
            team[i] = new JSONObject();
        }

        for (int i = 0; i < temp.size(); ++i) {

            team[i].put("id", temp.get(i).getBrawler().getId());
            team[i].put("position", temp.get(i).getBrawler_pos());
            team[i].put("damage", temp.get(i).getBrawler_dmg());
            team[i].put("health", temp.get(i).getBrawler_hp());
            team[i].put("a_time", temp.get(i).getBrawler().getA_time());
            team[i].put("a_id", temp.get(i).getBrawler().getAttributeId());
            team[i].put("url", temp.get(i).getBrawler().getPircture_url());

        }
        return team;
    }

    /**
     * Set the Brawler url picture for given brawler id
     *
     * @param j - {"id" : 1 , "url" : "imgur link"} = set brawler with id 1's picture url to the new given link
     * @return
     */
    @PostMapping(value = "/seturl")
    public Brawler seturl(@RequestBody JSONObject j) {

        int id = (int) j.get("id");
        String url = (String) j.get("url");
        Brawler b = brawlerRepository.findById(id).get();
        b.setPircture_url(url);
        brawlerRepository.save(b);

        return b;
    }

    /**
     * Increment the turn count and win or loss for the given account id
     * @param id account id
     * @param result win, loss, or tie
     */
    @PostMapping(value = "/loopTurn/{id}/{result}")
    public void loopTurn(@PathVariable int id, @PathVariable String result){
        Account a = accountRepository.findById(id);
        if(result.equals("win")){
            a.setTurn(a.getTurn() + 1);
            a.setWins(a.getWins() + 1);
        }
        else if (result.equals("loss")){
            a.setTurn(a.getTurn() + 1);
            a.setLoss(a.getLoss() + 1);
        }
        else{
            a.setTurn(a.getTurn() + 1);
        }

        accountRepository.save(a);
    }


    /**
     * Reset the turn count to 1 for the given account id
     * @param id account id
     */
    @PostMapping(value = "/resetTurn/{id}")
    public void resetTurn(@PathVariable int id){
        Account a = accountRepository.findById(id);
        a.setTurn(1);
        accountRepository.save(a);
    }

}

