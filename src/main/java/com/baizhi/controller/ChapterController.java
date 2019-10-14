package com.baizhi.controller;


import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    @RequestMapping("findAll")
    public Map<String, Object> findAll(Integer page, Integer rows, String id) {
        Integer count = chapterService.count(id);
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        List<Chapter> all = chapterService.findAll(page, rows, id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", all);
        map.put("records", count);
        map.put("page", page);
        map.put("total", total);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Chapter chapter, String oper, HttpSession session, String id) throws Exception {
        String edid = chapterService.edid(chapter, oper, session, id);
        return edid;
    }

    @RequestMapping("update")
    public void update(MultipartFile filepath, String bannerId, HttpSession session) {
        chapterService.update(filepath, bannerId, session);
    }

    @RequestMapping("/download")
    public void download(String filepath, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取目标文件夹的路径
        String realPath = request.getSession().getServletContext().getRealPath("/audio");
        //读入
        FileInputStream fis = new FileInputStream(new File(realPath, filepath));

        //写出
        ServletOutputStream os = response.getOutputStream();
        //设置响应头
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(filepath, "utf-8"));

        IOUtils.copy(fis, os);
        //关流
        IOUtils.closeQuietly(fis);
        IOUtils.closeQuietly(os);

    }
}
