package com.hc.resume_backend.model.dto.redis;

import com.hc.resume_backend.model.dto.deepin.FileMessage;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Judy
 * @create 2023-06-25-18:47
 */
@Data
public class MessageRedisData {
    private FileMessage fileMessage;
    private LocalDateTime expireTime;
}
