package com.lcwd.user.service.impl;

import com.lcwd.user.service.entities.Rating;
import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.exceptions.ResourceNotFoundException;
import com.lcwd.user.service.repositories.UserRepository;
import com.lcwd.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        //generate unique userId
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        //get user from database with the help of user repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found on server!! : " + userId));
        //fetch the rating of the above user from RATING SERVICE
        ArrayList<Rating> ratingsOfUser = restTemplate.getForObject("http://localhost:8083/ratings/user/" + user.getUserId(), ArrayList.class);
        logger.info("{}", ratingsOfUser);
        user.setRatings(ratingsOfUser);
        return user;
    }
}

