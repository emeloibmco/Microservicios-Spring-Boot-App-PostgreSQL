FROM node:14-alpine3.15 as build-step

WORKDIR /app
COPY . .
RUN npm install
RUN npm run build 

FROM nginxinc/nginx-unprivileged:stable-alpine
COPY --from=build-step /app/build /usr/share/nginx/html




