package org.wowtools.rtsp2flv.servlet;

import org.wowtools.rtsp2flv.service.UrlManageService;
import org.wowtools.rtsp2flv.util.ServletWriteUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liuyu
 * @date 2022/9/21
 */
public class RemoveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        if (null == name) {
            ServletWriteUtil.writeErrString(response, "name不能为空");
            return;
        }
        UrlManageService.remove(name);
        ServletWriteUtil.writeString(response, "success");
    }
}
