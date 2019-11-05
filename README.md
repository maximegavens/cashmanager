## Cash Manager

Cash manager is a distant payment system that can receive and execute
orders issued by a terminal app on your android phone.

# Docker

To launch the server and build the android app, you can use docker.

```
docker-compose build && docker-compose up
```

This command will do multiple things:
 - Launch the api server which will be available on http://localhost:8080
 - Build the android app which will be available on http://localhost:8081/client.apk
