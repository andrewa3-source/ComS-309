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



    @RequestMapping(method = RequestMethod.GET, path = "/brawlers")
    public List<Brawlers> listBrawlers() {
        List<Brawlers> results = brawlersRepo.findAll();
        return results;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/brawlers/add")
    public String addBrawler(@RequestBody Brawlers brawler){
        brawlersRepo.save(brawler);
        return "Added!";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/brawlers/{b_id}/")
    public Optional<Brawlers> findBrawler(@PathVariable("b_id") int id){
        Optional<Brawlers> results = brawlersRepo.findById(id);
        return results;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/brawlers/delete/{b_id}/")
    public String deleteBrawler(@PathVariable("b_id") int id){
        Brawlers results = brawlersRepo.findById(id).get();
        brawlersRepo.delete(results);
        return "Deleted " + results.getBrawlerName();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/brawlers/stats/{b_id}/")
    public String brawlerStats(@PathVariable("b_id") int id){
        Brawlers results = brawlersRepo.findById(id).get();

        return results.getBrawlerStats();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/brawlers/update/{b_id}/{new_health}")
    public String updateBrawler(@PathVariable("b_id") int id, @PathVariable("new_health") int health){
        Brawlers results = brawlersRepo.findById(id).get();
        results.setHealth(health);
        return "Updated " + results.getBrawlerName() + "'s Health to " + results.getHealth() + "!";
    }


















}
