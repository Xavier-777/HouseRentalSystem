package com.house.controller.house;

import com.house.constant.PathConst;
import com.house.pojo.entity.House;
import com.house.service.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 房屋详情控制类
 *
 * @author chriy
 */
@Controller
public class HouseDetailController {

    @Autowired
    private IHouseService houseService;

    /**
     * 房源详情
     * 问题：图片显示
     *
     * @param id      id
     * @param request req
     * @return view
     */
    @GetMapping("/detail.html")
    public String detail(int id, HttpServletRequest request) {
        System.out.println(PathConst.imgRealPath + "       " + PathConst.imgMappingPath);
        House details = houseService.findHouseDetailsById(id);
        List<String> list = new ArrayList<String>();
        String[] split = details.getHouseDetailsImg().split(":-:");
        for (int i = 0; i < split.length; i++) {
            list.add(split[i]);
            System.out.println(split[i]);
        }
        request.getSession().setAttribute("Details", details);
        request.getSession().setAttribute("DetailsImg", list);
        return "/user/houseDetails.jsp";
    }
}
