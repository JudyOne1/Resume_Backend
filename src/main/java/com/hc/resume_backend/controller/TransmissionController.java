package com.hc.resume_backend.controller;

import com.hc.resume_backend.server.TransmissionServer;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
    @ApiOperation("/测试用的，不用管")
    public void doDeepin() throws IOException {
        transmissionServer.sendMessage("hello");
    }
}
