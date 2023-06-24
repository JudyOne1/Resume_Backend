//package com.hc.resume_backend.config;
//
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.http.HttpSession;
//import javax.websocket.HandshakeResponse;
//import javax.websocket.server.HandshakeRequest;
//import javax.websocket.server.ServerEndpointConfig;
//
//@Configuration
//public class GetHttpSessionConfig extends ServerEndpointConfig.Configurator {
//    @Override
//    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
//        //获取httpSession
//        HttpSession httpSession = (HttpSession) request.getHttpSession();
//        //保存httpSession，将其存储到配置对象中
//        sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
//    }
//}