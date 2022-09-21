package org.wowtools.rtsp2flv.service;

import org.wowtools.rtsp2flv.util.CmdUtil;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * rtsp地址管理
 *
 * @author liuyu
 * @date 2022/9/21
 */
public class UrlManageService {

    private static final ReadWriteLock lock = new ReentrantReadWriteLock();

    private static final String prefix = "/rtsp2flv/";
    private static final String suffix = "flv";

    /**
     * 添加一个rtsp转flv流
     *
     * @param name 输出flv名称，若此名称已被占用则不做任何操作
     * @param url  输入流地址，例如rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mp4
     * @param type 预留字段，输入流的类型，后续扩展多种流，目前只有rtsp
     */
    public static void add(String name, String url, String type) {
        if (name.indexOf(" ") > 0 || name.indexOf(prefix) > 0 || name.indexOf(suffix) > 0) {
            throw new RuntimeException("name不能包含空格、" + prefix + "、" + suffix);
        }

        for (String currentName : _getAll()) {
            if (name.equals(currentName)) {
                return;
            }
        }
        lock.readLock().lock();
        try {
            //双检锁
            for (String currentName : _getAll()) {
                if (name.equals(currentName)) {
                    return;
                }
            }

            CmdUtil.execute(null,
                    "$(nohup ffmpeg -rtsp_transport tcp -i " +
                            url + " -vcodec h264 -f flv -an rtmp://localhost:1935" +
                            prefix + name + suffix +
                            " >/mydata/logs/" + name + ".log 2>&1 &) &");
            for (String currentName : _getAll()) {
                if (name.equals(currentName)) {
                    return;
                }
            }
            throw new RuntimeException("添加流失败");
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 移除一个flv流
     *
     * @param name flv名称
     */
    public static void remove(String name) {
        lock.writeLock().lock();
        try {
            CmdUtil.execute(null, "ps -ef|grep '" +
                    prefix + name + suffix +
                    "' |grep -v grep|cut -c 9-15|xargs kill -9 >/dev/null 2>&1");
        } finally {
            lock.writeLock().unlock();
        }
        CmdUtil.execute(null,
                "$(nohup rm -rf >/mydata/logs/" + name + ".log  &) &");
    }

    /**
     * 获取所有flv流名称
     *
     * @return
     */
    public static List<String> getAll() {
        lock.readLock().lock();
        try {
            return _getAll();
        } finally {
            lock.readLock().unlock();
        }
    }

    private static List<String> _getAll() {
        String str = CmdUtil.execute(null, "ps -ef|grep " + prefix);
        String[] rows = str.split("\n");
        List<String> res = new LinkedList<>();
        for (String row : rows) {
            if (row.isBlank()) {
                continue;
            }
            if (row.indexOf("grep ") > 0) {
                continue;
            }
            int b = str.indexOf(prefix) + prefix.length();
            int e = str.indexOf(suffix, b);
            String name = str.substring(b, e);
            res.add(name);
        }
        return res;
    }

}
