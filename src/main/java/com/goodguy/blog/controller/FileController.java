package com.goodguy.blog.controller;

import com.goodguy.blog.result.Result;
import com.goodguy.blog.result.ResultFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
public class FileController {

    String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();
    File imageFolder = new File(path,"static/images/upload/");

    @PostMapping("/api/admin/content/upload")
    public Result uploadFile(MultipartFile file) {
        try {
            File f = new File(imageFolder, DigestUtils.md5DigestAsHex(file.getBytes()) + file.getOriginalFilename()
                    .substring(file.getOriginalFilename().length() - 4));
            if (!f.getParentFile().exists())
                f.getParentFile().mkdirs();
            file.transferTo(f);
            String imgUrl = "/api/file/" + f.getName();
            return ResultFactory.buildSuccessResult(imgUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultFactory.buildFailResult("上传失败");
        }
    }

    @GetMapping("/api/content/filelist")
    public Result fileList(){
        File[] array = imageFolder.listFiles();
        for(int i=0;i<array.length;i++)
        {
            if(array[i].isFile())//如果是文件
            {
                System.out.print(" ");
                // 只输出文件名字
                System.out.println( array[i].getName());
                // 输出当前文件的完整路径
                // System.out.println("#####" + array[i]);
                // 同样输出当前文件的完整路径   大家可以去掉注释 测试一下
                // System.out.println(array[i].getPath());
            }
            else if(array[i].isDirectory())//如果是文件夹
            {
                System.out.print(" ");
                System.out.println( array[i].getName());
                //System.out.println(array[i].getPath());
                //文件夹需要调用递归 ，深度+1
            }
        }
        return ResultFactory.buildSuccessResult(null);
    }

}
