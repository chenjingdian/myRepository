package com.biyou.controller;


import com.biyou.pojo.Article;
import com.biyou.service.ArticleService;
import com.biyou.utils_entry.FastDFSClient;
import com.biyou.utils_entry.FastDFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class UploadController {

    @Autowired
    private ArticleService articleService;


    /**
     * 表单上传
     * 多个文件上传
     */
    @PostMapping("/article")
    public Article moreimage(HttpServletRequest request, Article article) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        //准备好接受图片的 数组

        String[] arr = new String[files.size()];
        if (files != null) {

            for (int i = 0; i < files.size(); i++) {
                try {
                    //判断文件是否存在
                    if (files.get(i) == null) {
                        continue;
                    }
                    //获取文件的完整名称
                    String originalFilename = files.get(i).getOriginalFilename();
                    if (StringUtils.isEmpty(originalFilename)) {
                        continue;
                    }

                    //获取文件的扩展名称  abc.jpg   jpg
                    String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

                    //获取文件内容
                    byte[] content = files.get(i).getBytes();

                    //创建文件上传的封装实体类
                    FastDFSFile fastDFSFile = new FastDFSFile(originalFilename, content, extName);

                    //基于工具类进行文件上传,并接受返回参数  String[]
                    String[] uploadResult = FastDFSClient.upload(fastDFSFile);

                    //封装返回结果
                    String url = FastDFSClient.getTrackerUrl() + uploadResult[0] + "/" + uploadResult[1];

                    arr[i] = url;
                } catch (Exception e) {
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println("这次没有上传文件");
                }
            }
            article.setImages(arr);
        }

        articleService.addDoc(article);

        return article;
    }

}
