package com.hc.resume_backend.model.dto.deepin;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @version v1.0
 * @ClassName: FileMessage
 * @Description: 用于封装浏览器发送给服务端的消息数据
 */
@Data
@AllArgsConstructor
public class FileMessage {
    private Long pid;
    private String url;

}