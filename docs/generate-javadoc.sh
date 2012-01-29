#!/bin/sh

javadoc \
-d javadoc \
-doctitle "RESTful Android" \
-windowtitle "RESTful Android" \
-sourcepath ../src com.jeremyhaberman.restfulandroid \
-classpath $1 \
-subpackages com.jeremyhaberman.restfulandroid.activity:com.jeremyhaberman.restfulandroid.provider:com.jeremyhaberman.restfulandroid.security:com.jeremyhaberman.restfulandroid.service:com.jeremyhaberman.restfulandroid.util
