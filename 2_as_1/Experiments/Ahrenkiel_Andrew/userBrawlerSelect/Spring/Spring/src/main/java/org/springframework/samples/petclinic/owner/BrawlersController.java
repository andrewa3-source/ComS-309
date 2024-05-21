package org.springframework.samples.petclinic.owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BrawlersController {

    BrawlerRepository brawlersRepo;
    @RequestMapping(method = RequestMethod.GET, path = "/init/brawlers")
    public String createDummyData() {
        Brawlers b1 = new Brawlers(1, "pikachu", 5, 5);
        Brawlers b2 = new Brawlers(2, "squirtle", 3, 6);
        Brawlers b3 = new Brawlers(3, "charmander", 6, 2);
        Brawlers b4 = new Brawlers(4, "bulbasaur", 4, 4);
        Brawlers b5 = new Brawlers(5, "totodile", 3, 5);
        Brawlers b6 = new Brawlers(6, "chikorita", 7, 2);
        Brawlers b7 = new Brawlers(7, "cyndaquil", 2, 6);
        Brawlers b8 = new Brawlers(8, "mudkip", 1, 9);
        Brawlers b9 = new Brawlers(9, "treecko", 3, 7);
        Brawlers b10 = new Brawlers(10, "Thanos", 100, 100);

        brawlersRepo.save(b1);
        brawlersRepo.save(b2);
        brawlersRepo.save(b3);
        brawlersRepo.save(b4);
        brawlersRepo.save(b5);
        brawlersRepo.save(b6);
        brawlersRepo.save(b7);
        brawlersRepo.save(b8);
        brawlersRepo.save(b9);
        brawlersRepo.save(b10);

        return "initialized all brawlers!";
    }













}
