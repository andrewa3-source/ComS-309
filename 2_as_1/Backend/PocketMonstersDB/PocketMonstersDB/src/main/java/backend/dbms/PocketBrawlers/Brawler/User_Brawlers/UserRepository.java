package backend.dbms.PocketBrawlers.Brawler.User_Brawlers;

import backend.dbms.PocketBrawlers.Brawler.User_Brawlers.User_Brawlers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User_Brawlers, Integer> {
    @Override
    long count();

    @Override
    Optional<User_Brawlers> findById(Integer integer);

    @Override
    <S extends User_Brawlers> S save(S entity);
}
