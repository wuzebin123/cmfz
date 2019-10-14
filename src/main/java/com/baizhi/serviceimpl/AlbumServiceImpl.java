package com.baizhi.serviceimpl;

import com.baizhi.entity.Album;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumMapper albumMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Album> findAll(Integer page, Integer rows) {
        Integer start = (page - 1) * rows;
        List<Album> all = albumMapper.findAll(start, rows);
        return all;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer count() {
        return albumMapper.count();
    }

    @Override
    public String edid(Album album, String oper, HttpSession session, String id) {
        if ("add".equals(oper)) {
            //添加
            String s = UUID.randomUUID().toString();
            album.setId(s);
            Date date = new Date();
            album.setCreate_date(date);
            album.setPublish_date(date);
            albumMapper.insert(album);
            return s;
        } else if ("del".equals(oper)) {
            String[] split = id.split(",");
            for (String s : split) {
                albumMapper.delete(s);
            }
            //删除
            albumMapper.delete(album.getId());
            return null;
        } else {
            //修改
            Album one = albumMapper.findOne(album.getId());
            String xg = (String) session.getAttribute("xg");
            if ("xg".equals(xg)) {

            } else {
                album.setCover(one.getCover());
            }
            Date date = new Date();
            album.setCreate_date(date);
            album.setPublish_date(date);
            albumMapper.update(album);
            session.removeAttribute("xg");
            return null;
        }
    }

    @Override
    public void update(MultipartFile cover, String bannerId, HttpSession session) {
        //获取图片存储的位置
        String realPath = session.getServletContext().getRealPath("/img");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String originalFilename = cover.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + originalFilename;
        try {
            cover.transferTo(new File(realPath, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        session.setAttribute("xg", "xg");
        //修改图片的路径
        Album one = albumMapper.findOne(bannerId);
        one.setCover(newFileName);
        String id = "";
        edid(one, "aaa", session, id);
    }
}
