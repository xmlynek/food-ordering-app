#!/bin/sh
WWW_DIR=/usr/share/nginx/html

INJECT_FILE_SRC="${WWW_DIR}/envVars.original.js"
INJECT_FILE_DST="${WWW_DIR}/envVars.js"

envsubst < $INJECT_FILE_SRC > $INJECT_FILE_DST

[ -z "$@" ] && exec nginx -g 'daemon off;' || exec "$@"