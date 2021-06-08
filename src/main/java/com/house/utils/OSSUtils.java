package com.house.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public class OSSUtils {

    public final static String endpoint = "";
    public final static String accessKeyId = "";
    public final static String accessKeySecret = "";
    public final static String bucket = "";

    /**
     * 图片上传
     *
     * @param inputStream 文件流
     * @param filename    文件名
     */
    public static void fileUpload(InputStream inputStream, String filename) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 填写Bucket名称和Object完整路径。Object完整路径中不能包含Bucket名称。
        ossClient.putObject(bucket, filename, inputStream);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 图片在线观看
     *
     * @param fileName 图片的名
     * @return
     */
    public static String getImage(String fileName) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 设置URL过期时间为7天。
        Date expiration = new Date(new Date().getTime() + 1000 * 3600 * 24 * 7);
        // 生成以GET方法访问的签名URL，访客可以直接通过浏览器访问相关内容。
        URL url = ossClient.generatePresignedUrl(bucket, fileName, expiration);
        // 关闭OSSClient。
        ossClient.shutdown();
        return url.toString();
    }
}
