package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Album {
    private String id;
    private String title;//标题
    private String cover;//图片
    private String author;//作者
    private String beam;//波音员
    private Integer count;//章节数
    private Date publish_date;//发布时间
    private String content;//简介
    private Date create_date;//上传时间
    private String state;//状态
    private Double fraction;//分数
}
