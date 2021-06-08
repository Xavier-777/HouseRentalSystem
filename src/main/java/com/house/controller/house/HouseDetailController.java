package com.house.controller.house;

import com.house.pojo.entity.House;
import com.house.service.IHouseService;
import com.house.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
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
     *
     * @param id      id
     * @param request req
     * @return view
     */
    @GetMapping("/detail.html")
    public String detail(int id, HttpServletRequest request) {
        House details = houseService.findHouseDetailsById(id);
        List<String> DetailsImgList = FileUtils.parseHouseDetailsImg(details.getHouseDetailsImg());
        request.getSession().setAttribute("Details", details);
        request.getSession().setAttribute("DetailsImg", DetailsImgList);
        return "/user/houseDetails.jsp";
    }
}
