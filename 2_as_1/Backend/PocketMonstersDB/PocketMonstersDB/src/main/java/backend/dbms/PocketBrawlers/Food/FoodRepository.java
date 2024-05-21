package backend.dbms.PocketBrawlers.Food;

import backend.dbms.PocketBrawlers.Brawler.Brawler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Andrew Ahrenkiel
 */
public interface FoodRepository extends JpaRepository<Food, Integer> {
    @Override
    long count();

    @Override
    Optional<Food> findById(Integer integer);

    @Override
    <S extends Food> S save(S entity);
}
