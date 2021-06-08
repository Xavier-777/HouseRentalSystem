package com.house.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author chriy
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Order {
    private int orderId;
    private int houseId;
    private int userId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date orderTime;
    private String orderUser;
    private String orderPublisher;
    private String orderNumber;
    private BigDecimal orderPrice;
    private String orderStatus;
    private String orderName = "租房";    //不计入数据库，只用于支付宝支付
}
