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
package org.springframework.samples.netflix.shows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.netflix.users.UserRepository;
import org.springframework.samples.netflix.users.Users;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Show controller / routing class
 *
 * @author Reid Coates
 */
@RestController
class ShowController {

    @Autowired
    ShowRepository showsRepository;

    private final Logger logger = LoggerFactory.getLogger(ShowController.class);
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST, path = "/create")
    public String saveShow(@RequestBody Shows show) {
        showsRepository.save(show);
        return "New show "+ show.getTitle() + " has been added!";
    }

    /**
     * Method which adds a show to a user's history
     *
     * @param showId that was watch
     * @param userId that watched the show
     */
    @RequestMapping(method = RequestMethod.PUT, path = "/watch/{userId}/{showId}")
    public String saveShow(@PathVariable("showId") Integer showId, @PathVariable("userId") Integer userId) {
        Optional<Users> user = userRepository.findById(userId);
        Optional<Shows> show = showsRepository.findById(showId);
        if (user.isPresent() && show.isPresent()) {
            user.get().addHistory(show.get().getTitle());
            userRepository.save(user.get());
            return show.get().getTitle() + " has been added to " + user.get().getUsername() + "'s watch history.";
        } else {
            return "User or show does not exist.";
        }
    }


    // function just to create dummy data
    @RequestMapping(method = RequestMethod.GET, path = "/create/initialize")
    public String createShows() {
        Shows s1 = new Shows(1, "Kenobi", 8);
        Shows s2 = new Shows(2, "Hunter x Hunter", 144);
        Shows s3 = new Shows(3, "Queen's Gambit", 8);
        Shows s4 = new Shows(4, "Breaking Bad", 43);
        showsRepository.save(s1);
        showsRepository.save(s2);
        showsRepository.save(s3);
        showsRepository.save(s4);
        return "Successfully created initial show list!";
    }

    @RequestMapping(method = RequestMethod.GET, path = "/list")
    public List<Shows> getAllShows() {
        List<Shows> results = showsRepository.findAll();
        logger.info("Number of Records Fetched:" + results.size());
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/read/{showId}")
    public Optional<Shows> findShowsById(@PathVariable("showId") int id) {
        Optional<Shows> results = showsRepository.findById(id);
        return results;
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/delete/{showId}")
    public String deleteShow(@PathVariable("showId") int id) {
        Optional<Shows> results = showsRepository.findById(id);
        showsRepository.deleteById(id);
        return "Successfully deleted " + results.get().getTitle();
    }


    @RequestMapping(method = RequestMethod.PUT, path = "/update/{showId}/{newTitle}")
    public String findShowByIdAndUpdate(@PathVariable("showId") int id, @PathVariable("newTitle") String title) {
        Optional<Shows> result = showsRepository.findById(id);
        result.get().setTitle(title);
        showsRepository.save(result.get());
        return "Successfully updated show " + result.get().getTitle();
    }
}
