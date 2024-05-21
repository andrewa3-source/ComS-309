package backend.dbms.PocketBrawlers.Services;

import backend.dbms.PocketBrawlers.Account.Account;
import backend.dbms.PocketBrawlers.Account.AccountRepository;
import backend.dbms.PocketBrawlers.Brawler.User_Brawlers.User_Brawlers;
import backend.dbms.PocketBrawlers.Leagues.League;
import backend.dbms.PocketBrawlers.Leagues.LeagueRepository;
import io.swagger.annotations.Api;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for League and Account repositories to run queries in both.
 *
 * @author Reid Coates
 */
@Service
@Api
public class AccountLeagueService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private LeagueRepository leagueRepository;

    /**
     * Returns a formatted JSONObject of searched for Account's brawler team.
     *
     */
    public JSONObject getFormattedBrawlersFromId(int account_id){
        JSONObject accountBrawlers = new JSONObject();

        Account a = accountRepository.findById(account_id);

        List<User_Brawlers> brawlers = a.getBrawlers();

        for (int i = 1; i < 6; i ++){
            accountBrawlers.put("b" + i, brawlers.get(i - 1).getBrawler());
        }

        return accountBrawlers;
    }


    /**
     * Search a selected league for the members in the user repository
     *
     * @param league_id to search for
     * @return Array of accounts as members of the league
     */
    public List<Account>getAllLeagueMembers(int league_id){
        // get all leagues with that id
        List<League> leagues = leagueRepository.findByid(league_id);
        for (League league : leagues) {
            List<Account> accounts = accountRepository.findByLeague(league);
            league.setAccounts(accounts);
        }

        // check if search found anything
        if (leagues.size() > 0){
            return leagues.get(0).getAccounts();
        } else {
            return new ArrayList<Account>();
        }

    }

    /**
     * Returns all leagues in repository
     *
     * @return List of leagues
     */
    public List<League> getAllLeagues() {
        List<League> leagues = leagueRepository.findAll();
        for (League league : leagues) {
            List<Account> accounts = accountRepository.findByLeague(league);
            league.setAccounts(accounts);
        }
        return leagues;
    }

    /**
     * Returns all leagues with given id
     *
     * @param league_id to search by
     * @return List of leagues with id
     */
    public List<League> getLeagueById(int league_id) {
        // find all leagues with given id
        List<League> leagues = leagueRepository.findByid(league_id);
        for (League league : leagues) {

            // for all found leagues, search the repository for every account in that league
            List<Account> accounts = accountRepository.findByLeague(league);
            league.setAccounts(accounts);
        }
        return leagues;
    }

    /**
     * Method which adds a user to a league via search the repository. Returns null if either league or user is not found.
     *
     * @param username of user to add in
     * @param league_id of league to add to
     * @return league if user is added, null if either is not found
     */
    public League addAccountToTeam(String username, int league_id) {
        // Find all users with username and all leagues with league id
        List<Account> accounts = accountRepository.findByUsername(username);
        List<League> leagues = leagueRepository.findByid(league_id);

        // check to make sure one has been found in each repository
        if (accounts.size() > 0 && leagues.size() > 0) {
            // get the first instance (assuming we have no repeats)
            Account account = accounts.get(0);
            League league = leagues.get(0);

            // connect the account and league, mapping will do the rest
            account.setLeague(league);
            league.addAccount(account);

            // save both and return the saved league on success
            accountRepository.save(account);
            return leagueRepository.save(league);
        } else {
            return null;
        }
    }
}