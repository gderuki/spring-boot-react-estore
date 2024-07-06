FROM nginx:alpine

COPY config/nginx.conf /etc/nginx/nginx.conf
# uncomment when deploying to production
# COPY ./build /var/www/ 

EXPOSE 80

CMD [ "nginx", "-g", "daemon off;" ]