FROM openjdk:8
VOLUME /tmp
ADD resume_backend-0.0.1-SNAPSHOT.jar /app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]


FROM openjdk:8
# volume 指定临时文件目录为/tmp
# 其效果是在主机/var/lib/docker目录下创建一个临时文件，并连接到容器的/tmp
VOLUME /tmp

# 将jar包添加到容器中并更名
COPY resume_backend-0.0.1-SNAPSHOT.jar app.jar

RUN chmod +x /app.jar

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/app.jar"]
# ENTRYPOINT java -jar app.jar