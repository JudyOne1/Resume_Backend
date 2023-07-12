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
import org.apache.poi.util.IOUtils;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.spire.doc.*;

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
    public BaseResponse uploadFile(@RequestBody FileUploadRequest File) throws Exception {
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
        }else if (extension.equals("txt")){
            //转换成word
            log.warn("处理了"+extension+"格式的数据");
            MultipartFile WORDmultipartFile = convertToWord(multipartFile);
            //转换成pdf
            MultipartFile toPDF = word2pdf(WORDmultipartFile);
            obsService.saveData(uuid+".pdf",toPDF.getBytes());
            return ResultUtils.success("success");
        }else if (extension.equals("docx")){
            //转换成pdf
            MultipartFile toPDF = word2pdf(multipartFile);
            obsService.saveData(uuid+".pdf",toPDF.getBytes());
            return ResultUtils.success("success");
        }else {
            //上传到obs对象云存储中
//        UUID uuid = UUID.randomUUID();
            obsService.saveData(uuid+"."+extension,multipartFile.getBytes());
            return ResultUtils.success("success");
        }
    }

    private MultipartFile word2pdf(MultipartFile multipartFile) throws IOException {
        com.spire.doc.Document document = new com.spire.doc.Document(multipartFile.getInputStream());
        File pdfFile = new File("pdfFile");
        document.saveToFile(new FileOutputStream(pdfFile),FileFormat.PDF);
        return convertFileToMultipartFile(pdfFile);
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

//    public static MultipartFile convertDocxToPdf(MultipartFile docxFile) throws IOException {
//        //MultipartFile转File
//        File file = new File("tempFile"); // 创建File对象
//        docxFile.transferTo(file); // 将MultipartFile保存到本地临时文件中
//
//        // 创建临时文件用于存储转换后的内容
//        File tempPdfFile = File.createTempFile("converted", ".pdf");
//
//        try (FileOutputStream fos = new FileOutputStream(tempPdfFile);
//             XWPFDocument document = new XWPFDocument(OPCPackage.open(file))) {
//            // 从docx文件中提取文本内容
//            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
//            String text = extractor.getText();
//            System.out.println(text);
//
//            // 使用iText将文本内容转换为pdf
//            com.itextpdf.text.Document pdfDocument = new com.itextpdf.text.Document();
//            com.itextpdf.text.pdf.PdfWriter.getInstance(pdfDocument, fos);
//            pdfDocument.open();
//            pdfDocument.add(new com.itextpdf.text.Paragraph(text));
//            pdfDocument.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        //File转MultipartFile
//        FileInputStream input = new FileInputStream(tempPdfFile);
//        MultipartFile multipartFile = new MockMultipartFile(
//                tempPdfFile.getName(), tempPdfFile.getName(), "application/pdf", IOUtils.toByteArray(input));
//        input.close();
//        return multipartFile;
//    }




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


    private static File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(multipartFile.getBytes());
        fos.close();
        return file;
    }

    private static MultipartFile convertFileToMultipartFile(File file) throws IOException {
        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "application/pdf", IOUtils.toByteArray(input));
        input.close();
        return multipartFile;
    }

}
