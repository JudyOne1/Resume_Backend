package com.hc.resume_backend.controller;

import com.hc.resume_backend.common.BaseResponse;
import com.hc.resume_backend.common.ErrorCode;
import com.hc.resume_backend.common.ResultUtils;
import com.hc.resume_backend.model.dto.file.FileDownloadRequest;
import com.hc.resume_backend.model.dto.file.FileUploadRequest;
import com.hc.resume_backend.service.ObsService;
import com.hc.resume_backend.utils.Base64ToMultipartFile;
import com.hc.resume_backend.utils.UuidUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @author Judy
 * @create 2023-06-15-21:04
 */
@RestController
@RequestMapping("/upload")
@Slf4j
@Api("上传相关接口")
public class UploadController {

    @Autowired
    private ObsService obsService;

    @ApiOperation(value = "上传文件")
    @PostMapping("/uploadFile")
    @ResponseBody
    @ApiParam(name = "base64File", value = "base64格式的文件", required = true)
    public BaseResponse uploadFile(@RequestBody FileUploadRequest File) throws IOException, DocumentException {
        //base64File格式类似于： data:image/gif;base64,R0lGODlhHA
        //data:application/pdf;base64,
        //data:text/plain;base64,
        //data:application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64,

        String base64File = File.getBase64File();

        final String[] base64Array = base64File.split(",");
        // base64转为流
        String dataUir = base64Array[0];
        String data = base64Array[1];
        Base64ToMultipartFile multipartFile = new Base64ToMultipartFile(data, dataUir);
        //获取格式类型
        String extension = multipartFile.getExtension();
        log.warn("格式类型"+extension);
        if (extension.equals("plain")){
            //如果是文本
            extension="txt";
        }
        if (extension.equals("vnd.openxmlformats-officedocument.wordprocessingml.document")){
            //如果是docx
            extension="docx";
        }
        if (ObjectUtils.isEmpty(multipartFile) || multipartFile.getSize() <= 0) {
//            System.out.println("文件为空,请检查");
            return ResultUtils.error(ErrorCode.FILEMISS_ERROR,"文件为空,请检查");
        }

        Long uuid = UuidUtils.getId();
        if (extension.equals("png")||extension.equals("jpeg")){
            //转换成pdf
            log.warn("处理了"+extension+"格式的数据");
            MultipartFile PDFmultipartFile = convertToPDF(multipartFile,extension);
            obsService.saveData(uuid+".pdf",PDFmultipartFile.getBytes());
            return ResultUtils.success("success");
        }
        if (extension.equals("txt")){
            //转换成word
            log.warn("处理了"+extension+"格式的数据");
            MultipartFile WORDmultipartFile = convertToWord(multipartFile);
            obsService.saveData(uuid+".docx",WORDmultipartFile.getBytes());
            return ResultUtils.success("success");
        }

        //上传到obs对象云存储中
//        UUID uuid = UUID.randomUUID();
        obsService.saveData(uuid+"."+extension,multipartFile.getBytes());

        return ResultUtils.success("success");
    }
    public static MultipartFile convertToWord(MultipartFile txtFile) throws IOException {
        // 读取文本内容
        String text = new String(txtFile.getBytes());

        // 创建Word文档对象
        XWPFDocument document = new XWPFDocument();

        // 插入文本内容
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(text);

        // 将Word文档保存为文件
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        document.write(outputStream);
        byte[] byteArray = outputStream.toByteArray();

        return new MockMultipartFile("convertedFile.docx", byteArray);
    }

    public static MultipartFile convertToPDF(MultipartFile pngFile,String extension) throws IOException, DocumentException {
        /*if (extension.equals("txt")) {
            try {
                File tempFile = File.createTempFile("temp", "." + extension);
                pngFile.transferTo(tempFile);
                // 读取文本文件内容
                BufferedReader reader = new BufferedReader(new FileReader(tempFile));
                StringBuilder textContent = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    textContent.append(line).append("\n");
                }
                reader.close();

                // 创建BufferedImage对象
                int width = 400; // 图像宽度
                int height = 200; // 图像高度
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

                // 绘制文本到图像
                Graphics2D graphics = image.createGraphics();
                graphics.setColor(Color.WHITE); // 设置背景色
                graphics.fillRect(0, 0, width, height); // 填充背景色
                graphics.setColor(Color.BLACK); // 设置文本颜色
                Font font = new Font("Arial", Font.BOLD, 16); // 设置字体
                graphics.setFont(font);
                graphics.drawString(textContent.toString(), 10, 20); // 绘制文本
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                // 将图像保存为文件
                ImageIO.write(image, "png", outputStream);
                byte[] pngBytes = outputStream.toByteArray();
                pngFile = new MockMultipartFile("file.png", pngBytes);
                extension = "png";
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (IllegalStateException e) {
                throw new RuntimeException(e);
            }
        }*/

        File tempFile = File.createTempFile("temp", "."+extension);
        pngFile.transferTo(tempFile);

        File pdfFile = File.createTempFile("temp", ".pdf");

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
        document.open();

        com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(tempFile.getAbsolutePath());
        image.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight());
        document.add(image);

        document.close();
        tempFile.delete();

        FileInputStream input = new FileInputStream(pdfFile);
        MultipartFile multipartFile = new MockMultipartFile("file", pdfFile.getName(), "application/pdf", input);

        return multipartFile;
    }




    @ApiOperation(value = "下载文件")
    @PostMapping("/downloadFile")
    @ResponseBody
    public BaseResponse downloadFile(@RequestBody FileDownloadRequest fileDownloadRequest) throws IOException {
        String url = obsService.getData(fileDownloadRequest.getPid());
        if (url==null){
            return ResultUtils.error(ErrorCode.FILEMISS_ERROR,"此文件由数据集导入，无法下载");
        }
        return ResultUtils.success(url);
    }


}
