## # #!/bin/bash
#
## # BUILD_JAR=$(ls /home/ubuntu/action/build/libs/*.jar)
## # JAR_NAME=$(basename $BUILD_JAR)
## # echo "> build 파일명: $JAR_NAME" >> /home/ubuntu/action/deploy.log
#
## # echo "> build 파일 복사" >> /home/ubuntu/action/deploy.log
## # DEPLOY_PATH=/home/ubuntu/action/
## # sudo cp $BUILD_JAR $DEPLOY_PATH
#
## # echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ubuntu/action/deploy.log
## # CURRENT_PID=$(pgrep -f $JAR_NAME)
#
## # if [ -z $CURRENT_PID ]
## # then
## # echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ubuntu/action/deploy.log
## # else
## # echo "> kill -15 $CURRENT_PID"
## # kill -15 $CURRENT_PID
## # sleep 5
## # fi
#
## # DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
## # echo "> DEPLOY_JAR 배포" >> /home/ubuntu/action/deploy.log
## # nohup java -jar $DEPLOY_JAR >> /home/ubuntu/deploy.log 2>/home/ubuntu/action/deploy_err.log &
#
##!/usr/bin/env bash
#
#REPOSITORY=/home/ubuntu/app
#
#echo "> 현재 구동 중인 애플리케이션 pid 확인"
#
#CURRENT_PID=$(pgrep -fla java | grep hayan | awk '{print $1}')
#
#echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"
#
#if [ -z "$CURRENT_PID" ]; then
#  echo "현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
#else
#  echo "> kill -15 $CURRENT_PID"
#  kill -15 $CURRENT_PID
#  sleep 5
#fi
#
#echo "> 새 애플리케이션 배포"
#
#JAR_NAME=$(ls -tr $REPOSITORY/*SNAPSHOT.jar | tail -n 1)
#
#echo "> JAR NAME: $JAR_NAME"
#
#echo "> $JAR_NAME 에 실행권한 추가"
#
#chmod +x $JAR_NAME
#
#echo "> $JAR_NAME 실행"
#
#nohup java -jar -Duser.timezone=Asia/Seoul $JAR_NAME >> $REPOSITORY/nohup.out 2>&1 &
#
## !/bin/bash
## BUILD_JAR=$(ls /home/ubuntu/action/build/libs/practice-githubAction-deploy-0.0.1-SNAPSHOT.jar)
## JAR_NAME=$(basename $BUILD_JAR)
#
## echo "> 현재 시간: $(date)" >> /home/ubuntu/action/deploy.log
#
## echo "> build 파일명: $JAR_NAME" >> /home/ubuntu/action/deploy.log
#
## echo "> build 파일 복사" >> /home/ubuntu/action/deploy.log
## DEPLOY_PATH=/home/ubuntu/action/
## cp $BUILD_JAR $DEPLOY_PATH
#
## echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ubuntu/action/deploy.log
## CURRENT_PID=$(pgrep -f $JAR_NAME)
#
## if [ -z $CURRENT_PID ]
## then
## echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ubuntu/action/deploy.log
## else
## echo "> kill -9 $CURRENT_PID" >> /home/ubuntu/action/deploy.log
## sudo kill -9 $CURRENT_PID
## sleep 5
## fi
#
#
## DEPLOY_JAR=$DEPLOY_PATH$JAR_NAME
## echo "> DEPLOY_JAR 배포" >> /home/ubuntu/action/deploy.log
## sudo nohup java -jar $DEPLOY_JAR >> /home/ubuntu/deploy.log 2>/home/ubuntu/action/deploy_err.log &
