FROM openjdk:8
VOLUME /tmp
COPY resume_backend-0.0.1-SNAPSHOT.jar app.jar
# ENTRYPOINT java -jar app.jar

RUN chmod +x /app.jar
# volume 指定临时文件目录为/tmp
# 其效果是在主机/var/lib/docker目录下创建一个临时文件，并连接到容器的/tmp
# 将jar包添加到容器中并更名
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
#暴露8080端口
# EXPOSE 8080
