package com.house.controller.house;

import com.house.constant.HouseStatus;
import com.house.constant.OrderStatus;
import com.house.pojo.dto.UserOrderData;
import com.house.pojo.entity.*;
import com.house.scheduleTask.CancelOrder;
import com.house.service.IAlipayService;
import com.house.service.IHouseService;
import com.house.service.IOrderService;
import com.house.service.IUserService;
import com.house.utils.FileUtils;
import com.house.utils.OSSUtils;
import com.house.utils.OrderUtils;
import com.sun.deploy.net.URLEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;
import java.util.Timer;

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

    @Autowired
    private IHouseService houseService;

    @Autowired
    private IAlipayService alipayService;

    @Autowired
    private IUserService userService;

    /**
     * 订单id
     */
    private String payingOrderId = null;

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
            //下单
            House details = houseService.findHouseDetailsById(Integer.parseInt(id));
            Order order = new Order();
            order.setHouseId(Integer.parseInt(id));
            order.setOrderUser(user.getUserNickName());
            order.setOrderPublisher(details.getPublisher());
            order.setUserId(user.getUserId());
            order.setOrderStatus(OrderStatus.ordered.toString());
            order.setOrderNumber(OrderUtils.getOrderNumber());
            order.setOrderPrice(details.getHousePrice());
            int orderResult = orderService.addOrder(order);
            //修改房屋状态
            House house = new House();
            house.setHouseId(Integer.parseInt(id));
            house.setHouseStatus(HouseStatus.ordered.toString());
            int houseResult = houseService.checkHouse(house);
            //计划任务：15分钟后若不付款则取消订单取消订单
            Timer timer = new Timer();
            timer.schedule(new CancelOrder(house, order, houseService, orderService), 1000 * 60 * 15);

            if (orderResult > 0 && houseResult > 0) {
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

    /**
     * 订单支付
     *
     * @param orderId
     * @param housePrice
     * @return
     */
    @RequestMapping("/payOrder")
    public String payOrder(String orderId, String housePrice, HttpServletRequest request) {
        Order order = new Order();
        order.setOrderPrice(new BigDecimal(housePrice));
        order.setOrderNumber(OrderUtils.getOrderNumber());
        String body = alipayService.pay(order);
        request.getSession().setAttribute("body", body);
        payingOrderId = orderId;            //写在这里防止被截取orderId
        return "order/paying.jsp";
    }

    /**
     * 支付成功后，将改变订单、房屋状态，然后钱直接打到账户上
     *
     * @param request
     * @return
     */
    @GetMapping("/verifyOrder")
    public String verifyOrder(HttpServletRequest request) {
        VerifyMessage verify = alipayService.verify(request, payingOrderId);
        payingOrderId = null;                   //参数传递完后置空值
        if (verify.getMessage().equals("OK")) {
            int orderId = verify.getOrderId();
            Order order = new Order();
            order.setOrderId(orderId);
            order.setOrderStatus(OrderStatus.paid.toString());
            //改变订单状态
            orderService.checkOrder(order);
            //改变房屋状态
            Order orderInfo = orderService.findOrderInfo(orderId);
            House house = new House();
            house.setHouseId(orderInfo.getHouseId());
            house.setHouseStatus(HouseStatus.using.toString());
            houseService.checkHouse(house);
            //钱直接到账
            User user = userService.findUserByPublisher(orderInfo.getOrderPublisher());
            user.setUserBalance(orderInfo.getOrderPrice());
            userService.updateBalance(user);
        }
        return "redirect:/user/home.html";
    }


    @GetMapping("/detailOrder")
    public String detailOrder(int orderId, HttpServletRequest request) {
        UserOrder userOrder = orderService.getOrderDetail(orderId);
        request.getSession().setAttribute("detailOrderInfo", userOrder);
        request.getSession().setAttribute("briefImage", OSSUtils.getImage(userOrder.getHouseImage()));
        List<String> imgList = FileUtils.parseHouseDetailsImg(userOrder.getHouseDetailsImg());
        request.getSession().setAttribute("DetailsImg", imgList);
        return "order/detailOrder.jsp";
    }

}
