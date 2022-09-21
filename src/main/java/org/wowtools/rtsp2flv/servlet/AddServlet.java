package org.wowtools.rtsp2flv.servlet;

import org.wowtools.rtsp2flv.service.UrlManageService;
import org.wowtools.rtsp2flv.util.ServletWriteUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新增一个流，参数:<br/>
 * url rtsp流的地址<br/>
 * name 转换成flv的名字<br/>
 *
 * @author liuyu
 * @date 2022/9/21
 */
public class AddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        if (null == name) {
            ServletWriteUtil.writeErrString(response, "name不能为空");
            return;
        }
        String url = request.getParameter("url");
        if (null == url) {
            ServletWriteUtil.writeErrString(response, "url不能为空");
            return;
        }
        String type = request.getParameter("type");
        UrlManageService.add(name, url, type);
        ServletWriteUtil.writeString(response, "success");
    }
}
