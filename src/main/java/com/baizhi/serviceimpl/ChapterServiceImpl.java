package com.baizhi.serviceimpl;


import com.baizhi.entity.Chapter;
import com.baizhi.mapper.AlbumMapper;
import com.baizhi.mapper.ChapterMapper;
import com.baizhi.service.ChapterService;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterMapper chapterMapper;
    @Autowired
    AlbumMapper albumMapper;

    @Override
    public List<Chapter> findAll(Integer page, Integer rows, String id) {
        Integer start = (page - 1) * rows;
        List<Chapter> all = chapterMapper.findAll(start, rows, id);
        return all;
    }

    @Override
    public Integer count(String id) {
        Integer count = chapterMapper.count(id);
        return count;
    }

    @Override
    public String edid(Chapter chapter, String oper, HttpSession session, String id) {

        if ("add".equals(oper)) {
            //添加
            String s = UUID.randomUUID().toString();
            chapter.setId(s);
            Date date = new Date();
            chapter.setCreate_date(date);
            chapterMapper.insert(chapter);
            Integer count = chapterMapper.count(chapter.getAlbum_id());
            albumMapper.updateCount(count, chapter.getAlbum_id());
            return s;
        } else if ("del".equals(oper)) {
            String[] split = id.split(",");
            for (String s : split) {
                chapterMapper.delete(s);
            }
            //删除
            chapterMapper.delete(chapter.getId());

            Integer count = chapterMapper.count(chapter.getAlbum_id());
            System.out.println(count + "--------------");
            albumMapper.updateCount(count, chapter.getAlbum_id());
            return null;
        } else {
            //修改
            Chapter one = chapterMapper.findOne(chapter.getId());
            String xg = (String) session.getAttribute("xg");
            if ("xg".equals(xg)) {
            } else {
                chapter.setFilepath(one.getFilepath());
            }
            one.setTitle(chapter.getTitle());
            if (one.getSize() == null) {
                Date date = new Date();
                chapter.setCreate_date(date);
                chapterMapper.update(chapter);
            } else {
                chapterMapper.update(one);
            }

            session.removeAttribute("xg");
            return null;
        }
    }

    @Override
    public void update(MultipartFile filepath, String bannerId, HttpSession session) {
        //获取图片存储的位置
        String realPath = session.getServletContext().getRealPath("/audio");

        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String originalFilename = filepath.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + originalFilename;
        try {
            filepath.transferTo(new File(realPath, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取文件位置
        String realPath1 = session.getServletContext().getRealPath("/audio/" + newFileName);
        System.out.println(realPath1);
        File file1 = new File(realPath1);
        //获取文件大小  单位是字节 byte
        long length = file1.length();
        //获取音频时长 单位是秒      AudioFile类需要引入额外依赖 jaudiotagger
        AudioFile read = null;
        try {
            read = AudioFileIO.read(file1);
        } catch (CannotReadException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TagException e) {
            e.printStackTrace();
        } catch (ReadOnlyFileException e) {
            e.printStackTrace();
        } catch (InvalidAudioFrameException e) {
            e.printStackTrace();
        }
        AudioHeader header = read.getAudioHeader();
        int trackLength = header.getTrackLength();
        //获取分钟数
        Integer m = trackLength / 60;
        //获取秒秒数
        Integer s = trackLength % 60;
        System.out.println(m + "分" + s + "秒");
        //将文件大小强转成double类型
        double size = (double) length;
        System.out.println(size / 1024 / 1024);
        //获取文件大小 单位是MB
        double ll = size / 1024 / 1024;
        //取double小数点后两位  四舍五入
        BigDecimal bg = new BigDecimal(ll).setScale(2, RoundingMode.UP);
        System.out.println(bg + "MB");

        Chapter one = chapterMapper.findOne(bannerId);
        one.setLongs(m + "分" + s + "秒");
        one.setSize(bg + "MB");
        session.setAttribute("xg", "xg");
        //修改图片的路径
        one.setFilepath(newFileName);
        String id = "";
        System.out.println(one);
        edid(one, "aaa", session, id);
    }
}
