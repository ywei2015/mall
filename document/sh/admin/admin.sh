#!/usr/bin/env bash
# 定义应用组名
group_name='mall'
# 定义应用名称
app_name='mall-admin'
# 定义应用版本
app_version='1.0-SNAPSHOT'
# 定义应用环境
profile_active='prod'
echo '----copy jar----'
docker stop ${app_name}
echo '----stop container----'
docker rm ${app_name}
echo '----rm container----'
docker rmi ${group_name}/${app_name}:${app_version}
echo '----rm image----'
# 打包编译docker镜像
docker build -t ${group_name}/${app_name}:${app_version} .
echo '----build image----'
docker run -p 8761:8761 --name ${app_name} \
-e 'spring.profiles.active'=${profile_active} \
-e TZ="Asia/Shanghai" \
--link mysql:db \
--link redis:redis \
--link eureka-server:eureka-server \
-v /usr/logs/${app_name}/:/var/logs/ \
-d ${group_name}/${app_name}:${app_version}
echo '----start container----'