
> docker build  -t java-fry -f Dockerfile . 

> docker run  --env-file=env --network="host" -a stdout java-fry:latest

> ./gradlew clean build


