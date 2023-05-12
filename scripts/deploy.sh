!/bin/bash
DEPLOY_PATH=/home/ubuntu/action/
BUILD_JAR=$(ls /home/ubuntu/action/build/libs/ilcle_back-0.0.1-SNAPSHOT.jar)
JAR_NAME=$(basename $BUILD_JAR)

echo "> 현재 시간: $(date)" >> $DEPLOY_PATH/deploy.log

echo "> build 파일명: $JAR_NAME" >> $DEPLOY_PATH/deploy.log

echo "> build 파일 복사" >> $DEPLOY_PATH/deploy.log

cp $BUILD_JAR $DEPLOY_PATH

echo "> 현재 실행중인 애플리케이션 pid 확인" >> $DEPLOY_PATH/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z $CURRENT_PID ]
then
  echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> $DEPLOY_PATH/deploy.log
else
  echo "> kill -9 $CURRENT_PID" >> $DEPLOY_PATH/deploy.log
  sudo kill -9 $CURRENT_PID
  sleep 5
fi

DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
source ~/.bashrc
echo "> DEPLOY_JAR 배포"    >> $DEPLOY_PATH/deploy.log
sudo nohup java -jar $DEPLOY_JAR >> $DEPLOY_PATH/nohup.out 2>&1 &