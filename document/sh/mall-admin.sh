#!/usr/bin/env bash
app_name='mall-admin'
docker stop ${app_name}
echo '----stop container----'
docker rm ${app_name}
echo '----rm container----'
docker rmi `docker images | grep none | awk '{print $3}'`
echo '----rm none images----'
docker run -p 8761:8761 --name ${app_name} \
--link mysql:db \
--link redis:redis \
--link eureka-server:eureka-server \
-v /etc/localtime:/etc/localtime \
-v /usr/logs/${app_name}/:/var/logs/ \
-d mall/${app_name}:1.0-SNAPSHOT
echo '----start container----'