package com.hc.resume_backend.service.impl;


import com.hc.resume_backend.service.ObsService;
import com.obs.services.ObsClient;
import com.obs.services.model.ObsObject;
import com.obs.services.model.PutObjectRequest;
import org.apache.poi.util.IOUtils;

import java.io.*;

/**
 * @author Judy
 * @create 2023-06-15-19:19
 */
public class ObsServiceImpl implements ObsService {
    String endPoint = "https://obs.cn-south-1.myhuaweicloud.com";
    String ak = "LCXYX7LWVRLANEQ8NSB6";
    String sk = "y2FktAfEm23kXfVtK810rWKRap5JEkZqXLVTr5Jl";
    //对象链接地址格式为：https://桶名.域名/文件夹目录层级/对象名


    @Override
    public void saveData(String Key,byte[] bytes) throws FileNotFoundException {
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        // localfile为待上传的本地文件路径，需要指定到具体的文件名
        obsClient.putObject("zhuyifan", Key, new ByteArrayInputStream(bytes));
    }

    @Override
    public byte[] getData(String Key) throws IOException {
        // 创建ObsClient实例
        final ObsClient obsClient = new ObsClient(ak, sk, endPoint);

        ObsObject obsObject = obsClient.getObject("zhuyifan", Key);
        // 读取对象内容
        InputStream input = obsObject.getObjectContent();

        byte[] bytes = IOUtils.toByteArray(input);
        input.close();
        return bytes;
    }


    /**
     * 将Byte数组转换成文件
     * @param bytes byte数组
     * @param filePath 文件路径  如 D:\\Users\\Downloads\\
     * @param fileName  文件名
     */
    public static void fileToBytes(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {

            file = new File(filePath + fileName);
            if (!file.getParentFile().exists()){
                //文件夹不存在 生成
                file.getParentFile().mkdirs();

            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
