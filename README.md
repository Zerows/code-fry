Source code for code-fry. Which writes the commands to a rabbitmq topic. Each language has a separate topic. 

> docker build  -t java-fry -f Dockerfile . 

> docker run --network="host" -a stdout java-fry:latest

> ./gradlew clean build


