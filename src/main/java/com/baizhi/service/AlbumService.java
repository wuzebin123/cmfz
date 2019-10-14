package com.baizhi.service;

import com.baizhi.entity.Album;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface AlbumService {
    List<Album> findAll(Integer page, Integer rows);

    Integer count();

    String edid(Album album, String oper, HttpSession session, String id);

    void update(MultipartFile src, String bannerId, HttpSession session);
}
