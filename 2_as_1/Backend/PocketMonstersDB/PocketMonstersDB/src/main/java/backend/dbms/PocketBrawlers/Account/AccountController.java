package backend.dbms.PocketBrawlers.Account;

import java.util.List;

import backend.dbms.PocketBrawlers.Brawler.Brawler;
import backend.dbms.PocketBrawlers.Brawler.User_Brawlers.User_Brawlers;
import backend.dbms.PocketBrawlers.Leagues.League;
import backend.dbms.PocketBrawlers.Leagues.LeagueRepository;
import backend.dbms.PocketBrawlers.Services.AccountLeagueService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;
import backend.dbms.PocketBrawlers.Account.Account_League;
import backend.dbms.PocketBrawlers.Account.AccountLeagueRepository;

import static java.lang.Integer.parseInt;

/**
 * Controller for account routing
 *
 * @author Reid Coates
 */
@RestController
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountLeagueRepository accountLeagueRepository;

    @Autowired
    LeagueRepository leagueRepository;

    private final AccountLeagueService accountLeagueService;

    public AccountController(AccountLeagueService accountLeagueService) {
        this.accountLeagueService = accountLeagueService;
    }

    @GetMapping(value = "/formattedBrawlers/{id}")
    public JSONObject formattedBrawlers(@PathVariable int id){
        JSONObject temp = new JSONObject();

        Account a = accountRepository.findById(id);

        List<User_Brawlers> b = a.getBrawlers();

        for(int i = 0; i < b.size(); i++){
            String t = "b" + i;
            for(int j = 0; j < b.size(); ++j) {
                if (b.get(j).getBrawler_pos() == i) {
                    temp.put(t, b.get(i).toJSONObject());
                }
            }
        }

        temp.put("count", b.size());

        return temp;
    }

    @GetMapping(value = "/getAllData")
    public List<Account> getAllData() {
        return accountRepository.findAll();
    }

    /**
     * Function to validate if an account is inside the MySql database.
     *
     * @param a The {@link Account} taken from the JSON body of the response.
     * @return A JSON body if the user is found in the database, otherwise null.
     */
    @PostMapping(value = "/authorizeAccount")
    public JSONObject authorizeAccount(@RequestBody Account a) {
        List<Account> templist = accountRepository.findByUsername(a.getUsername());
        if (templist.size() > 0) {
            Account u = templist.get(0);
            if (u.getPassword().equals(a.getPassword())) {
                return u.toJSONObject();
            }
        }
        return null;
    }

    /**
     * Deletes all data in repository.
     */
    @GetMapping(value = "/deleteAllData")
    public void deleteAllData() {
        accountRepository.deleteAll();
    }

    /**
     * Creates an account with a default user type of 0 if none is specified.
     *
     * @param a Account object from Request.
     * @return A copy of the Account object for verification.
     */
    @PostMapping(value = "/createAccount")
    public JSONObject saveUser(@RequestBody Account a) {
        accountRepository.save(a);
        return accountRepository.save(a).toJSONObject();
    }

    @PutMapping(value = "/changePassword/")
    public JSONObject changePW(@RequestBody JSONObject temp) throws JSONException {
        String id = (String) temp.get("id");
        String newPassword = (String) temp.get("newPassword");
        Account a = accountRepository.findById(parseInt(id));
        a.setPassword(newPassword);
        accountRepository.save(a);
        JSONObject tempRet = new JSONObject();
        tempRet.put("newPassword", newPassword);

        return tempRet;
    }

    @PostMapping(value = "/requestLeagueOrganizer/{id}")
    public void requestLeague(@PathVariable int id){
        Account a = accountRepository.findById(id);
        Account_League temp = new Account_League(id, a.getUsername());
        accountLeagueRepository.save(temp);
    }

    @GetMapping(value = "/getAllLeagueRequests")
    public List<Account_League> getAllLeagueRequests(){
        return accountLeagueRepository.findAll();
    }

    @PostMapping(value = "/leagueRequestResponse/{id}/{choice}")
    public void leageRQ(@PathVariable int id, @PathVariable boolean choice){
        Account a = accountRepository.findById(id);
        String user = a.getUsername();
        accountLeagueRepository.delete(accountLeagueRepository.findByUsername(user).get(0));
        if (choice){
            League newLeague = new League("myLeague" + id, id);
            newLeague.addAccount(a);
            a.setType(1);
            leagueRepository.save(newLeague);
            accountRepository.save(a);
        }
    }
}
