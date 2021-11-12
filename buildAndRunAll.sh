#!/bin/bash
if [ $# -eq 1 ] && [ $1 = "local" ]
then
cp ./front-end/src/LocalApp.js ./front-end/src/App.js
else
cp ./front-end/src/LabApp.js ./front-end/src/App.js
cd ./front-end
npm run build
cd ..
fi
cd ./back-end
gradle build
cd ..
if [ $# -eq 1 ] && [ $1 = "local" ]
then 
java -jar ./back-end/build/libs/Game.jar & node ./front-end/server.js & npm start --prefix ./front-end/ &
else
java -jar ./back-end/build/libs/Game.jar & node ./front-end/server.js &
fi



