# docker build -t wowtools/rtsp2flv .
# docker run -tid --name rtsp2flv -v /home/logs/:/mydata/logs/ --publish "0.0.0.0:8080:8080" --publish "0.0.0.0:8000:8000" wowtools/rtsp2flv
FROM wowtools/jdk11-centos7
EXPOSE 8080 8000
COPY mydata/ /mydata
RUN \
 # 安装ffmpeg
    yum localinstall -y --nogpgcheck https://download1.rpmfusion.org/free/el/rpmfusion-free-release-7.noarch.rpm \
    && yum install -y ffmpeg ffmpeg-devel \
    && ffmpeg -version \
# 安装npm
    && cd /mydata/tmp/ \
    && xz -d node-v14.15.3-linux-x64.tar.xz \
    && tar -xvf node-v14.15.3-linux-x64.tar \
    && mv node-v14.15.3-linux-x64 /mydata/ \
    && ln -s /mydata/node-v14.15.3-linux-x64/bin/npm /usr/local/bin/ \
    && ln -s /mydata/node-v14.15.3-linux-x64/bin/node /usr/local/bin/ \
    && node -v \
# 建立node工程live-app
    && mkdir -p /mydata/app/live-app/ \
    && cd /mydata/app/live-app/ \
    && npm init --yes \
    && npm install node-media-server --save \
    && mv /mydata/tmp/single_app.js /mydata/app/live-app/ \
# 清理临时文件等收尾工作
    && rm -rf /mydata/tmp
# 
ENTRYPOINT ["/bin/bash", "/mydata/startup.sh"]