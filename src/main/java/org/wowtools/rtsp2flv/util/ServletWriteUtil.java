package org.wowtools.rtsp2flv.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author liuyu
 * @date 2022/9/21
 */
public class ServletWriteUtil {

    public static void writeString(HttpServletResponse response, String str) {
        response.setCharacterEncoding("UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.print(str);
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeErrString(HttpServletResponse response, String str) {
        writeString(response, str);
        response.setStatus(500);
    }
}
