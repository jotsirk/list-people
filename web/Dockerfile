FROM node:14 AS build
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build -- --configuration=docker

FROM nginx:alpine
COPY --from=build /app/dist/web /usr/share/nginx/html
