package com.hc.resume_backend.server;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
//import com.hc.resume_backend.config.GetHttpSessionConfig;
import com.hc.resume_backend.model.dto.file.FileMessage;
import com.hc.resume_backend.model.entity.Uploadfileinfo;
import com.hc.resume_backend.service.UploadfileinfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.sockjs.transport.session.WebSocketServerSockJsSession;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 前端通知后端->往深度学习python传输数据
 * @author Judy
 * @create 2023-06-20-19:17
 */

//@ServerEndpoint(value = "/transmission",configurator = GetHttpSessionConfig.class)
@ServerEndpoint(value = "/transmission")
@Slf4j
@Component
public class TransmissionServer  {

    public static boolean FLAG = false;

    @Resource
    private UploadfileinfoService uploadfileinfoService;

    //所有的endpoint实例都用同一个map集合
    public static final Map<String,Session> onlineUser = new ConcurrentHashMap<>();

    private HttpSession httpSession;

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
        //todo 万一断开了又重连怎么办？一直发送？【一致性问题】
//        this.httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
//        String python = (String) this.httpSession.getAttribute("python");
//        onlineUser.put(python,session);


        session.getBasicRemote().sendText("连接成功");



//        // 调用方法发送文件   链接后修改标识，一上传就发送
//        // 数据库中查询没处理过的文件0  发送到python端
//        LambdaQueryWrapper<Uploadfileinfo> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Uploadfileinfo::getHandle,0);
//        List<Uploadfileinfo> list = uploadfileinfoService.list(queryWrapper);
//        ArrayList<FileMessage> fileMessages = new ArrayList<>();
//        for (Uploadfileinfo uploadfileinfo : list) {
//            FileMessage fileMessage = new FileMessage(uploadfileinfo.getPid(), uploadfileinfo.getObsurl());
//            fileMessages.add(fileMessage);
//        }
//        //发送json数据
//        String str = JSONUtil.toJsonStr(fileMessages);
//        session.getBasicRemote().sendText(str);
//        FLAG = true;

    }

       /**
        * 实现服务器主动推送
        */
      public void sendMessage(String message) throws IOException {
        //this.session.getBasicRemote().sendText(message);
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
        // todo 接收数据 处理数据并且保存到数据库中 需要修改handle属性
        log.error("收到消息");
        session.getBasicRemote().sendText("服务端已收到信息");
        System.out.println(message);

    }
    @OnError
    public void onError(Session session, Throwable error){
        error.printStackTrace();
    }
}
