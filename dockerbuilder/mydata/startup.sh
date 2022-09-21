cd /mydata/app/rtsp2flv/
nohup java -Xmx64m -jar rtsp2flv-1.0-SNAPSHOT.jar >/mydata/rtsp2flv.log &

cd /mydata/app/live-app/
node single_app.js