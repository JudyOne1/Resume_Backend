package com.hc.resume_backend.controller;

import cn.hutool.json.JSONObject;
import com.hc.resume_backend.model.dto.deepin.ResultMessage;
import com.hc.resume_backend.server.TransmissionServer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * @author Judy
 * @create 2023-06-20-21:34
 */
@RestController
@ResponseBody
@RequestMapping("/doDeepin")
public class TransmissionController {
//todo delete
    @Autowired
    private TransmissionServer transmissionServer;

    @PostMapping("/text")
    @ApiOperation("/测试连接用的，不用管")
    public void doDeepin() throws IOException {
        transmissionServer.sendMessage("hello");
    }

    @PostMapping("/message")
    @ApiOperation("/测试消息用的，不用管")
    public void message(@RequestBody ResultMessage resultMessage) throws IOException {

    }


}
