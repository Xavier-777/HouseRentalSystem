package com.house.mapper;

import com.house.pojo.entity.House;
import com.house.pojo.entity.HouseFilter;
import com.house.pojo.entity.Page;

import java.util.List;

/**
 * @author chriy
 */
public interface HouseMapper {
    /**
     * 首页信息展示
     *
     * @return List
     */
    List<House> findHomeInfo();

    /**
     * 通过id查询房屋详情
     *
     * @param id id
     * @return House
     */
    House findHouseDetailsById(int id);

    /**
     * 添加房源信息
     *
     * @param house house
     * @return int
     */
    int addNewHouse(House house);

    /**
     * 查询用户发布的房源信息
     *
     * @param page page分页
     * @return List
     */
    List<House> findHouseByUser(Page page);

    /**
     * 删除用户发布的房源信息
     *
     * @param houseId id
     * @return int
     */
    int deleteUserHouse(int houseId);

    /**
     * 修改用户发布的房源信息
     *
     * @param house house对象
     * @return int
     */
    int updateHouse(House house);

    /**
     * 条件查询
     *
     * @param keywords 关键字
     * @return list
     */
    List<House> findHouseByLike(String keywords);

    /**
     * 降序查询
     *
     * @return list
     */
    List<House> findHouseOrderByDesc();

    /**
     * 升序序查询
     *
     * @return list
     */
    List<House> findHouseOrderByAsc();

    /**
     * 查找用户发布的房源数目
     *
     * @param p
     * @return
     */
    int findHouseCountByUser(Page p);

    /**
     * 审核房屋
     *
     * @param house
     * @return
     */
    int checkHouse(House house);

    /**
     * 筛选房屋
     *
     * @param houseFilter
     * @return
     */
    List<House> findTargetHouses(HouseFilter houseFilter);

    /**
     * 删除房屋图片
     *
     * @param house
     * @return
     */
    int deleteHouseImg(House house);

    /**
     * 更新房屋图片
     * @param house
     * @return
     */
    int updateHouseImg(House house);
}
