package com.revature.main.dao;

import com.revature.main.model.BanList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BanListRepository extends JpaRepository<BanList,Integer> {
    BanList findByType(String type);
}
