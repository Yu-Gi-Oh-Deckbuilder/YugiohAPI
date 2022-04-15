package com.revature.main.service;

import com.revature.main.dao.BanListRepository;
import com.revature.main.model.BanList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class BanListService {

    @Autowired
    private BanListRepository banListRepository;

    public BanList findBanList(String type){
        return banListRepository.findByType(type);
    }
}
