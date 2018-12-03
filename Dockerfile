FROM java:8
COPY . /app
WORKDIR /app
RUN ./gradlew clean
CMD ./gradlew run --info
