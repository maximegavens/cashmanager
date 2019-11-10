FROM maven

COPY . .

RUN mvn package

EXPOSE 8080
CMD ["mvn", "spring-boot:run"]