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
public class Chapter {
    private String id;
    private String filepath;//视频
    private String title;//标题
    private String size;//体积大小
    private String longs;//时长
    private Date create_date;//上传时间
    private String album_id;//所属的专辑id
}
