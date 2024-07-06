FROM node:20-alpine

WORKDIR /app

COPY package*.json ./

RUN npm install && \
  npm install --save-dev babel-plugin-module-resolver customize-cra react-app-rewired @babel/plugin-proposal-private-property-in-object

COPY . .

EXPOSE 3000

CMD ["npm", "start"]