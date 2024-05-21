package backend.dbms.PocketBrawlers.Account;
import backend.dbms.PocketBrawlers.Leagues.League;
import backend.dbms.PocketBrawlers.Account.Account_League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for saving Account League Requests
 *
 * @author Andrew Ahrenkiel
 */
public interface AccountLeagueRepository extends JpaRepository<Account_League, Integer> {
    List<Account_League> findByUsername(String username);

    Account_League findById(int id);

}