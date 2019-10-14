package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Picture {
    @Excel(name = "编号")
    private String id;
    @Excel(name = "标题")
    private String title;//标题
    @Excel(name = "状态")
    private String state;//状态
    @Excel(name = "内容")
    private String describe;//内容
    @Excel(name = "发布时间", format = "yyyy年MM月dd", width = 20)
    private Date time;//发布时间
    @Excel(name = "图片路径", type = 2, width = 30, height = 20, needMerge = true)
    private String src;//图片路径
}
