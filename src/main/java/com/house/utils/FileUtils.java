package com.house.utils;

import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class FileUtils {

    /**
     * 将文件名转化为UUID的形式，但其后缀不变
     *
     * @param file
     * @return
     */
    public static String FileNameToUUID(MultipartFile file) {
        return UUID.randomUUID().toString().replace("-", "")
                + Objects.requireNonNull(file.getOriginalFilename())
                .substring(file.getOriginalFilename().lastIndexOf("."));
    }

    /**
     * 解析图片：将数据库的HouseDetailsImg转化为直接的图片url
     *
     * @param imgString
     * @return
     */
    public static List<String> parseHouseDetailsImg(String imgString) {
        if (imgString == null)
            return null;
        else {
            List<String> ImgList = new ArrayList<>();
            String[] imgFilename = imgString.split(":");
            for (String imgKey : imgFilename) {
                ImgList.add(OSSUtils.getImage(imgKey));
            }
            return ImgList;
        }
    }

    /**
     * 删除数据库中，房屋详细图片和隐私图片，通过删除掉对应的字符串即可
     *
     * @param imgString 数据库中图片的字符串
     * @param index     对应照片的索引值
     * @return
     */
    public static String deleteImgString(String imgString, int index) {
        if (imgString == null)
            return null;
        else {
            String[] imgFilename = imgString.split(":");
            String deleteTarget = imgFilename[index];
            String s = imgString.replaceAll(deleteTarget + ":", "");
            return s;
        }
    }

}
