package com.hc.resume_backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 *
 *  在打包运行在外部服务器时，将该类中的@Configuration\ @Bean 注解去掉
 *  本地运行时，需要上述注释 ???
 *
 * @author Judy
 * @create 2023-06-20-18:45
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig {
    @Bean
    //注入ServerEndpointExporter，自动注册使用@ServerEndpoint注解的类
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }

}
