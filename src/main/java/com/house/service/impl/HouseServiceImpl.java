package com.house.service.impl;

import java.util.List;

import com.house.pojo.entity.HouseFilter;
import com.house.service.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.mapper.HouseMapper;
import com.house.pojo.entity.House;
import com.house.pojo.entity.Page;

/**
 * @author chriy
 */
@Service
public class HouseServiceImpl implements IHouseService {

    @Autowired
    private HouseMapper dao;

    @Override
    public List<House> findHomeInfo() {
        return dao.findHomeInfo();
    }

    @Override
    public House findHouseDetailsById(int id) {
        return dao.findHouseDetailsById(id);
    }

    @Override
    public int addNewHouse(House house) {
        return dao.addNewHouse(house);
    }

    @Override
    public List<House> findHouseByUser(Page page) {
        return dao.findHouseByUser(page);
    }

    @Override
    public int deleteUserHouse(int houseId) {
        return dao.deleteUserHouse(houseId);
    }

    @Override
    public int updateHouse(House house) {
        return dao.updateHouse(house);
    }

    @Override
    public List<House> findHouseByLike(String keywords) {
        return dao.findHouseByLike(keywords);
    }

    @Override
    public List<House> findHouseOrderByAsc() {
        return dao.findHouseOrderByAsc();
    }

    @Override
    public int findHouseCountByUser(Page p) {
        return dao.findHouseCountByUser(p);
    }

    @Override
    public int checkHouse(House house) {
        return dao.checkHouse(house);
    }

    @Override
    public List<House> findTargetHouses(HouseFilter houseFilter) {
        return dao.findTargetHouses(houseFilter);
    }

    @Override
    public int deleteHouseImg(House house) {
        return dao.deleteHouseImg(house);
    }

    @Override
    public int updateHouseImg(House house) {
        return dao.updateHouseImg(house);
    }

    @Override
    public List<House> findHouseOrderByDesc() {
        return dao.findHouseOrderByDesc();
    }
}
