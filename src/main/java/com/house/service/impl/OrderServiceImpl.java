package com.house.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.mapper.OrderMapper;
import com.house.pojo.entity.Order;
import com.house.pojo.entity.Page;
import com.house.pojo.entity.UserOrder;
import com.house.service.IOrderService;

/**
 * @author chriy
 */
@Service
public class OrderServiceImpl implements IOrderService{

	@Autowired
	private OrderMapper mapper;
	
	@Override
	public int addOrder(Order order) {
		return mapper.addOrder(order);
	}

	@Override
	public List<UserOrder> findAllOrder(Page page) {
		return mapper.findAllOrder(page);
	}

	@Override
	public int getOrderCount(int userId) {
		return mapper.getOrderCount(userId);
	}

	@Override
	public int deleteOrder(int orderId) {
		return mapper.deleteOrder(orderId);
	}

	@Override
	public int checkOrder(Order order) {
		return mapper.checkOrder(order);
	}

	@Override
	public Order findOrderInfo(int orderId) {
		return mapper.findOrderInfo(orderId);
	}

	@Override
	public UserOrder getOrderDetail(int orderId) {
		return mapper.getOrderDetail(orderId);
	}

}
