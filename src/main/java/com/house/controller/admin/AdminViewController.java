package com.house.controller.admin;

import com.house.pojo.entity.House;
import com.house.pojo.entity.User;
import com.house.service.IAdminService;
import com.house.service.IHouseService;
import com.house.utils.FileUtils;
import com.house.utils.OSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 管理员界面视图跳转
 *
 * @author chriy
 */
@Controller
@RequestMapping("/admin")
public class AdminViewController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private IHouseService houseService;

    /**
     * 登录页
     *
     * @return view
     */
    @GetMapping({"/", "/index.html"})
    public String toAdminLogin() {
        return "/admin/login.jsp";
    }

    /**
     * 注销登录
     *
     * @param request req
     * @return view
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/index.html";
    }

    /**
     * 管理员首页
     *
     * @return view
     */
    @GetMapping("/home.html")
    public String toAdminHomePage() {
        return "/admin/home.jsp";
    }

    /**
     * 修改密码页
     *
     * @return view
     */
    @GetMapping("/changePassword.html")
    public String toChangePasswordPage() {
        return "/admin/changePassword.jsp";
    }

    /**
     * 查询所有用户页
     *
     * @return view
     */
    @GetMapping("/allUser.html")
    public String toAllUserPage() {
        return "/admin/allUser.jsp";
    }

    /**
     * 所有房源数据页
     *
     * @return view
     */
    @GetMapping("/houseList.html")
    public String toAllHousePage() {
        return "/admin/houseList.jsp";
    }

    /**
     * 传入id,跳转到修改用户界面
     *
     * @return view
     */
    @GetMapping("/editUser.html")
    public String toEditUserPage(int userId, HttpServletRequest req) {
        User findUserById = adminService.findUserById(userId);
        req.getSession().setAttribute("User", findUserById);
        return "/admin/editUser.jsp";
    }

    /**
     * 跳转到管理员更新房源界面
     *
     * @param houseId 房源ID
     * @param request req
     * @return view
     */
    @RequestMapping("/updateHouse.html")
    public String toUpdatePage(int houseId, HttpServletRequest request) {
        House house = houseService.findHouseDetailsById(houseId);
        request.getSession().setAttribute("House", house);
        return "/admin/updateHouse.jsp";
    }

    /**
     * 跳转到管理员审核
     *
     * @param houseId
     * @param request
     * @return
     */
    @RequestMapping("/checkHouse.html")
    public String toCheckPage(int houseId, HttpServletRequest request) {
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
        return "/admin/checkHouse.jsp";
    }
}
