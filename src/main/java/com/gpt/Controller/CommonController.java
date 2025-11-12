package com.gpt.Controller;

import com.gpt.Common.R;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

/**
 * @Author: 程序员Eighteen
 * @CreateTime: 2025-10-08  15:09
 * @BelongsProject: Regent_Takeout
 * @Description: TODO
 * @Version: 1.0
 */

// 文件的上传和下载
@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    // 文件上传
    @PostMapping("/upload")
    public R<String> upload(@RequestParam("file") MultipartFile file){
        log.info(file.toString());

        // 使用原始文件名
        String originalFilename = file.getOriginalFilename();
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

        // 使用UUID随机生成文件名
        String fileName = UUID.randomUUID() + suffix; // 动态拼接文件后缀名  移除了toString()。因为冗余了

        // 创建一个目录对象
        File dir=new File(basePath);
        // 判断是否存在
        if(!dir.exists()){
            dir.mkdirs();
        }

        // 转存到指定位置
        try{
            file.transferTo(new File(basePath+fileName));
            log.info(file.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        return R.success(fileName);
    }

    // 文件下载
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response){
        // 输入流，读取文件内容
        try{
            FileInputStream fileInputStream=new FileInputStream(new File(basePath+name));

            // 输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");
            int len=0;
            byte[] buffer = new byte[1024];
            while ((len=fileInputStream.read(buffer))!=-1){
                outputStream.write(buffer,0,len);
                outputStream.flush();
            }
            // 关闭资源
            outputStream.close();
            fileInputStream.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
