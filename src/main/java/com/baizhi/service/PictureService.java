package com.baizhi.service;

import com.baizhi.entity.Picture;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface PictureService {
    Integer count();

    List<Picture> findAll(Integer page, Integer rows);

    String edid(Picture picture, String oper, HttpSession session, String id);

    void update(MultipartFile src, String bannerId, HttpSession session);

    void dc(HttpServletRequest request, HttpServletResponse response);
}
