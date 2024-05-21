package backend.dbms.PocketBrawlers.Leagues;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for Leagues.
 *
 * @author Reid Coates
 */
public interface LeagueRepository extends JpaRepository<League, Integer> {
    List<League> findByName(String name);

    List<League> findByid(int league_id);
}