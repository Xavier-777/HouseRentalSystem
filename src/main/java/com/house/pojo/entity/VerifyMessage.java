package com.house.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerifyMessage {
    private int orderId;
    private String message;
    private BigDecimal orderPrice;
}
