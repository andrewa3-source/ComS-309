package backend.dbms.PocketBrawlers.Leagues;

import backend.dbms.PocketBrawlers.Account.Account;
import backend.dbms.PocketBrawlers.Brawler.Brawler;
import backend.dbms.PocketBrawlers.Services.AccountLeagueService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller which allows admins to control leagues.
 *
 * @author Reid Coates
 */
@RestController
public class LeagueController {

    @Autowired
    LeagueRepository leagueRepository;

    private final AccountLeagueService accountLeagueService;

    public LeagueController(AccountLeagueService accountLeagueService) {
        this.accountLeagueService = accountLeagueService;
    }

    /**
     * Function to create league from JSONObject passed in.
     *
     * @param jsonObject to create league from
     * @return JSONObject of created league
     */
    @PostMapping(value = "/createLeague")
    public JSONObject saveLeague(@RequestBody JSONObject jsonObject) {

        // map the passed in JSONObject to a league object
        ObjectMapper m = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        League l;

        // write the object to league
        try {
            String league_as_string = m.writeValueAsString(jsonObject);
            l = m.readValue(league_as_string, League.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        // save the created object
        leagueRepository.save(l);
        return l.toJSONObject();
    }

    /**
     * Add a league member to a league if they exist
     *
     * @param obj JSONObject with username and league id
     * @return league which the member was added to
     */
    @PostMapping(value ="/addLeagueMember")
    public JSONObject addMember(@RequestBody JSONObject obj){
        // pull the username and league_id of the request
        Integer league_id = Integer.parseInt((String) obj.get("league_id"));
        String username = (String) obj.get("username");

        JSONObject t = new JSONObject();

        // use the league service to add the member to the league and get the members of the account
        accountLeagueService.addAccountToTeam(username, league_id);
        List<Account> accounts = accountLeagueService.getAllLeagueMembers(league_id);
        JSONObject data = new JSONObject();

        // turn the accounts into a string array to give the front end for displaying
        ArrayList<String> league_members = new ArrayList<String>();
        for (Account account: accounts) {
            league_members.add(account.getUsername());
        }

        // add the league_id as a string and the league members as a string array
        data.put("league_id", league_id.toString());
        data.put("league_members", league_members);
        return data;
    }

    /**
     * Return all members in a league
     *
     * @param obj JSONObject with league id to search for
     * @return JSONObject with league id and all members as string array
     */
    @PostMapping(value = "/getLeague")
    public JSONObject getMembers(@RequestBody JSONObject obj){
        // pull the league_id from the request
        Integer league_id = Integer.parseInt((String) obj.get("league_id"));

        // use the service to find the members in the league
        List<Account> accounts = accountLeagueService.getAllLeagueMembers(league_id);
        JSONObject data = new JSONObject();

        // turn the accounts into a string array to give the front end for displaying
        ArrayList<String> league_members = new ArrayList<String>();
        for (Account account: accounts) {
            league_members.add(account.getUsername());
        }

        // add the league_id as a string and the league members as a string array
        data.put("league_id", league_id.toString());
        data.put("league_members", league_members);
        return data;
    }

}
