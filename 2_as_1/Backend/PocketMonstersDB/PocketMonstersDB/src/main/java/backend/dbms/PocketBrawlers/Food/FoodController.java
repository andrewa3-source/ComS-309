package backend.dbms.PocketBrawlers.Food;

import backend.dbms.PocketBrawlers.Account.Account;
import backend.dbms.PocketBrawlers.Account.AccountRepository;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * @author Andrew Ahrenkiel
 */
@RestController
public class FoodController {
    @Autowired
    FoodRepository foodRepository;

    @Autowired
    AccountRepository accountRepository;

    @GetMapping("/getAllFood")
    public List<Food> getAllFood(){
        return foodRepository.findAll();
    }

    @PostMapping("/addFood/{lvl}")
    public void addFood(@RequestBody String newFoodString, @PathVariable int lvl){
        Food newFood = new Food(newFoodString, lvl);
        foodRepository.save(newFood);
    }

    @GetMapping("/getRandomFood/{id}")
    public JSONObject getRandomFood(@PathVariable int id){
        Random r = new Random();
        List<Food> rFood = foodRepository.findAll();
        JSONObject temp = new JSONObject();
        Account a = accountRepository.findById(id);
        int turn = a.getTurn();
        while (true) {
            int choice = r.nextInt((int) foodRepository.count());
            Food foo = rFood.get(choice);
            if (turn > 4) {
                temp.put("id", foo.getId());
                temp.put("picture", foo.getPicture());
                return temp;
            } else if (foo.getLevel() == 1) {
                temp.put("id", foo.getId());
                temp.put("picture", foo.getPicture());
                return temp;
            }
        }
    }

    @DeleteMapping("/removeFood/{id}")
    public void removeFood(@PathVariable int id){
        foodRepository.deleteById(id);

    }

    @PostMapping("/setImg/{id}")
    public void setUrl(@PathVariable int id, @RequestBody String s){
        Food temp = foodRepository.findById(id).get();
        temp.setPicture(s);
        foodRepository.save(temp);

    }

    @PostMapping("setLvl/{id}/{lvl}")
    public void setLevel(@PathVariable int id, @PathVariable int lvl){
        Food temp = foodRepository.findById(id).get();
        temp.setLevel(lvl);
        foodRepository.save(temp);
    }


}
