FROM node:22-alpine as builder

WORKDIR /home/app

COPY package.json .

RUN npm install --legacy-peer-deps

COPY . .

ARG VITE_GOOGLE_CLIENT_ID

ENV VITE_GOOGLE_CLIENT_ID_BY_ARG=${VITE_GOOGLE_CLIENT_ID}

RUN npm install && npm run build    

# second stage 

FROM nginx:1.25-alpine

COPY ./nginx.conf etc/nginx/conf.d/default.conf

COPY --from=builder home/app/dist /usr/share/nginx/html

EXPOSE 80

CMD [ "nginx", "-g", "daemon off;" ]