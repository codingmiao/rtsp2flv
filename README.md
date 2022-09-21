一个rtsp流转flv流，以便用flv.js方式在web上直接播放rtsp

# 快速开始

## 1、启动docker容器
```shell
docker run -tid --name rtsp2flv -v /home/logs/:/mydata/logs/ --publish "0.0.0.0:8080:8080" --publish "0.0.0.0:8000:8000" wowtools/rtsp2flv
```
注:

8080为管理端口，调用8080端口上的restful接口进行rtsp流的添加/删除/查看。

8000为flv对外暴露的端口，web前端访问此端口以获得并展示flv(可能需要用nginx转发一下以解决跨域)。

容器内`/mydata/logs/`目录存放了rtsp视频流的日志，有需要的话可以把这个目录映射到宿主机上。

## 2、添加rtsp流转换
假如我们需要把rtsp流`rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mp4` ,转换为flv`http://ip:8000/rtsp2flv/1flv.flv`
，则在启动容器后，我们只需向容器发送一条restful请求:
```shell
curl http://mycentos7:8080/add?name=1&url=rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mp4
```
返回`success`提示则说明添加成功，随后，可在“VLC media player”等工具中验证url`http://ip:8000/rtsp2flv/1flv.flv` 是否可以播放，或者写代码用flv.js等工具来播放(可能需要用nginx转发一下以解决跨域)。

## 3、移除rtsp流转换
```shell
curl http://ip:8080/remove?name=1
```
返回`success`，即把刚才添加的name为1的rtsp流转换移除。

## 4、查看rtsp流转换
```shell
curl http://ip:8080/get
```
返回已添加的rtsp流转换名称列表，以空格分隔


# 自己构建镜像

参见[dockerbuilder/readme.md](dockerbuilder/readme.md)
