FROM node:21.1.0-alpine AS builder
WORKDIR /app
COPY public ./public
COPY src/ ./src
COPY package.json .
COPY package-lock.json .
COPY tsconfig.json .
COPY index.html .
COPY tsconfig.node.json .
COPY vite.config.ts .

RUN npm install
RUN npm run build

FROM nginx:stable-alpine as runner
ENV NODE_ENV production
COPY --from=builder /app/dist /usr/share/nginx/html
COPY ./.nginx/nginx.conf /etc/nginx/conf.d/default.conf

RUN apk add --no-cache gettext
COPY ./.nginx/entrypoint.sh /entrypoint.sh
RUN chmod 777 /entrypoint.sh
COPY public/envVars.js /usr/share/nginx/html/envVars.original.js

EXPOSE 80
ENTRYPOINT ["sh", "/entrypoint.sh"]