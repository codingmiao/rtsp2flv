package org.wowtools.rtsp2flv;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.wowtools.rtsp2flv.servlet.AddServlet;
import org.wowtools.rtsp2flv.servlet.GetAllServlet;
import org.wowtools.rtsp2flv.servlet.RemoveServlet;

/**
 * @author liuyu
 * @date 2022/9/21
 */
public class StartUp {
    public static void main(String[] args) throws Exception {
        //添加servlet支持
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        servletContextHandler.setContextPath("/");
        servletContextHandler.addServlet(new ServletHolder(new AddServlet()), "/add");
        servletContextHandler.addServlet(new ServletHolder(new GetAllServlet()), "/get");
        servletContextHandler.addServlet(new ServletHolder(new RemoveServlet()), "/remove");

        HandlerList handlerList = new HandlerList();
        handlerList.addHandler(servletContextHandler);

        Server server = new Server(8080);
        server.setHandler(handlerList);
        server.setStopTimeout(0);
        server.start();
        server.join();
    }
}
