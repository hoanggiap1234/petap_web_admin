#!/bin/sh

echo "The application will start in ${STARTUP_SLEEP}s..." && sleep ${STARTUP_SLEEP}
exec java ${JAVA_OPTS} -noverify -XX:+AlwaysPreTouch -Djava.security.egd=file:/dev/./urandom -cp /app/resources/:/app/classes/:/app/libs/* "io.petapp.PetAppServiceApplication"  "$@"
