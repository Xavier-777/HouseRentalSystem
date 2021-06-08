package com.house.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 房屋选择
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseFilter {
    private String housePriceLimit;     //它将拆分为 housePriceMin 与 housePriceMax
    private int housePriceMin;
    private int housePriceMax;
    private String houseModel;
    private String houseType;
    private String houseOriented;
    private String housePriceSort;      //分为 ASC 和 DESC
}
