package com.hc.resume_backend.service;

import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Judy
 * @create 2023-06-15-19:19
 */
@Service
public interface ObsService {

    /**
     * 保存数据到obs对象云存储中
     * @param Key 文件名
     * @param bytes 文件字节数据
     * @throws FileNotFoundException
     */
    public void saveData(String Key,byte[] bytes) throws FileNotFoundException;

    /**
     * 获取obs对象云存储中的数据
     * @param Key 文件名
     * @return 获得文件字节数据
     * @throws IOException
     */
    public byte[] getData(String Key) throws IOException;
}
