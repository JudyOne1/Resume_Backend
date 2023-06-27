package com.hc.resume_backend.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hc.resume_backend.model.dto.deepin.FileMessage;
import com.hc.resume_backend.model.entity.Uploadfileinfo;
import com.hc.resume_backend.service.UploadfileinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 前端通知后端->往深度学习python传输数据
 * @author Judy
 * @create 2023-06-20-19:17
 */
@ServerEndpoint(value = "/transmission")
@Slf4j
@Component
public class TransmissionServer  {

    public static boolean FLAG = false;

    private static RedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        TransmissionServer.redisTemplate = redisTemplate;
    }

//    @Autowired // 注入失败
//    private UploadfileinfoService uploadfileinfoService;

    private static UploadfileinfoService uploadfileinfoService;
    @Autowired
    public void setUploadfileinfoService(UploadfileinfoService uploadfileinfoService) {
        TransmissionServer.uploadfileinfoService = uploadfileinfoService;
    }

    //所有的endpoint实例都用同一个map集合
    public static final Map<String,Session> onlineUser = new ConcurrentHashMap<>();
    //"resume:pid:174  { pid:   url:   expire:   }"
    public static final String redisKey = "resume:pid:";
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 建立websocket连接后，被调用
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) throws IOException {
        //将session进行保存
        onlineUser.put("python",session);
        this.session = session;
        log.error("有新连接");
        // 连接后调用方法发送文件 并且 修改标识，一上传就可以发送
        // 数据库中查询没处理过的文件 0
        ValueOperations valueOperations = redisTemplate.opsForValue();
        LambdaQueryWrapper<Uploadfileinfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Uploadfileinfo::getHandle,0);
        List<Uploadfileinfo> list = uploadfileinfoService.list(queryWrapper);
        ArrayList<FileMessage> fileMessages = new ArrayList<>();
        for (Uploadfileinfo uploadfileinfo : list) {
            FileMessage fileMessage = new FileMessage(uploadfileinfo.getPid(), uploadfileinfo.getObsurl());
            fileMessages.add(fileMessage);
        }

        ArrayList<FileMessage> result = fileMessages;

        //获得所有的pid
        List<Long> pids = fileMessages.stream().map(FileMessage::getPid).collect(Collectors.toList());
        //查询redis中是否存在,存在则已经发过去但是没接收到回复
        for (Long pid : pids) {
            Object value = valueOperations.get(redisKey + pid);
            if (value == null){
                //redis中没有，第一次发送,存入redis
                valueOperations.set(redisKey + pid,1, 5 ,TimeUnit.MINUTES);
            }else {
                //redis中有，发送后没收到回复，先等等，等过期，在fileMessages中剔除
                result = (ArrayList<FileMessage>) result.stream().filter(fileMessage -> {
                    if (Objects.equals(fileMessage.getPid(), pid)) {
                        return false;
                    } else {
                        return true;
                    }
                }).collect(Collectors.toList());
            }
        }
        //发送消息
        if (!result.isEmpty()){
            session.getBasicRemote().sendText(JSONUtil.toJsonStr(result));
        }
        FLAG = true;
    }

       /**
        * 实现服务器主动推送
        */
      public void sendMessage(String message) throws IOException {
          Session python = onlineUser.get("python");
          python.getBasicRemote().sendText(message);
      }

    @OnClose
    public void onClose(){
        log.error("连接关闭");

        FLAG = false;
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        // todo 接收数据 处理数据并且保存到数据库中 需要修改handle属性 redis不用管等过期
        log.error("收到消息");
        System.out.println(message);

    }
    @OnError
    public void onError(Session session, Throwable error){
        error.printStackTrace();
    }
}
// todo 部署dockerfile