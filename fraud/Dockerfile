FROM openjdk

RUN mkdir -p /home/app

COPY ./app /home/app

# set default dir so that next commands executes in /home/app dir
WORKDIR /home/app

COPY target/fraud-1.0.0.jar /home/app/fraud-1.0.0.jar

ENTRYPOINT ["java","-jar","/home/app/fraud-1.0.0.jar"]



#To create an image from our Dockerfile, we have to run ‘docker build’ like before:
#$> docker build --tag=fraud:latest .

#Finally, we’re able to run the container from our image:
#$> docker run -p8887:8888 fraud:latest