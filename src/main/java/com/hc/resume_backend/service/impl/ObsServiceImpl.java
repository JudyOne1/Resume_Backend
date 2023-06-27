package com.hc.resume_backend.service.impl;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hc.resume_backend.mapper.UploadfileinfoMapper;
import com.hc.resume_backend.model.dto.deepin.FileMessage;
import com.hc.resume_backend.model.entity.Uploadfileinfo;
import com.hc.resume_backend.server.TransmissionServer;
import com.hc.resume_backend.service.ObsService;
import com.obs.services.ObsClient;
import com.obs.services.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.util.concurrent.TimeUnit;

import static com.hc.resume_backend.server.TransmissionServer.redisKey;

/**
 * @author Judy
 * @create 2023-06-15-19:19
 */
@Service
public class ObsServiceImpl implements ObsService {
    @Value("${app.data.bucketName}")
    String bucketName;
    @Value("${app.data.endPoint}")
    String endPoint;
    @Value("${app.data.ak}")
    String ak;
    @Value("${app.data.sk}")
    String sk;
    //对象链接地址格式为：https://桶名.域名/文件夹目录层级/对象名

    @Resource
    private UploadfileinfoMapper uploadfileinfoMapper;

    @Resource
    private RedisTemplate redisTemplate;

    @Resource
    private TransmissionServer transmissionServer;

    @Override
    @Transactional
    public void saveData(String Key,byte[] bytes) throws IOException {
        // 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);
        // localfile为待上传的本地文件路径，需要指定到具体的文件名
        PutObjectResult result = obsClient.putObject(bucketName, Key, new ByteArrayInputStream(bytes));
        String objectKey = result.getObjectKey();
        String objectUrl = result.getObjectUrl();
        Uploadfileinfo uploadfileinfo = new Uploadfileinfo();

        String[] split = Key.split("\\.");
        Long pid = Long.valueOf(split[0]);
//        Long pid = UuidUtils.getId();
        uploadfileinfo.setPid(pid);
        uploadfileinfo.setObsurl(objectUrl);
        uploadfileinfo.setResumekey(objectKey);
        uploadfileinfo.setHandle(0);
        uploadfileinfoMapper.insert(uploadfileinfo);

        //如果已经连接，那么直接发送message
        if (TransmissionServer.FLAG){
            FileMessage fileMessage = new FileMessage(pid, objectUrl);
            String str = JSONUtil.toJsonStr(fileMessage);
            redisTemplate.opsForValue().set(redisKey + pid,0, 5 , TimeUnit.MINUTES);
            transmissionServer.sendMessage(str);
        }
    }

    @Override
    public String getData(Long pid) throws IOException {
        //根据pid查url
        QueryWrapper<Uploadfileinfo> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",pid);
        Uploadfileinfo uploadfileinfo = uploadfileinfoMapper.selectOne(wrapper);
        String obsurl = uploadfileinfo.getObsurl();
        return obsurl;

//        // 创建ObsClient实例
//        final ObsClient obsClient = new ObsClient(ak, sk, endPoint);
//
//        ObsObject obsObject = obsClient.getObject(bucketName, Key);
//        // 读取对象内容
//        InputStream input = obsObject.getObjectContent();
//
//        byte[] bytes = IOUtils.toByteArray(input);
//        input.close();
//        return bytes;
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
