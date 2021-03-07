package com.house.controller.house;

import com.house.pojo.dto.UserOrderData;
import com.house.pojo.entity.Order;
import com.house.pojo.entity.Page;
import com.house.pojo.entity.User;
import com.house.pojo.entity.UserOrder;
import com.house.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 收藏控制类
 *
 * @author chriy
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    /**
     * 用户的收藏房源界面、“我的租房信息”
     *
     * @return view
     */
    @GetMapping("/myOrder.html")
    public String toOrderPage() {
        return "/user/myOrder.jsp";
    }

    /**
     * 添加订单
     * 问题：重复添加订单
     *
     * @param id      房源id
     * @param request req
     * @return res
     */
    @PostMapping("/addOrder")
    @ResponseBody
    public String addOrder(String id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("loginUser");
        try {
            Order order = new Order();
            order.setHouseId(Integer.parseInt(id));
            order.setOrderUser(user.getUserNickName());
            order.setUserId(user.getUserId());
            int result = orderService.addOrder(order);
            if (result > 0) {
                return "OK";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return "FAIL";
    }

    /**
     * 查询我的所有收藏房源信息
     *
     * @param page    page
     * @param limit   limit
     * @param request req
     * @return res
     */
    @PostMapping("/myOrderInfo")
    @ResponseBody
    public UserOrderData findAllOrder(int page, int limit, HttpServletRequest request) {
        Page p = new Page();
        p.setPage((page - 1) * limit);
        p.setLimit(limit);
        User user = (User) request.getSession().getAttribute("loginUser");
        p.setUserId(user.getUserId());
        UserOrderData uod = new UserOrderData();
        List<UserOrder> order = orderService.findAllOrder(p);
        uod.setCode(0);
        uod.setCount(orderService.getOrderCount(user.getUserId()));
        uod.setData(order);
        uod.setMsg("200");
        return uod;
    }

    /**
     * 删除收藏的房源信息
     *
     * @param orderId 单号
     * @return res
     */
    @PostMapping("/deleteOrder")
    @ResponseBody
    public String deleteOrder(int orderId) {
        int result = orderService.deleteOrder(orderId);
        if (result > 0) {
            return "OK";
        }
        return "FAIL";
    }
}
