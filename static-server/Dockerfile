FROM nginx

FROM node:lts-alpine

RUN npm install -g http-server

EXPOSE 8080

CMD ["http-server", "/public"]