package com.house.controller.house;

import com.house.constant.HouseStatus;
import com.house.pojo.entity.House;
import com.house.pojo.entity.HouseFilter;
import com.house.service.IHouseService;
import com.house.utils.OSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页控制类
 *
 * @author chriy
 */
@Controller
public class HomeController {

    @Autowired
    private IHouseService houseService;

    /**
     * 首页
     *
     * @param request request
     * @return view
     */
    @GetMapping({"/index.html", "/"})
    public String index(HttpServletRequest request) {
        List<House> findHomeInfo = houseService.findHomeInfo();
        List<House> passHouses = new ArrayList<>();
        //只放行通过审核的
        for (House house : findHomeInfo) {
            if (house.getHouseStatus().equals(HouseStatus.pass.toString())) {
                house.setHouseImage(OSSUtils.getImage(house.getHouseImage()));
                passHouses.add(house);
            }
        }
        request.getSession().setAttribute("House", passHouses);
        return "index/index.jsp";
    }

    /**
     * 首页搜索
     * 模糊查询：几室几厅、描述信息、社区名
     *
     * @param request  request
     * @param keywords keywords
     * @return res
     */
    @PostMapping("/fuzzy")
    public String findHouseByLike(HttpServletRequest request, String keywords) {
        List<House> findHomeInfo = houseService.findHouseByLike(keywords);
        request.getSession().removeAttribute("House");
        request.getSession().setAttribute("House", findHomeInfo);
        return "index/index.jsp";
    }

    /**
     * 筛选框筛选
     *
     * @param request
     * @param houseFilter
     * @return
     */
    @PostMapping("/findHouseByHouseFilter")
    @ResponseBody
    public String findHouseByHouseFilter(HttpServletRequest request, HouseFilter houseFilter) {
        request.getSession().setAttribute("HouseFilter", houseFilter);
        //解析价格
        if (!houseFilter.getHousePriceLimit().equals("null")) {
            String[] strings = houseFilter.getHousePriceLimit().split("-");
            houseFilter.setHousePriceMin(Integer.valueOf(strings[0]));
            houseFilter.setHousePriceMax(Integer.valueOf(strings[1]));
        }
        List<House> houseList = houseService.findTargetHouses(houseFilter);
        List<House> passHouses = new ArrayList<>();
        for (House house : houseList) {
            if (house.getHouseStatus().equals(HouseStatus.pass.toString())) {
                house.setHouseImage(OSSUtils.getImage(house.getHouseImage()));
                passHouses.add(house);
            }
        }
        request.getSession().setAttribute("House", passHouses);
        return "OK";
    }

    @GetMapping("/toIndex.html")
    public String toIndex() {
        return "index/index.jsp";
    }

    /**
     * 筛选框的重置按钮
     *
     * @param request
     * @return
     */
    @GetMapping("/resetFilter")
    public String resetFilter(HttpServletRequest request) {
        request.getSession().removeAttribute("HouseFilter");
        return "index/index.jsp";
    }

    /**
     * 价格升序查询
     *
     * @param request request
     * @return res
     */
    @GetMapping("/priceAsc")
    public String findPriceAsc(HttpServletRequest request) {
        List<House> findHomeInfo = houseService.findHouseOrderByAsc();
        List<House> passHouses = new ArrayList<>();
        for (House house : findHomeInfo) {
            if (house.getHouseStatus().equals(HouseStatus.pass.toString())) {
                house.setHouseImage(OSSUtils.getImage(house.getHouseImage()));
                passHouses.add(house);
            }
        }
        request.getSession().setAttribute("House", passHouses);
        return "OK";
    }

    /**
     * 价格降序查询
     *
     * @param request request
     * @return res
     */
    @GetMapping("/priceDesc")
    public String findPriceDesc(HttpServletRequest request) {
        List<House> findHomeInfo = houseService.findHouseOrderByDesc();
        List<House> passHouses = new ArrayList<>();
        for (House house : findHomeInfo) {
            if (house.getHouseStatus().equals(HouseStatus.pass.toString())) {
                house.setHouseImage(OSSUtils.getImage(house.getHouseImage()));
                passHouses.add(house);
            }
        }
        request.getSession().setAttribute("House", passHouses);
        return "index/index.jsp";
    }
}
