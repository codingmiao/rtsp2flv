1、wget https://npm.taobao.org/mirrors/node/v14.15.3/node-v14.15.3-linux-x64.tar.xz 下载npm包，放到mydata/tmp/node-v14.15.3-linux-x64.tar.xz

2、mvn clean package -DskipTests编译出jar包，放到mydata/app/rtsp2flv/rtsp2flv-1.0-SNAPSHOT.jar

3、docker build -t wowtools/rtsp2flv . 构建docker镜像
