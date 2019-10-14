package com.baizhi.controller;


import com.baizhi.entity.Picture;
import com.baizhi.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("picture")
public class PictureController {
    @Autowired
    PictureService pictureService;

    @RequestMapping("findAll")
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Integer count = pictureService.count();
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        List<Picture> all = pictureService.findAll(page, rows);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", all);
        map.put("records", count);
        map.put("page", page);
        map.put("total", total);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Picture picture, String oper, HttpSession session, String id) {
        String edid = pictureService.edid(picture, oper, session, id);
        return edid;
    }

    @RequestMapping("update")
    public void update(MultipartFile src, String bannerId, HttpSession session) {
        pictureService.update(src, bannerId, session);
    }

    @RequestMapping("dc")
    public void dc(HttpServletRequest request, HttpServletResponse response) {
        pictureService.dc(request, response);
    }
}
