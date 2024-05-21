package backend.dbms.PocketBrawlers.Account;


import backend.dbms.PocketBrawlers.Leagues.League;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for saving Accounts
 *
 * @author Reid Coates
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {
    List<Account> findByUsername(String username);

    Account findById(int id);

    List<Account> findByLeague(League league);
}
