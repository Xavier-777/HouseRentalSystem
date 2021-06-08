package com.house.controller.house;

import com.house.constant.HouseStatus;
import com.house.pojo.entity.House;
import com.house.service.IHouseService;
import com.house.utils.FileUtils;
import com.house.utils.OSSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

/**
 * 添加房源信息控制类
 *
 * @author chriy
 */
@Controller
@RequestMapping("/house")
public class AddHouseController {

    @Autowired
    private IHouseService houseService;

    /**
     * 简介图名字
     */
    private String briefName = null;

    /**
     * 简介图流
     */
    private InputStream briefIS = null;

    /**
     * 详细图名字
     */
    private StringBuilder detailsPath = new StringBuilder();

    /**
     * 详细图片流
     * 图片名字 : 图片流
     */
    private Map<String, InputStream> detailsIS = new HashMap<>();

    /**
     * 个人信息名字
     */
    private StringBuilder privacyPath = new StringBuilder();

    /**
     * 个人信息图片流
     * 图片名字 : 图片流
     */
    private Map<String, InputStream> privacyIS = new HashMap<>();


    /**
     * 添加房源界面
     *
     * @return view
     */
    @GetMapping("/addHouse.html")
    public String addHouse() {
        return "addHouse.jsp";
    }

    /**
     * 简介图片上传：一张
     *
     * @param briefFile file
     * @return res
     */
    @RequestMapping("/briefImage")
    @ResponseBody
    public Map<String, Object> briefImage(@RequestParam("brief") MultipartFile briefFile) {
        Map<String, Object> map = new HashMap<>(16);
        try {
            briefName = FileUtils.FileNameToUUID(briefFile);
            briefIS = briefFile.getInputStream();
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
    @RequestMapping("/detailsImage")
    @ResponseBody
    public Map<String, Object> detailsImage(@RequestParam("detailsImage") List<MultipartFile> file) {
        Map<String, Object> map = null;
        String filename = null;

        try {
            //记录详情图片信息
            if (!file.isEmpty()) {
                map = new HashMap<>(16);

                for (MultipartFile f : file) {
                    filename = FileUtils.FileNameToUUID(f);         //名字重新命名
                    detailsPath.append(filename + ":");             //名字装入 detailsPath 中
                    detailsIS.put(filename, f.getInputStream());
                }
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
    @RequestMapping("/privacyImage")
    @ResponseBody
    public Map<String, Object> privacyImage(@RequestParam("privacyImage") List<MultipartFile> privacyFile) {
        Map<String, Object> map = null;
        String filename = null;

        try {
            //记录详情图片信息
            if (!privacyFile.isEmpty()) {
                map = new HashMap<>(16);

                for (MultipartFile f : privacyFile) {
                    filename = FileUtils.FileNameToUUID(f);         //名字重新命名
                    privacyPath.append(filename + ":");             //名字装入 detailsPath 中
                    privacyIS.put(filename, f.getInputStream());
                }
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
     * 添加新房源信息
     *
     * @param house 房源数据
     * @return res
     */
    @PostMapping("/addHouseRecord")
    @ResponseBody
    public String addHouse(House house) {
        if (house.getPublisher() == null || "".equals(house.getPublisher())) {
            house.setPublisher("管理员");
        }

        try {
            //上简介文件
            if (briefName != null) {
                OSSUtils.fileUpload(briefIS, briefName);
                house.setHouseImage(briefName);
            }

            //解析详细图并上传
            if (detailsPath.length() != 0) {
                String[] detailsName = detailsPath.toString().split(":");
                for (String detailName : detailsName) {
                    InputStream detailIS = detailsIS.get(detailName);
                    OSSUtils.fileUpload(detailIS, detailName);  //上传详细文件
                }
                house.setHouseDetailsImg(detailsPath.toString());
            }

            //解析隐私图
            if (privacyPath.length() != 0) {
                String[] privacyName = privacyPath.toString().split(":");
                for (String name : privacyName) {
                    InputStream privacy = privacyIS.get(name);
                    OSSUtils.fileUpload(privacy, name);       //上传个人信息图
                }
                house.setHousePrivacyImg(privacyPath.toString());
            }

            house.setHouseStatus(HouseStatus.unchecked.toString());

            //将图片的名字写入数据库
            int result = houseService.addNewHouse(house);

            // 若写入成功，置空上一次的添加记录
            if (result > 0) {
                briefName = null;
                detailsPath = new StringBuilder();
                privacyPath = new StringBuilder();
                return "OK";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "FAIL";
    }
}
