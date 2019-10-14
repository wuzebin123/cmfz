package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    AlbumService albumService;

    @RequestMapping("findAll")
    public Map<String, Object> findAll(Integer page, Integer rows) {
        Integer count = albumService.count();
        Integer total = count % rows == 0 ? count / rows : count / rows + 1;
        List<Album> all = albumService.findAll(page, rows);
        HashMap<String, Object> map = new HashMap<>();
        map.put("rows", all);
        map.put("records", count);
        map.put("page", page);
        map.put("total", total);
        return map;
    }

    @RequestMapping("edit")
    public String edit(Album album, String oper, HttpSession session, String id) {
        String edid = albumService.edid(album, oper, session, id);
        return edid;
    }

    @RequestMapping("update")
    public void update(MultipartFile cover, String bannerId, HttpSession session) {
        albumService.update(cover, bannerId, session);
    }
}
