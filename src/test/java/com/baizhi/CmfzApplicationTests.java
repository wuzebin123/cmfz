package com.baizhi;


import io.goeasy.GoEasy;
import net.minidev.json.JSONArray;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzApplicationTests {

    @Autowired
    private HttpSession session;

    @Test
    public void contextLoads() {
    }

    @Test
    public void contextLoads2() throws TagException, ReadOnlyFileException, CannotReadException, InvalidAudioFrameException, IOException {
        //获取文件位置
        String realPath = session.getServletContext().getRealPath("/img/1569669702646_贺敬轩-毕业季(超清).mp4");
        File file = new File(realPath);
        //获取文件大小  单位是字节 byte
        long length = file.length();
        //获取音频时长 单位是秒      AudioFile类需要引入额外依赖 jaudiotagger
        AudioFile read = AudioFileIO.read(file);
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
    }

    @Test
    public void goeasy() throws InterruptedException {
        while (true) {
            List<Integer> list = new ArrayList<>();
            list.add(new Random().nextInt(1000));
            list.add(new Random().nextInt(1000));
            list.add(new Random().nextInt(1000));
            list.add(new Random().nextInt(1000));
            list.add(new Random().nextInt(1000));
            list.add(new Random().nextInt(1000));
            String s = JSONArray.toJSONString(list);
            GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-41105467e17d4745869fc7051df656ee");
            goEasy.publish("164channel", s);
            Thread.sleep(2000);
        }
    }
}
