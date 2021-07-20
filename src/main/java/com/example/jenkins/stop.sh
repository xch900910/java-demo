#!/bin/bash

jar_name=$(ls | grep master-data-)
isServerExist=$(ps -ef | grep master-data- | grep -v "grep" | wc -l)
if [ "$isServerExist" = "1" ]; then
  pid=$(ps -ef | grep master-data- | grep -v "grep" | awk '{print $2}')
  kill -9 $pid
  echo "服务已停止"
fi
