package com.house.scheduleTask;

import com.house.constant.HouseStatus;
import com.house.constant.OrderStatus;
import com.house.pojo.entity.House;
import com.house.pojo.entity.Order;
import com.house.service.IHouseService;
import com.house.service.IOrderService;

import java.util.TimerTask;

/**
 * 计划任务：取消订单，即修改订单状态为 pass，取消掉order
 */
public class CancelOrder extends TimerTask {
    private House house;
    private Order order;
    private IHouseService houseService;
    private IOrderService orderService;

    public CancelOrder(House house, Order order, IHouseService houseService, IOrderService orderService) {
        this.house = house;
        this.order = order;
        this.houseService = houseService;
        this.orderService = orderService;
    }

    @Override
    public void run() {
        //改变房屋状态
        house.setHouseStatus(HouseStatus.pass.toString());
        houseService.checkHouse(house);
        //改变订单状态
        order.setOrderStatus(OrderStatus.not_pay.toString());
        orderService.checkOrder(order);
    }
}
