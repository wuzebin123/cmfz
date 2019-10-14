package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ChapterService {
    List<Chapter> findAll(Integer page, Integer rows, String id);

    Integer count(String id);

    String edid(Chapter chapter, String oper, HttpSession session, String id);

    void update(MultipartFile src, String bannerId, HttpSession session);
}
