package com.house.service;

import com.house.pojo.entity.Order;
import com.house.pojo.entity.VerifyMessage;

import javax.servlet.http.HttpServletRequest;

public interface IAlipayService {
    String pay(Order order);

    VerifyMessage verify(HttpServletRequest request,String orderId);

}
