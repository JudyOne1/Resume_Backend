package com.hc.resume_backend.model.dto.file;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @version v1.0
 * @ClassName: Message
 * @Description: 用于封装浏览器发送给服务端的消息数据
 */
@Data
@AllArgsConstructor
public class Message {
    private Long pid;
    private String url;

}