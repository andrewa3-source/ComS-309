package backend.dbms.PocketBrawlers.CPU;

import backend.dbms.PocketBrawlers.Account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for CPUs (Seperate from the Account Repository so that any deletion or updates don't break other code)
 * Holds the methods to search the Repository for items.
 *
 * @author Reid Coates
 */
public interface CPURepository extends JpaRepository<CPU, Integer> {
    CPU findById(int id);

    CPU findByAccountCPU(Account account);
}