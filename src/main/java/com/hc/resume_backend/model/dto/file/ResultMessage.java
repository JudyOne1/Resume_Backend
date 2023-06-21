package com.hc.resume_backend.model.dto.file;

import lombok.Data;

/**
 * @version v1.0
 * @ClassName: ResultMessage
 * @Description: 用来封装服务端给浏览器发送的消息数据
 */
@Data
public class ResultMessage {
    // todo 传输的消息类型是什么？
    private String message;
}