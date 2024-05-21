package backend.dbms.PocketBrawlers.CPU;
import java.util.List;

import backend.dbms.PocketBrawlers.Account.Account;
import backend.dbms.PocketBrawlers.Account.AccountRepository;
import backend.dbms.PocketBrawlers.Brawler.BrawlerRepository;
import backend.dbms.PocketBrawlers.Brawler.Brawler;
import backend.dbms.PocketBrawlers.Services.AccountLeagueService;
import backend.dbms.PocketBrawlers.Services.CPUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONObject;

/**
 * @author Reid Coates
 */
@RestController
public class CPUController{

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    BrawlerRepository brawlerRepository;

    @Autowired
    CPUTeamsRepository cpuTeamsRepository;

    private final CPUService cpuService;

    public CPUController(CPUService cpuService) {
        this.cpuService = cpuService;
    }

    @GetMapping(value = "generateCPU/{account_id}")
    public JSONObject generateCPU(@PathVariable int account_id) {
        Account a = accountRepository.findById(account_id);

        // new cpu
        CPU cpu = cpuService.createCPU(a.getTurn(), a.getUsername());

        JSONObject temp = new JSONObject();

        // new format
        for(int i = 0; i < cpu.cpuBrawlers.size(); i++){
            String t = "b" + i;
            for(int j = 0; j < cpu.cpuBrawlers.size(); ++j) {
                if (cpu.cpuBrawlers.get(j).getBrawler_pos() == i) {
                    temp.put(t, cpu.cpuBrawlers.get(i).toJSONObject());
                }
            }
        }
        temp.put("count", cpu.cpuBrawlers.size());
        return temp;
    }
//test

//    @GetMapping(value = "/getShopBrawler")
//    public JSONObject getShopBrawler() {
//        Brawler b = new Brawler();
//        Shop p = new Shop();
//        b = p.shopRandomize(brawlerRepository);
//
//        JSONObject temp = new JSONObject();
//        temp.put("id", b.getId());
//        temp.put("health", b.getHealth());
//        temp.put("damage", b.getDamage());
//        temp.put("position", b.getPos());
//        return temp;
//    }

//    @PostMapping(value = "/setTeam/{id}")
//    public void setTeam(@RequestBody JSONObject[] j, @PathVariable int id) throws CloneNotSupportedException {
//        Account a = accountRepository.findById(id).get();
//        if (!a.getBrawlers().isEmpty()) {
//            for(int i = 0; i < a.getBrawlers().size(); ++i){
//                userRepository.delete(a.getBrawlers().get(i));
//            }
//        }
//
//        if (!a.getBrawlers().isEmpty()) {
//            a.getBrawlers().removeAll(a.getBrawlers());
//        }
//
//        for (int i = 0; i < j.length; ++i){
//            Brawler b = brawlerRepository.findById((int) j[i].get("id")).get();
//
//            User_Brawlers temp = new User_Brawlers(a, b, (int) j[i].get("damage"), (int) j[i].get("health"), (int) j[i].get("position"));
//            userRepository.save(temp);
//            a.getBrawlers().add(temp);
//            b.getAccounts().add(temp);
//        }
//
//        cpuRepository.save(a);
//    }
//
//
//    @GetMapping(value = "/getTeam/{id}")
//    public List<CPUBrawler> getTeamfromId(@PathVariable int id){
//        CPU c = cpuRepository.findById(id).get(0);
//
//        return c.getBrawlers();
//    }
}

