package com.hc.resume_backend;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hc.resume_backend.mapper.BaseinfoMapper;
import com.hc.resume_backend.model.entity.Baseinfo;
import com.hc.resume_backend.model.entity.Taginfo;
import com.hc.resume_backend.model.entity.Uploadfileinfo;
import com.hc.resume_backend.service.BaseinfoService;
import com.hc.resume_backend.service.DetailinfoService;
import com.hc.resume_backend.service.TaginfoService;
import com.hc.resume_backend.service.UploadfileinfoService;
import com.huaweicloud.sdk.core.auth.BasicCredentials;
import com.huaweicloud.sdk.core.auth.ICredential;
import com.huaweicloud.sdk.core.exception.ConnectionException;
import com.huaweicloud.sdk.core.exception.RequestTimeoutException;
import com.huaweicloud.sdk.core.exception.ServiceResponseException;
import com.huaweicloud.sdk.vpc.v3.VpcClient;
import com.huaweicloud.sdk.vpc.v3.model.ListVpcsRequest;
import com.huaweicloud.sdk.vpc.v3.model.ListVpcsResponse;
import com.huaweicloud.sdk.vpc.v3.region.VpcRegion;
import com.obs.services.ObsClient;
import com.obs.services.model.ObsObject;
import org.apache.commons.io.FileUtils;
import org.apache.poi.util.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class ResumeBackendApplicationTests {

    @Autowired
    private BaseinfoService baseinfoService;

    @Autowired
    private DetailinfoService detailinfoService;

    @Resource
    private BaseinfoMapper baseinfoMapper;

    @Autowired
    private TaginfoService taginfoService;

    @Resource
    private UploadfileinfoService uploadfileinfoService;

    @Test
    void contextLoads() throws IOException {
//        QueryWrapper<Baseinfo> queryWrapper = new QueryWrapper<>();
//        QueryWrapper<Baseinfo> age = queryWrapper.select("age");
//
//        baseinfoService.list(queryWrapper);
//
//        queryWrapper = queryWrapper.select("level");
//        List<Baseinfo> list = baseinfoService.list(queryWrapper);
//        HashMap<String, ArrayList<String>> finalMap = new HashMap<>();
//        ArrayList<String> helper = new ArrayList<>();
//        list.stream().forEach((item)->helper.add(item.getLevel()));
//        System.out.println(helper);


//        ArrayList<Baseinfo> baseinfos = (ArrayList<Baseinfo>) baseinfoMapper.selectList(null);
//
//        HashMap<String, ArrayList<String>> map = new HashMap<>();
//        ArrayList<String> helper = new ArrayList<>();
//        baseinfos.stream().forEach((item)->helper.add(item.getAge().toString()));
//        map.put("age",helper);
//        helper.clear();
//        baseinfos.stream().forEach((item)->helper.add(item.getLevel()));
//        map.put("level",helper);
//        helper.clear();
//        baseinfos.stream().forEach((item)->helper.add(item.getWorkyears()));
//        map.put("workYears",helper);
//        System.out.println(map.toString());

//        ArrayList<Baseinfo> baseinfos = (ArrayList<Baseinfo>) baseinfoMapper.selectList(null);
//
//        HashMap<String, ArrayList<String>> map = new HashMap<>();
//        ArrayList<String> helper = new ArrayList<>();
//        baseinfos.forEach((item)->helper.add(item.getAge().toString()));
//        baseinfos.stream().forEach((item)->helper.add(item.getAge().toString()));
//        map.put("age",helper);
//
//        ArrayList<String> helper1 = new ArrayList<>();
//        baseinfos.stream().forEach((item)->helper1.add(item.getLevel()));
//        map.put("level",helper1);
//
//        ArrayList<String> helper2 = new ArrayList<>();
//        baseinfos.stream().forEach((item)->helper2.add(item.getWorkyears()));
//        map.put("workYears",helper2);
//        System.out.println(map.toString());

//        System.out.println("-------------------------------------");
//        QueryWrapper<Taginfo> taginfoQueryWrapper = new QueryWrapper<>();
//        //SELECT * FROM `taginfo` WHERE id = 1 or id = 2
//        taginfoQueryWrapper.eq("id",1).or().eq("id",2);
//        List<Taginfo> list = taginfoService.list(taginfoQueryWrapper);
//        System.out.println(list);



//        String path = "D:\\textobsdownload\\";
//        LambdaQueryWrapper<Uploadfileinfo> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(Uploadfileinfo::getHandle,0);
//        List<Uploadfileinfo> list = uploadfileinfoService.list(queryWrapper);
//        List<String> urlList = list.stream().map(Uploadfileinfo::getObsurl).collect(Collectors.toList());
//        for (String url1 : urlList) {
//            System.out.println(url1);
//            try {
//                URL url = new URL(url1);
//                URLConnection connection = url.openConnection();
//
//                try (InputStream inputStream = connection.getInputStream();
//                     FileOutputStream outputStream = new FileOutputStream(path+"111.jpeg")) {
//                    byte[] buffer = new byte[4096];
//                    int bytesRead;
//
//                    while ((bytesRead = inputStream.read(buffer)) != -1) {
//                        outputStream.write(buffer, 0, bytesRead);
//                    }
//
//                    System.out.println("文件下载完成！");
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

        String path = "D:\\textobsdownload\\222.jpeg";
        URL url = new URL("https://zhuyifan.obs.cn-south-1.myhuaweicloud.com:443/f25ed0cc-bb3a-456d-95e9-69e4e1ed4083.jpeg");
        File File = new File(path);
        FileUtils.copyURLToFile(url, File);

    }

    public String uploadToLocal(MultipartFile file){
        // 获取文件原本的名字
        String originName = file.getOriginalFilename();
        // 判断文件是否是pdf文件
        Set<String> set = new HashSet<>();
        set.add(".pdf");
        set.add(".doc");
        set.add(".docx");
        // 取出文件的后缀
        int count = 0;
        for(int i = 0; i < originName.length(); i++){
            if(originName.charAt(i) == '.'){
                count = i;
                break;
            }
        }
        String endName = originName.substring(count); //取出文件类型
        String fileType = originName.substring(count + 1); //文件类型
        if(!set.contains(endName)){
            return new String("上传的文件类型错误,只能上传pdf,doc,docx类型的文件");
        }
        // 创建保存路径
        //日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String format = sdf.format(new Date());
        String savePath = System.getProperty("user.dir") + "\\" + "files" +   "\\" + fileType + "\\" + format;
        // 保存文件的文件夹
        File folder = new File(savePath);
        // 判断路径是否存在,不存在则自动创建
        if(!folder.exists()){
            folder.mkdirs();
        }
        String saveName = originName;
        try {
            file.transferTo(new File(folder,saveName));
            String filePath = savePath + "\\" + saveName;
            return new String("文件路径为:" + filePath);
        } catch (IOException e){
            return new String(e.getMessage());
        }
    }

    @Test
    void testObsConnect(){
        // 配置认证信息
        ICredential auth = new BasicCredentials()
                .withAk("LCXYX7LWVRLANEQ8NSB6")
                .withSk("y2FktAfEm23kXfVtK810rWKRap5JEkZqXLVTr5Jl");

        // 创建服务客户端
        VpcClient client = VpcClient.newBuilder()
                // 配置认证信息
                .withCredential(auth)
                // 配置地区, 如果地区不存在会抛出IllegalArgumentException
                .withRegion(VpcRegion.valueOf("cn-south-1"))
                .build();

        // 发送请求并获取响应
        ListVpcsRequest request = new ListVpcsRequest();
        try {
            ListVpcsResponse response = client.listVpcs(request);
            System.out.println(response.toString());
        } catch (ConnectionException | RequestTimeoutException e) {
            e.printStackTrace();
        } catch (ServiceResponseException e) {
            e.printStackTrace();
            System.out.println(e.getHttpStatusCode());
            System.out.println(e.getRequestId());
            System.out.println(e.getErrorCode());
            System.out.println(e.getErrorMsg());
        }
    }

    @Test
    void testObsSAVE(){
        String endPoint = "https://obs.cn-south-1.myhuaweicloud.com";
        String ak = "LCXYX7LWVRLANEQ8NSB6";
        String sk = "y2FktAfEm23kXfVtK810rWKRap5JEkZqXLVTr5Jl";
// 创建ObsClient实例
        ObsClient obsClient = new ObsClient(ak, sk, endPoint);

        String path = "C:\\Users\\Judy\\Desktop\\picture\\b7fd5266d0160924ab1869ec135322fae6cd7a899eb5.jfif";
// localfile为待上传的本地文件路径，需要指定到具体的文件名
        obsClient.putObject("zhuyifan", "test01.jfif", new File(path));
    }

    @Test
    void testObsGET() throws IOException {
        String endPoint = "https://obs.cn-south-1.myhuaweicloud.com";
        String ak = "LCXYX7LWVRLANEQ8NSB6";
        String sk = "y2FktAfEm23kXfVtK810rWKRap5JEkZqXLVTr5Jl";

// 创建ObsClient实例
        final ObsClient obsClient = new ObsClient(ak, sk, endPoint);

        ObsObject obsObject = obsClient.getObject("zhuyifan", "test01");
// 读取对象内容
        InputStream input = obsObject.getObjectContent();

        byte[] bytes = IOUtils.toByteArray(input);

//        fileToBytes(bytes,"C:\\Users\\Judy\\Desktop\\picture\\","test01.jfif");
        System.out.println();
        input.close();


    }
    /**
     * 将Byte数组转换成文件
     * @param bytes byte数组
     * @param filePath 文件路径  如 D:\\Users\\Downloads\\
     * @param fileName  文件名
     */
    public static void fileToBytes(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {

            file = new File(filePath + fileName);
            if (!file.getParentFile().exists()){
                //文件夹不存在 生成
                file.getParentFile().mkdirs();

            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
