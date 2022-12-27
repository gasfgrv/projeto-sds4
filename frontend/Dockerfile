FROM node:16-alpine
WORKDIR /app
COPY . .
RUN yarn install 
RUN yarn build
ENV REACT_APP_BACKEND_URL http://localhost:8080
EXPOSE 3000

CMD [ "yarn", "start" ]
