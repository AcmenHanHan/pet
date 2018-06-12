FROM base_image

#MAINTAINER ziyun "wlcloudy@163.com"

# 环境变量
ENV WORK_PATH /home/project
ENV APP_DIR pet-app
ENV APP_VERSION latest

EXPOSE 5555

#VOLUME
VOLUME ["/home/project/pet-app", "/tmp/data"]

#COPY
COPY $APP_DIR $WORK_PATH/
#COPY $APP_NAME $WORK_PATH/

# WORKDIR
WORKDIR $WORK_PATH/pet-app/webapps

# ENTRYPOINT
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom"]

# CMD
CMD ["-jar", "pet.jar"]