package com.house.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 租房用户
 * @author chriy
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
	private int userId;
	private String userName;
	private String userPassword;
	private String userPhoneNumber;
	private String userNickName;
	private BigDecimal userBalance;
}
