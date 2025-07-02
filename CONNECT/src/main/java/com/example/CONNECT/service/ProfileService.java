package com.example.CONNECT.service;

import com.example.CONNECT.entry.ProfileEntry;
import com.example.CONNECT.entry.User;
import com.example.CONNECT.repository.ProfileRepository;
import com.example.CONNECT.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class ProfileService {


    @Autowired
    private UserService userService;

    @Autowired
    private ProfileRepository profileRepository;


    @Autowired
    private UserRepository userRepository;


    public String addContent(ProfileEntry profileEntry, String name) throws Exception {
        try {
            User user = userRepository.findByUsername(name);
            profileEntry.setDate(LocalDateTime.now());
            ProfileEntry entry = profileRepository.save(profileEntry);
            user.getProfileEntry().add(entry);
            userRepository.save(user);
            return "Profile updated.";
        }catch (Exception e){
            throw new Exception("Inter error occur."+e);
        }

    }


    public boolean deleteById(ObjectId id, String name){

        boolean removed = false;
        User user = userRepository.findByUsername(name);
        removed = user.getProfileEntry().removeIf(x -> x.getId().equals(id));
        if(removed){
            userService.saveUser(user);
            profileRepository.deleteById(id);
        }
        return removed;
    }
}