package com.house.controller.user;

import com.house.pojo.entity.House;
import com.house.pojo.entity.User;
import com.house.service.IHouseService;
import com.house.service.IUserService;
import com.house.utils.FileUtils;
import com.house.utils.OSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户控制类
 *
 * @author chriy
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IHouseService houseService;

    /**
     * 后台第一个欢迎界面
     *
     * @return view
     */
    @GetMapping("/welcome.html")
    public String toWelcomePage() {
        return "welcome.jsp";
    }

    /**
     * 用户管理页
     *
     * @return view
     */
    @GetMapping("/home.html")
    public String toUserSystemPage(HttpServletRequest request) {
        //这样做的目的是更新钱包余额
        User user = (User) request.getSession().getAttribute("loginUser");
        User login = userService.login(user);
        request.getSession().setAttribute("loginUser",login);
        return "/user/home.jsp";
    }

    /**
     * 用户修改密码界面
     *
     * @return view
     */
    @GetMapping("/updatePassword.html")
    public String toUpdatePassword() {
        return "/user/updatePassword.jsp";
    }

    /**
     * 用户发布的租房总信息
     *
     * @return view
     */
    @GetMapping("/userRental.html")
    public String toUserRentalPage() {
        return "/user/myRental.jsp";
    }

    /**
     * 用户更新房源信息
     * 使用的也是管理员的界面
     *
     * @param houseId 房源ID
     * @param request req
     * @return view
     */
    @GetMapping("/updateHouse.html")
    public String toUpdatePage(int houseId, HttpServletRequest request) {
        House house = houseService.findHouseDetailsById(houseId);
        request.getSession().setAttribute("House", house);
        //解析简介图
        request.getSession().setAttribute("briefImage", OSSUtils.getImage(house.getHouseImage()));
        //解析详情图片
        List<String> DetailsImgList = FileUtils.parseHouseDetailsImg(house.getHouseDetailsImg());
        request.getSession().setAttribute("DetailsImg", DetailsImgList);
        //解析隐私图片
        List<String> privacyImgList = FileUtils.parseHouseDetailsImg(house.getHousePrivacyImg());
        request.getSession().setAttribute("privacyImg", privacyImgList);
        return "/user/updateHouse.jsp";
    }

    /**
     * 更新用户密码
     * 需求：这里需要检测 old password 是否正确，用 checkUser 确认是否正确
     *
     * @param id     id
     * @param newPwd new password
     * @param oldPwd old password
     * @returnupdateHouse res
     */
    @PostMapping("/updateUserPwd")
    @ResponseBody
    public String updateUserPwd(String id, String newPwd, String oldPwd) {
        User oldUser = new User();
        oldUser.setUserId(Integer.parseInt(id));
        oldUser.setUserPassword(oldPwd);
        User checkUser = userService.checkOldPwd(oldUser);//检测 old password 是否正确
        if (checkUser != null) {
            User newUser = new User();
            newUser.setUserId(Integer.parseInt(id));
            newUser.setUserPassword(newPwd);
            int result = userService.updateUserPwd(newUser);
            if (result > 0) {
                return "OK";
            }
        }
        return "FAIL";
    }

    /**
     * 查看用户发布的房屋信息
     *
     * @param houseId
     * @return
     */
    @GetMapping("/myRentalDetail.html")
    public String myRentalDetail(int houseId, HttpServletRequest request) {
        House house = houseService.findHouseDetailsById(houseId);
        request.getSession().setAttribute("House", house);
        //解析简介图
        request.getSession().setAttribute("briefImage", OSSUtils.getImage(house.getHouseImage()));
        //解析详情图片
        List<String> DetailsImgList = FileUtils.parseHouseDetailsImg(house.getHouseDetailsImg());
        request.getSession().setAttribute("DetailsImg", DetailsImgList);
        //解析隐私图片
        List<String> privacyImgList = FileUtils.parseHouseDetailsImg(house.getHousePrivacyImg());
        request.getSession().setAttribute("privacyImg", privacyImgList);
        return "/user/myRentalDetail.jsp";
    }
}
