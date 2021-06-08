package com.house.controller.house;

import com.house.pojo.dto.UserHouseData;
import com.house.pojo.entity.House;
import com.house.pojo.entity.Page;
import com.house.pojo.entity.User;
import com.house.service.IHouseService;
import com.house.utils.FileUtils;
import com.house.utils.OSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chriy
 */
@Controller
@RequestMapping("/house")
public class HouseController {

    @Autowired
    private IHouseService houseService;

    /**
     * 查找用户自己发布的房屋信息
     *
     * @param request
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping("/findUserHouse")
    @ResponseBody
    public UserHouseData houseByUser(HttpServletRequest request, int page, int limit) {
        Page p = new Page();
        User user = (User) request.getSession().getAttribute("loginUser");
        String publisher = user.getUserNickName();
        p.setPublisher(publisher);          //使用用户的NickName作为搜索条件
        p.setLimit(limit);
        p.setPage((page - 1) * limit);
        List<House> list = houseService.findHouseByUser(p);
        int count = houseService.findHouseCountByUser(p);
        return new UserHouseData(0, "200", count, list);
    }

    /**
     * 删除用户发布的房源信息
     *
     * @param houseId 房源 ID
     * @return res
     */
    @PostMapping("/deleteUserHouse")
    @ResponseBody
    public String deleteUserHouse(String houseId) {
        int result = houseService.deleteUserHouse(Integer.parseInt(houseId));
        if (result > 0) {
            return "OK";
        }
        return "FAIL";
    }

    /**
     * 更新房源信息
     *
     * @param house 房源数据
     * @return res
     */
    @PostMapping("/updateHouse")
    @ResponseBody
    public String updateHouse(House house) {
        int result = houseService.updateHouse(house);
        if (result > 0) {
            return "OK";
        }
        return "FAIL";
    }

    /**
     * 删除房屋详细图片
     *
     * @param houseId
     * @param index
     * @return
     */
    @PostMapping("/deleteHouseDetailsImg")
    @ResponseBody
    public String deleteHouseDetailsImg(int houseId, int index) {
        House house = houseService.findHouseDetailsById(houseId);
        String s = FileUtils.deleteImgString(house.getHouseDetailsImg(), index);
        house.setHouseDetailsImg(s);
        house.setHousePrivacyImg(null);
        int result=houseService.deleteHouseImg(house);
        if (result > 0) {
            return "OK";
        }
        return "FAIL";
    }

    /**
     * 删除隐私图
     *
     * @param houseId
     * @param index
     * @return
     */
    @PostMapping("/deleteHousePrivacyImg")
    @ResponseBody
    public String deleteHousePrivacyImg(int houseId, int index) {
        House house = houseService.findHouseDetailsById(houseId);
        String s = FileUtils.deleteImgString(house.getHousePrivacyImg(), index);
        house.setHousePrivacyImg(s);
        house.setHouseDetailsImg(null);
        int result = houseService.deleteHouseImg(house);
        if (result > 0) {
            return "OK";
        }
        return "FAIL";
    }


    /**
     * 审核房屋
     *
     * @param house
     * @return
     */
    @RequestMapping("/checkHouse")
    @ResponseBody
    public String checkHouse(House house) {
        int result = houseService.checkHouse(house);
        if (result > 0) {
            return "OK";
        }
        return "FAIL";
    }

    /**
     * 简介图片上传：一张
     *
     * @param briefFile file
     * @return res
     */
    @RequestMapping("/updateBriefImage")
    @ResponseBody
    public Map<String, Object> updateBriefImage(@RequestParam("brief") MultipartFile briefFile, int HouseId) {
        Map<String, Object> map = new HashMap<>(16);
        House house = new House();
        try {
            String briefName = FileUtils.FileNameToUUID(briefFile);
            InputStream briefIS = briefFile.getInputStream();
            OSSUtils.fileUpload(briefIS, briefName);
            house.setHouseId(HouseId);
            house.setHouseImage(briefName);
            houseService.updateHouseImg(house);
            //前端显示上传图片，直接用js显示
            map.put("image", null);
            map.put("code", 0);
            map.put("msg", "上传成功");
        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "上传失败");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 详情图片上传：多张
     *
     * @param file file
     * @return res
     */
    @RequestMapping("/updateDetailsImage")
    @ResponseBody
    public Map<String, Object> updateDetailsImage(@RequestParam("detailsImage") List<MultipartFile> file, int HouseId) {
        Map<String, Object> map = new HashMap<>(16);
        String filename;
        StringBuilder detailsPath = new StringBuilder();
        try {
            //记录详情图片信息
            if (!file.isEmpty()) {
                House house = new House();
                house.setHouseId(HouseId);
                for (MultipartFile f : file) {
                    filename = FileUtils.FileNameToUUID(f);         //名字重新命名
                    detailsPath.append(filename + ":");             //名字装入 detailsPath 中
                    OSSUtils.fileUpload(f.getInputStream(), filename);
                }
                house.setHouseDetailsImg(detailsPath.toString());
                houseService.updateHouseImg(house);
                map.put("code", 0);
                map.put("msg", "上传成功");
            }
        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "上传失败");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 证件图片上传：多张
     *
     * @param privacyFile
     * @return res
     */
    @RequestMapping("/updatePrivacyImage")
    @ResponseBody
    public Map<String, Object> updatePrivacyImage(@RequestParam("privacyImage") List<MultipartFile> privacyFile, int HouseId) {
        Map<String, Object> map = new HashMap<>(16);
        String filename;
        StringBuilder privacyPath = new StringBuilder();
        try {
            //记录详情图片信息
            if (!privacyFile.isEmpty()) {
                House house = new House();
                house.setHouseId(HouseId);
                for (MultipartFile f : privacyFile) {
                    filename = FileUtils.FileNameToUUID(f);         //名字重新命名
                    privacyPath.append(filename + ":");             //名字装入 detailsPath 中
                    OSSUtils.fileUpload(f.getInputStream(), filename);
                }
                house.setHousePrivacyImg(privacyPath.toString());
                houseService.updateHouseImg(house);
                map.put("code", 0);
                map.put("msg", "上传成功");
            }
        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "上传失败");
            e.printStackTrace();
        }
        return map;
    }
}
