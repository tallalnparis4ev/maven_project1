if [ $# -eq 1 ] && [ $1 = "local" ]
then 
java -jar ./back-end/build/libs/Game.jar & node ./front-end/server.js & npm start --prefix ./front-end/ &
else
java -jar ./back-end/build/libs/Game.jar & node ./front-end/server.js &
fi

