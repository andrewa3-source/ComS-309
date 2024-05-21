package backend.dbms.PocketBrawlers.Services;

import backend.dbms.PocketBrawlers.Account.Account;
import backend.dbms.PocketBrawlers.Account.AccountRepository;
import backend.dbms.PocketBrawlers.Brawler.Brawler;
import backend.dbms.PocketBrawlers.Brawler.BrawlerRepository;
import backend.dbms.PocketBrawlers.Brawler.User_Brawlers.UserRepository;
import backend.dbms.PocketBrawlers.Brawler.User_Brawlers.User_Brawlers;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
 * @author Andrew Ahrenkiel
 */
@Service
@Api
public class ShopService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BrawlerRepository brawlerRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Randomize and return a brawler for the shop, can be any brawler, no restrictions
     * @return Random Brawler
     */
    public Brawler shopRandomize(){
        Random r = new Random();
        int choice = r.nextInt((int)brawlerRepository.count());
        List<Brawler> temp = brawlerRepository.findAll();
        Brawler random_Brawler = temp.get(choice);
        return random_Brawler;
    }

    /**
     * Randomize and return a brawler for the shop, based on the turn of the current account and the level of selected brawler
     * @param account_id current account
     * @return Random Brawler
     */
    public Brawler shopRandomizeWithTurn(int account_id){
        int turn = accountRepository.findById(account_id).getTurn();
        Random r = new Random();

        while(true){
            int choice = r.nextInt((int)brawlerRepository.count());
            List<Brawler> temp = brawlerRepository.findAll();
            Brawler random_Brawler = temp.get(choice);
            if(turn > 6){
                return random_Brawler;
            }
            else if (turn > 4){
                if (random_Brawler.getLevel() == 1 || random_Brawler.getLevel() == 2 || random_Brawler.getLevel() == 3){
                    return random_Brawler;
                }
            }
            else if (turn > 2){
                if (random_Brawler.getLevel() == 1 || random_Brawler.getLevel() == 2){
                    return random_Brawler;
                }
            }
            else {
                if (random_Brawler.getLevel() == 1){
                    return random_Brawler;
                }
            }
        }
    }

    /**
     * Removes all the brawlers for the given account so they can be reset into a new team
     * (Warning, will erase current user brawler data for account)
     * @param a account to remove user brawlers from
     */
    public void removeTeamBrawlers(Account a){
        if (!a.getBrawlers().isEmpty()) {
            for(int i = 0; i < a.getBrawlers().size(); ++i){
                userRepository.delete(a.getBrawlers().get(i));
            }
        }

        if (!a.getBrawlers().isEmpty()) {
            a.getBrawlers().removeAll(a.getBrawlers());
        }
    }

    /**
     * Used for saving a user brawler to the account, brawler, and user brawler repository.
     * @param temp user brawler to be saved
     * @param a account
     * @param b brawler
     */
    public void saveUserBrawlerEverywhere(User_Brawlers temp, Account a, Brawler b){
        userRepository.save(temp);
        a.getBrawlers().add(temp);
        b.getAccounts().add(temp);
    }
}
