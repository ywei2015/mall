#!/usr/bin/env bash
app_name='eureka-server'
docker stop ${app_name}
echo '----stop container----'
docker rm ${app_name}
echo '----rm container----'
docker rmi `docker images | grep none | awk '{print $3}'`
echo '----rm none images----'
docker run -p 8760:8760 --name ${app_name} \
-v /etc/localtime:/etc/localtime \
-v /usr/logs/${app_name}/:/var/logs/ \
-d mall/${app_name}:1.0-SNAPSHOT
echo '----start container----'