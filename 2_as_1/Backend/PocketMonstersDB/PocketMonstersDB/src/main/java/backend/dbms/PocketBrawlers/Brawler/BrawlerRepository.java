package backend.dbms.PocketBrawlers.Brawler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

/**
 * @author Andrew Ahrenkiel
 */
@Api(tags = "br")
public interface BrawlerRepository extends JpaRepository<Brawler, Integer> {
    @Override
    long count();

    @Override
    @ApiOperation(value = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved users"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    Optional<Brawler> findById(Integer integer);

    List<Brawler> findByLevelLessThanEqual(int level);

    @Override
    <S extends Brawler> S save(S entity);
}
