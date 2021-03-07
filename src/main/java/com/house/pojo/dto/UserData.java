package com.house.pojo.dto;

import com.house.pojo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户数据传输对象
 * @author chriy
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserData {
	private int code;

	private String msg; //结果信息

	private int count;  //data的长度

	private List<User> data; //装User数据

}
