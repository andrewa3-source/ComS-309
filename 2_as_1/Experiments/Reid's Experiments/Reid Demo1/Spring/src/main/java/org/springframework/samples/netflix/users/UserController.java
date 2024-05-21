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
package org.springframework.samples.netflix.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * User controller / routing class
 *
 * @author Reid Coates
 */
@RestController
class UserController {

    @Autowired
    public UserRepository usersRepository;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(method = RequestMethod.POST, path = "/users/create")
    public String saveShow(@RequestBody Users user) {
        usersRepository.save(user);
        return "New user "+ user.getUsername() + " has been added!";
    }


    // function just to create dummy data
    @RequestMapping(method = RequestMethod.GET, path = "/users/create/initialize")
    public String createShows() {

        Users s1 = new Users(1, "Reid", "supersecure007");
        Users s2 = new Users(2, "John Doe", "password1234");
        usersRepository.save(s1);
        usersRepository.save(s2);

        return "Successfully created initial users!";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/users/list")
    public List<Users> getAllShows() {
        List<Users> results = usersRepository.findAll();
        logger.info("Number of Users Fetched:" + results.size());
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/users/read/{userId}")
    public Optional<Users> findShowsById(@PathVariable("userId") int id) {
        Optional<Users> results = usersRepository.findById(id);
        return results;
    }


    @RequestMapping(method = RequestMethod.PUT, path = "/users/update/{userId}/{newPassword}")
    public String findShowByIdAndUpdate(@PathVariable("userId") int id, @PathVariable("newPassword") String password) {
        Optional<Users> result = usersRepository.findById(id);
        result.get().setPassword(password);
        usersRepository.save(result.get());
        return "Successfully updated password for " + result.get().getUsername();
    }
}