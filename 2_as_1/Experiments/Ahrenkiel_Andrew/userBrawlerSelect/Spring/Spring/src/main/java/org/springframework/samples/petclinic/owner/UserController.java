/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import org.h2.engine.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
class UserController {

    @Autowired
    UserRepository usersRepository;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = RequestMethod.POST, path = "/users/new")
    public String saveUser(@RequestBody Users user) {
        usersRepository.save(user);
        return "New user "+ user.getUsername() + " Saved";
    }
     // function just to create dummy data
    @RequestMapping(method = RequestMethod.GET, path = "/init/users")
    public String createDummyData() {
        Users p1 = new Users(1, "John", "Doe", "mysticDragon123", "some numbers");
        Users p2 = new Users(2, "Jane", "Doe", "girlPower987", "you wish");
        Users p3 = new Users(3, "Ryan", "Joesph", "Rycat8", "515-345-41213");
        Users p4 = new Users(4, "Giga", "Chad", "redditGod88", "420-420-4200");
        usersRepository.save(p1);
        usersRepository.save(p2);
        usersRepository.save(p3);
        usersRepository.save(p4);

        return "Successfully Initialized Our NPC's and Avaliable Brawler List";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/users")
    public List<Users> getAllUsers() {
        logger.info("Entered into Controller Layer");
        List<Users> results = usersRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/users/{ownerId}")
    public Optional<Users> findOwnerById(@PathVariable("ownerId") int id) {
        logger.info("Entered into Controller Layer");
        Optional<Users> results = usersRepository.findById(id);
        return results;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/users/updateFN/{ownerId}/{newName}")
        public String updateUserFN(@PathVariable("ownerID") int id, @PathVariable String newName){
            Optional<Users> results = usersRepository.findById(id);
            String prev = results.get().getFirstName();
            results.get().setFirstName(newName);
            return "Updated user " + id + " first name from " + prev + " to " + newName;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/users/updateLN/{ownerId}/{newName}")
    public String updateUserLN(@PathVariable("ownerID") int id, @PathVariable("newName") String newName){
        Optional<Users> results = usersRepository.findById(id);
        String prev = results.get().getLastName();
        results.get().setLastName(newName);
        return "Updated user " + id + " last name from " + prev + " to " + newName;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/set-your-starter/{ownerId}/{starter}")
    public String updateUserSS(@PathVariable("ownerID") int id, @PathVariable("starter") String starter){

        for(int i = 0; i < 11; ++i){
            Optional<Users> results = usersRepository.findById(i);
            if (results.get().getStarter() == starter){
                return "sorry, that brawler is taken, please select a different brawler";
            }
        }
        Optional<Users> results = usersRepository.findById(id);
        results.get().setStarter(starter);
        return "Updated user " + id + " starting brawler to " + starter;
    }

}


