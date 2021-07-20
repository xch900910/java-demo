#!/bin/bash
source /etc/profile

springProfilesActive=$1
SERVER_PORT=$2
if [ "${springProfilesActive}" == '' ]; then
  echo "请输入参数指定spring.profiles.active的值"
  exit 1
fi
if [ "${SERVER_PORT}" == '' ]; then
  SERVER_PORT="8080"
fi
#进入项目目录
cd /usr/local/master-data
#获取启动项目jar包
jar_name=$(ls | grep master-data-)

#判断端口是否被占用
if [ -n "$SERVER_PORT" ]; then
  SERVER_PORT_COUNT=$(netstat -tln | grep $SERVER_PORT | wc -l)
  if [ $SERVER_PORT_COUNT -gt 0 ]; then
    echo "ERROR: 服务的端口： $SERVER_PORT already used!"
    exit 1
  fi
fi

#启动jar

nohup java -jar ${jar_name} --spring.profiles.active=${springProfilesActive} --server.port=${SERVER_PORT} >/dev/null 2>&1 &
#判断是否成功启动
COUNT=0
while [ $COUNT -lt 1 ]; do
  echo -e ".\c"
  sleep 1
  if [ -n "$SERVER_PORT" ]; then
    COUNT=$(netstat -an | grep $SERVER_PORT | wc -l)
  fi
  if [ $COUNT -gt 0 ]; then
    break
  fi
done

echo "启动成功"
