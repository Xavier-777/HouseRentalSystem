package com.house.interceptor;

import com.house.pojo.entity.Admin;
import com.house.pojo.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * @author chriy
 */
public class UserInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        response.setCharacterEncoding("utf-8");
        String requestUrl = request.getRequestURL().toString();

        // 检查 session
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        Admin admin = (Admin) session.getAttribute("Admin");
        if (user != null || admin != null) {
            return true;
        }

        //api白名单
        List<String> filterUserList = Arrays.asList("/index.html", "/user/login", "/user/register", "/detail.html",
                "/fuzzy", "/priceAsc", "/priceDesc", "/admin/", "/admin/index.html", "/admin/adminAccess", "/findHouseByHouseFilter",
                "/toIndex.html", "/resetFilter");
        for (String url : filterUserList) {
            if (requestUrl.contains(url)) {
                return true;
            }
        }

        response.sendRedirect("/");
        return false;
    }
}
