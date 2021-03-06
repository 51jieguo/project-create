package cn.yangtengfei.controller;


import cn.yangtengfei.util.ZipCompressor;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class IndexController {


    @Autowired
    private Configuration freemarkerConfiguration;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public void index(HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException, TemplateException {

        /*Map<String,Object> map = new HashMap<>();


        String path = this.getClass().getResource("/").toString();
        System.out.println("path = " + path);

        String filePath = path+"/sadasdasdsadasd";
        File file = new File(filePath);
        boolean bFile = file.mkdir();
        if(bFile){
            System.out.print("11111111111111111111111111");
        }else{
            System.out.print("1111123234243234");
        }*/

        httpServletResponse.setContentType("application/zip");
        httpServletResponse.setHeader("Content-disposition",
                "attachment; filename=" + new String("文件下载".getBytes(), "ISO-8859-1") + ".zip");





        //zipOutputStream.write();

        //String savePath = request.getServletContext().getRealPath("/");
        //String projectPath = savePath + File.separator + "myproject";
       /* File f = new File("D:/t");
        //f.mkdir();
        FileInputStream fis = new FileInputStream(f);
        byte[] buffer = new byte[2048];
        int readlength = 0;
        while((readlength = fis.read(buffer)) != -1){
            zipOutputStream.write(buffer,0,readlength);
        }*/

        /*OutputStream outputStream = httpServletResponse.getOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

        File F = new File("D://1231312321");
        F.mkdir();
        compress("D://t.zip",F.getAbsolutePath());


        zipOutputStream.flush();
        zipOutputStream.close();
        outputStream.flush();
        outputStream.close();*/
        //return "index";

        generateZipFile("D://","t",request,httpServletResponse);

    }

    public static void generateZipFile(String filePath,String artifactId,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        //生成压缩文件
        String fileName=filePath + File.separator +artifactId+".zip";
        ZipCompressor zipCompressor = new ZipCompressor(fileName);
        zipCompressor.compressExe(filePath + File.separator +artifactId);
        //response.addHeader("Content-Disposition", "attachment;filename="+artifactId+".zip");
        //response.addHeader("Content-Length", "" + new File(fileName).length());

        response.setContentType("application/zip");
        response.setHeader("Content-disposition",
                "attachment; filename=" + new String(artifactId.getBytes(), "ISO-8859-1") + ".zip");
        OutputStream out = null;
        InputStream in = null;
        try {
            in = new FileInputStream(fileName);
            out = response.getOutputStream();
            //写文件
            int b;
            while((b=in.read())!= -1)
            {
                out.write(b);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void downloadFile(String fileName, String tmpFileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        if(StringUtils.isEmpty(fileName) && StringUtils.isEmpty(tmpFileName)){
            return;
        }
        File file = new File(tmpFileName);
        if(!file.exists()) {
            return;
        }

        String postfix = tmpFileName.substring(tmpFileName.lastIndexOf("."));
        response.reset();
        String userAgent = request.getHeader("User-Agent");
        if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "utf-8") + postfix+"\"");
        } else {
            fileName = new String(fileName.getBytes("utf-8"),"ISO-8859-1"); // 下载的文件名显示编码处理
            response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + postfix+"\"");
        }
        response.setContentType("application/x-msdownload;charset=UTF-8");
        FileInputStream fis = new FileInputStream(file);
        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());

        byte[] buffer = new byte[2048];
        int readlength = 0;
        while((readlength = fis.read(buffer)) != -1){
            bos.write(buffer,0,readlength);
        }
        try {
            fis.close();
        } catch (IOException e) {
        }
        try {
            bos.flush();
            bos.close();
        } catch (IOException e) {
        }
    }


    private void createCode(Map<String, Object> model) throws IOException, TemplateException {
        StringBuffer content = new StringBuffer();
        content.append(FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfiguration.getTemplate("code/service.ftl"), model));
        System.out.print(content);
    }

    /**
     *
     * StringBuffer content = new StringBuffer();
     try
     {
     content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
     freemarkerConfiguration.getTemplate("mail.ftl"), model));
     return content.toString();
     }
     catch (Exception e)
     {
     log.error("Exception occured while processing fmtemplate:" + e.getMessage(), e);
     }
     return "";
     */

}
