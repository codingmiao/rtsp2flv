package org.wowtools.rtsp2flv.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 执行cmd/bash命令
 *
 * @author liuyu
 * @date 2022/7/12
 */
public class CmdUtil {
    public static String execute(String dir, String cmd) {
        StringBuilder sb = new StringBuilder();
        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command("sh", "-c", cmd);
        if (dir != null) {
            processBuilder.directory(new File(dir));
        }
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        try {
            //启动进程
            Process start = processBuilder.start();
            //获取输入流
            InputStream inputStream = start.getInputStream();
            //转成字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            int len = -1;
            char[] c = new char[1024];
            //读取进程输入流中的内容
            while ((len = inputStreamReader.read(c)) != -1) {
                String s = new String(c, 0, len);
                sb.append(s).append('\n');
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
