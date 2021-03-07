package com.house.controller.admin;

import com.house.pojo.dto.UserData;
import com.house.pojo.dto.UserHouseData;
import com.house.pojo.entity.Admin;
import com.house.pojo.entity.House;
import com.house.pojo.entity.Page;
import com.house.pojo.entity.User;
import com.house.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 管理员控制器
 *
 * @author chriy
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IAdminService adminService;

    /**
     * 登录请求
     *
     * @param userName     用户名
     * @param userPassword 密码
     * @param req          req
     * @return res
     */
    @PostMapping("/adminAccess")
    public String adminAccess(String userName, String userPassword, HttpServletRequest req) {
        Admin admin = new Admin(0, userName, userPassword);
        Admin adminAccess = adminService.adminAccess(admin);
        req.getSession().setAttribute("Admin", adminAccess);
        if (adminAccess != null) {
            return "OK";
        }
        return "FAIL";
    }

    /**
     * 查询所有用户
     *
     * @return res
     */
    @GetMapping("/allUser")
    public UserData findAllUser() {
        List<User> findAllUser = adminService.findAllUser();
        UserData userData = new UserData();
        userData.setCode(0);
        userData.setCount(findAllUser.size());
        userData.setData(findAllUser);
        userData.setMsg("OK");
        return userData;
    }

    /**
     * 更新用户信息
     *
     * @param user user
     * @return res
     */
    @PostMapping("/editUser")
    public String editUser(User user) {
        int result = adminService.updateUser(user);
        if (result > 0) {
            return "OK";
        }
        return "FAIL";
    }

    /**
     * 查询所有房源
     * 用了分页，page、limit是前端的layui直接发到后端的
     *
     * @param page  page    第几页
     * @param limit limit   每页显示几条，默认是10
     * @return res
     */
    @RequestMapping("/houseList")
    public UserHouseData findAllHouse(int page, int limit) {
        Page p = new Page();
        p.setLimit(limit);
        p.setPage((page - 1) * limit);
        List<House> findAllHouse = adminService.findAllHouse(p);
        UserHouseData data = new UserHouseData();
        data.setCode(0);
        data.setCount(findAllHouse.size());
        data.setData(findAllHouse);
        data.setMsg("OK");
        return data;
    }

    /**
     * 删除房源
     *
     * @param houseId 房源id
     * @return res
     */
    @RequestMapping("/deleteHouse")
    public String deleteHouse(int houseId) {
        int result = adminService.deleteHouse(houseId);
        if (result > 0) {
            return "OK";
        }
        return "FAIL";
    }

    /**
     * 管理员删除用户
     */
    @PostMapping("/deleteUser")
    public String deleteUser(Integer userId) {
        int result = adminService.deleteUser(userId);
        if (result > 0) {
            return "OK";
        }
        return "FAIL";
    }

    /**
     * 修改管理员登录密码
     *
     * @param request    req
     * @param oldPwd     旧密码
     * @param newPwd     新密码
     * @param confirmPwd 确认密码
     * @return res
     */
    @PostMapping("/changePassword")
    public String changePassword(HttpServletRequest request, String oldPwd, String newPwd, String confirmPwd) {
        Admin checkAdmin = new Admin();
        Admin adminSession = (Admin) request.getSession().getAttribute("Admin");
        checkAdmin.setId(adminSession.getId());
        checkAdmin.setUserPassword(oldPwd);
        // 检查 oldPwd 是否正确
        Admin checkAdminPwd = adminService.checkAdminPwd(checkAdmin);
        if (checkAdminPwd == null) {
            return "ERROR";
        }
        // 两次密码校验
        if (!newPwd.equals(confirmPwd)) {
            return "FAIL";
        }
        Admin admin = new Admin();
        admin.setId(adminSession.getId());
        admin.setUserPassword(newPwd);
        int result = adminService.changePassword(admin);
        if (result > 0) {
            return "OK";
        }
        return "FAIL";
    }
}
