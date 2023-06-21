package com.hc.resume_backend.utils;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.hc.resume_backend.model.dto.file.ResultMessage;

/**
 * @version v1.0
 * @ClassName: MessageUtils
 * @Description: 封装json格式消息的工具类
 * @Author: 黑马程序员
 */
public class MessageUtils {

    public static String getMessage(boolean isSystemMessage,String fromName, Object message) {

        ResultMessage result = new ResultMessage();
        result.setMessage(message);
        return JSONUtil.toJsonStr(result);
    }
}