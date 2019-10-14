package com.baizhi.serviceimpl;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Picture;
import com.baizhi.mapper.PictureMapper;
import com.baizhi.service.PictureService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {
    @Autowired
    PictureMapper pictureMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Integer count() {
        Integer count = pictureMapper.count();
        return count;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Picture> findAll(Integer page, Integer rows) {
        Integer start = (page - 1) * rows;
        List<Picture> all = pictureMapper.findAll(start, rows);
        return all;
    }

    @Override
    public String edid(Picture picture, String oper, HttpSession session, String id) {
        if ("add".equals(oper)) {
            //添加
            String s = UUID.randomUUID().toString();
            picture.setId(s);
            Date date = new Date();
            picture.setTime(date);
            pictureMapper.insert(picture);
            return s;
        } else if ("del".equals(oper)) {
            String[] split = id.split(",");
            for (String s : split) {
                pictureMapper.delete(s);
            }
            //删除
            pictureMapper.delete(picture.getId());
            return null;
        } else {
            //修改
            Picture one = pictureMapper.findOne(picture.getId());
            String xg = (String) session.getAttribute("xg");
            if ("xg".equals(xg)) {

            } else {
                picture.setSrc(one.getSrc());
            }
            Date date = new Date();
            picture.setTime(date);
            pictureMapper.update(picture);
            session.removeAttribute("xg");
            return null;
        }
    }

    @Override
    public void update(MultipartFile src, String bannerId, HttpSession session) {

        //获取图片存储的位置
        String realPath = session.getServletContext().getRealPath("/img");

        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String originalFilename = src.getOriginalFilename();
        String newFileName = new Date().getTime() + "_" + originalFilename;
        try {
            src.transferTo(new File(realPath, newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        session.setAttribute("xg", "xg");
        //修改图片的路径
        Picture one = pictureMapper.findOne(bannerId);
        one.setSrc(newFileName);
        String id = "";
        edid(one, "aaa", session, id);
    }

    @Override
    public void dc(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("----------------------");
        List<Picture> select = pictureMapper.select();
        for (Picture picture : select) {
            picture.setSrc("E:\\source\\ajax\\cmfz\\src\\main\\webapp\\img\\" + picture.getSrc());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("轮播图管理", "轮播图"),
                Picture.class, select);

        try {
            ServletOutputStream outputStream = response.getOutputStream();
            response.setHeader("content-disposition", "attachment");
            response.setContentType("application/vnd.ms-Excel;charset=utf-8");
            workbook.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("导出成功");
    }
}
