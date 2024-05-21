package backend.dbms.PocketBrawlers.Brawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Andrew Ahrenkiel
 */

@RestController
public class BrawlerController {
    @Autowired
    BrawlerRepository brawlerRepository;

    /**
     * Used for reseting the database with our current list of brawlers
     * @return
     */
    @PostMapping(value = "/setBrawlerDB")
    public String setBrawlers(){
//        if (brawlerRepository.findAll() != null){
//            brawlerRepository.deleteAll();
//        }

//        Brawler b1 = new Brawler("Umbasaur", 3, 3, 0, 1, 0, Brawler.attributeTime.BUY, "https://imgur.com/P0WQtmC");
//        Brawler b2 = new Brawler("Corpmander", 2, 3, 1, 1, 0, Brawler.attributeTime.BATTLE, "https://imgur.com/Xhy1WqH");
//        Brawler b3 = new Brawler("Lickvee", 2, 2, 2, 1, 0, Brawler.attributeTime.BUY, "https://imgur.com/4gkdoAO");
//        Brawler b4 = new Brawler("Loporeon", 4, 4, 3, 1, 0, Brawler.attributeTime.SELL, "https://imgur.com/8JeJiLh");
        //Brawler b5 = new Brawler("Cofados", 5, 5, 4, 2, 0, Brawler.attributeTime.FAINT, "https://imgur.com/WCNgmz5");
        //Brawler b6 = new Brawler("Mimetuff", 2, 2, 5, 2, 0, Brawler.attributeTime.BATTLE, "https://imgur.com/X49ue2w");
        Brawler b7 = new Brawler("Seatric", 3, 4, 6, 2, 0, Brawler.attributeTime.BATTLE, "https://i.imgur.com/oGoYHGF.png");
        Brawler b8 = new Brawler("Osharim", 2, 1, 7, 1, 0, Brawler.attributeTime.BATTLE, "https://i.imgur.com/aFyszGP.png");
        Brawler b9 = new Brawler("Pikaunter", 10, 10, 8, 3, 0, Brawler.attributeTime.BUY, "https://i.imgur.com/cZ6x4cf.png");
        Brawler b10 = new Brawler("Pipsqueak", 6, 6, 9, 2, 0, Brawler.attributeTime.BATTLE, "https://i.imgur.com/LvgMuAL.png");
        Brawler b11 = new Brawler("Reggoro", 1, 7, 10, 2, 0, Brawler.attributeTime.SELL, "https://i.imgur.com/m3dTx9l.png");
        Brawler b12 = new Brawler("FILTHY BALL", 25, 25, 11, 4, 0, Brawler.attributeTime.BATTLE, "https://i.imgur.com/0WPTrH8.png");
        Brawler b13 = new Brawler("Dituffet", 2, 4, 12, 1, 0, Brawler.attributeTime.BATTLE, "https://i.imgur.com/y7jxBjo.png");
        Brawler b14 = new Brawler("GMO", 6, 8, 13, 4, 0, Brawler.attributeTime.BATTLE, "https://i.imgur.com/yAejBN9.png");
        brawlerRepository.save(b7);
        brawlerRepository.save(b8);
        brawlerRepository.save(b9);
        brawlerRepository.save(b10);
        brawlerRepository.save(b11);
        brawlerRepository.save(b12);
        brawlerRepository.save(b13);
        brawlerRepository.save(b14);

        return "Success!";
    }

    @PostMapping(value = "/Change_A/{b_id}/{a_id}/{a_time}")
    public void changeA(@PathVariable int b_id, @PathVariable int a_id, @PathVariable String a_time){
        Brawler b = brawlerRepository.findById(b_id).get();
        b.setAttributeId(a_id);
        if (a_time.equals("BUY")){
            b.setA_time(Brawler.attributeTime.BUY);
        }
        else if (a_time.equals("SELL")){
            b.setA_time(Brawler.attributeTime.SELL);
        }
        else if (a_time.equals("BATTLE")){
            b.setA_time(Brawler.attributeTime.BATTLE);
        }

        brawlerRepository.save(b);
    }

    @PostMapping(value = "/ChangeBrawlerLevel/{id}/{lvl}")
    public void changeBrawlerLevel(@PathVariable int id, @PathVariable int lvl){
        Brawler temp = brawlerRepository.findById(id).get();
        temp.setLevel(lvl);
        brawlerRepository.save(temp);
    }
}
