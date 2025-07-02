package com.example.CONNECT.cache;


import com.example.CONNECT.entry.ConfigApiEntry;
import com.example.CONNECT.repository.ConfigAPIRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AppCache
{

    public static String APP_CACHE = null;


    @Autowired
    private ConfigAPIRepository configAPIRepository;


    @PostConstruct
    public void init(){
        List<ConfigApiEntry> configApiEntries = configAPIRepository.findAll();
        APP_CACHE = configApiEntries.getFirst().getAPI_KEY();
    }
}
