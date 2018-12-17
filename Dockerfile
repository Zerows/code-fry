FROM java:8
COPY . /app
WORKDIR /app

RUN mkdir /home/runner
RUN mkdir /home/runner/bin

RUN ln -s /usr/bin/java /home/runner/bin/java
RUN ln -s /usr/bin/javac /home/runner/bin/javac
RUN ln -s /usr/bin/ruby /home/runner/bin/ruby
RUN ln -s /usr/bin/python /home/runner/bin/python
RUN ln -s /usr/bin/node /home/runner/bin/node
RUN ./gradlew clean build --no-daemon

ENV PATH=/home/runner/bin
RUN echo $PATH

CMD java -jar build/libs/fry-code-runner-1.0-SNAPSHOT.jar

