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
     * 虚拟路径，显示图片必须要加上
     */
    private String simplePath = PathConst.imgMappingPath;

    /**
     * 房源详情
     *
     * @param id      id
     * @param request req
     * @return view
     */
    @GetMapping("/detail.html")
    public String detail(int id, HttpServletRequest request) {
        System.out.println(simplePath);
        House details = houseService.findHouseDetailsById(id);
        List<String> DetailsImgList = new ArrayList<String>();
        String[] imgFilename = details.getHouseDetailsImg().split(":-:");
        for (int i = 0; i < imgFilename.length; i++) {
            DetailsImgList.add(simplePath + imgFilename[i]);
        }
        request.getSession().setAttribute("Details", details);
        request.getSession().setAttribute("DetailsImg", DetailsImgList);
        return "/user/houseDetails.jsp";
    }
}
