package com.house.controller.house;

import com.house.constant.PathConst;
import com.house.pojo.entity.House;
import com.house.service.IHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
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
     * 后期修改：想每10条加载一次
     *
     * @param request request
     * @return view
     */
    @GetMapping({"/index.html", "/"})
    public String index(HttpServletRequest request) {
        List<House> findHomeInfo = houseService.findHomeInfo();
        //给每一个图片都加上虚拟路径，以显示图片
        for (int i = 0; i < findHomeInfo.size(); i++) {
            House house = findHomeInfo.get(i);
            house.setHouseImage(PathConst.imgMappingPath + house.getHouseImage());
        }
        request.getSession().setAttribute("House", findHomeInfo);
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
     * 价格升序查询
     *
     * @param request request
     * @return res
     */
    @GetMapping("/priceAsc")
    public String findPriceAsc(HttpServletRequest request) {
        List<House> findHomeInfo = houseService.findHouseOrderByAsc();
        request.getSession().removeAttribute("House");
        request.getSession().setAttribute("House", findHomeInfo);
        return "index/index.jsp";
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
        request.getSession().removeAttribute("House");
        request.getSession().setAttribute("House", findHomeInfo);
        return "index/index.jsp";
    }
}
