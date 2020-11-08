package com.example.demo;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/upload")
public class UploadController {
      @Value("${img.upload-path}")
      private String uploadPath2;
      @Value("${img.img-url}")
      private String imgUrl;

//    //单个文件上传
//    @RequestMapping("/dc/fileUpload")
//    @ResponseBody
//    public String fileUpload( MultipartFile file){
//        // 获取上传文件路径
//        String uploadPath = file.getOriginalFilename();
//        // 获取上传文件的后缀
//        String fileSuffix = uploadPath.substring(uploadPath.lastIndexOf(".") + 1, uploadPath.length());
//        if (fileSuffix.equals("apk")) {
//            uploadPath = uploadPath2;
//        } else {
//            // 上传目录地址
//            // String uploadpath="E:/hot-manage/image/";//windows路径
//            uploadPath =uploadPath2;// liux路劲
//        }
//        // 上传文件名
//        String fileName = new Date().getTime() + new Random().nextInt(100) + "." + fileSuffix;
//        File savefile = new File(uploadPath + fileName);
//        if (!savefile.getParentFile().exists()) {
//            savefile.getParentFile().mkdirs();
//        }
//        try {
//            file.transferTo(savefile);
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (fileSuffix.equals("apk")) {
//            return "/apk/" + fileName;
//        } else {
//            return "/image/" + fileName;
//        }
//    }


      @RequestMapping("/imgs")
      @ResponseBody
      public String uploadImg(@RequestParam("files")MultipartFile[] files ){
          List<String> urls = new ArrayList<>();
          if (files.length==0){
              return  "fail";
          }
          try{

              for (MultipartFile file:files){
                  String originalFilename = file.getOriginalFilename();
                  String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
                  if (!extName.equals("jpg") || !extName.equals("png") || !extName.equals("gif")){
                      return "请插入图片";
                  }
                  String fileName = UUID.randomUUID().toString().substring(0,5)+System.currentTimeMillis()+ "." + extName;
                          Files.write(Paths.get(uploadPath2+fileName),file.getBytes());
                  urls.add(imgUrl+fileName);
              }
          }catch (Exception e){
              e.printStackTrace();
          }
          String data = JSON.toJSONString(urls);
            return data;
      }
}
