package backend.dbms.PocketBrawlers.Services;

import backend.dbms.PocketBrawlers.Account.Account;
import backend.dbms.PocketBrawlers.Brawler.Brawler;
import backend.dbms.PocketBrawlers.Account.AccountRepository;
import backend.dbms.PocketBrawlers.Brawler.BrawlerRepository;
import backend.dbms.PocketBrawlers.Brawler.User_Brawlers.User_Brawlers;
import backend.dbms.PocketBrawlers.CPU.CPU;
import backend.dbms.PocketBrawlers.CPU.CPUBrawler;
import backend.dbms.PocketBrawlers.CPU.CPURepository;
import backend.dbms.PocketBrawlers.CPU.CPUTeamsRepository;
import backend.dbms.PocketBrawlers.Leagues.League;
import backend.dbms.PocketBrawlers.Leagues.LeagueRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.catalina.User;
import org.apiguardian.api.API;
import org.jetbrains.annotations.NotNull;
import org.json.HTTP;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Service for CPU
 *
 * @author Reid Coates
 */
@Service
@Api
public class CPUService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BrawlerRepository brawlerRepository;

    @Autowired
    private CPURepository cpuRepository;

    @Autowired
    private CPUTeamsRepository cpuTeamsRepository;


    public CPU createCPU(int level, String username){
        CPU cpu = new CPU("https://imgur.com/P0WQtmC", level);


        cpu = addAccountToTeam(username, cpu);
        cpu = generateCPUBrawlers(level, cpu);

        cpu.toJSONObject();

        return cpu;
    }



    /**
     * Creates a new arraylist of brawlers for a CPU
     *
     * @return arraylist of all generated brawlers
     */
    public CPU generateCPUBrawlers(int level, CPU cpu){
        Random rand = new Random();

        int count = 0;

        // set count based on CPU level
        switch (level){
            case 0:
                count = 1;
                break;
            case 1:
                count = rand.nextInt(1) + 1;
                break;
            case 2:
                count = rand.nextInt(1) + 2;
                break;
            case 3:
                count = rand.nextInt(2) + 2;
                break;
            case 4:
                count = rand.nextInt(2) + 3;
                break;
            default:
                count = 5;
                break;
        }

        // get list of all available brawlers by level for selection
        List<Brawler> availableBrawlers = brawlerRepository.findByLevelLessThanEqual(cpu.getLevel() + 1);
        List<CPUBrawler> brawlers = new ArrayList<CPUBrawler>();

        for (int i = 0; i < count; i++){
            //System.out.println((rand.nextInt(availableBrawlers.size())));
            //System.out.println(availableBrawlers.size());

            Brawler chosenBrawler = availableBrawlers.get(rand.nextInt(availableBrawlers.size()));
            System.out.println(chosenBrawler.toJSONObject());

            int id = chosenBrawler.getId();
            Brawler.attributeTime a_time = chosenBrawler.getA_time();
            int a_id = chosenBrawler.getAttributeId();
            int damage = chosenBrawler.getDamage();
            int health = chosenBrawler.getHealth();
            int position = i;
            String url = chosenBrawler.getPircture_url();
            String name = chosenBrawler.getName();
            CPUBrawler temp = new CPUBrawler(id, cpu, damage, health, position, url, name, a_time, a_id);
            System.out.println(temp.toJSONObject());
            cpuTeamsRepository.save(temp);
            brawlers.add(temp);
        }

        int size = brawlers.size();

        for(int i = 0; i < size; i++){
            saveCPUBrawler(brawlers.get(i), cpu);
        }

        return cpuRepository.findById(cpu.getId());
    }

    public void saveCPUBrawler(@NotNull CPUBrawler cpuBrawler, @NotNull CPU cpu){
        cpu.addBrawler(cpuBrawler);
        cpuBrawler.setBrawlerCPU(cpu);
        cpuTeamsRepository.save(cpuBrawler);
        cpuRepository.save(cpu);
    }

    public CPU addAccountToTeam(String username, CPU cpu) {
        // Find all users with username
        List<Account> accounts = accountRepository.findByUsername(username);

        // check to make sure one has been found
        if (accounts.size() > 0) {
            Account account = accounts.get(0);

//            if (!account.getCPU().equals(null)){
//                cpuRepository.delete(cpuRepository.findById(account.getCPU().getId()).get(0));
//            }
            //List<CPUBrawler> ydgyegdy = cpu.getCPUBrawlers();

            //System.out.println("hp:" + ydgyegdy.get(0).getBrawler_hp());

            // connect the account and cpu, mapping will do the rest
            account.setCpu(cpu);
            cpu.setAccount(account);

            //ydgyegdy = cpu.getCPUBrawlers();

            //System.out.println("hp test:" + ydgyegdy.get(0).getBrawler_hp());


            // save both and return the saved league on success
            accountRepository.save(account);

            //ydgyegdy = cpu.getCPUBrawlers();

            //System.out.println("hp test:" + ydgyegdy.get(0).getBrawler_hp());

            cpu = cpuRepository.save(cpu);


            //ydgyegdy = cpu.getCPUBrawlers();

            //System.out.println("hp test:" + ydgyegdy.get(0).getBrawler_hp());
            return cpu;
        } else {
            return null;
        }
    }
}
