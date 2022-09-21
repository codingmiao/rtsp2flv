package org.wowtools.rtsp2flv.servlet;

import org.wowtools.rtsp2flv.service.UrlManageService;
import org.wowtools.rtsp2flv.util.ServletWriteUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author liuyu
 * @date 2022/9/21
 */
public class GetAllServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<String> res = UrlManageService.getAll();
        ServletWriteUtil.writeString(response, String.join(" ", res));
    }
}
