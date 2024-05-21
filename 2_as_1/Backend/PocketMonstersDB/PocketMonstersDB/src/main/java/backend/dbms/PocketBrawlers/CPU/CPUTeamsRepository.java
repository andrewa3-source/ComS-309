package backend.dbms.PocketBrawlers.CPU;

import backend.dbms.PocketBrawlers.CPU.CPUBrawler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Reid Coates
 */
public interface CPUTeamsRepository extends JpaRepository<CPUBrawler, Integer> {
    @Override
    Optional<CPUBrawler> findById(Integer integer);
}
