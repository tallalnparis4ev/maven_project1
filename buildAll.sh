#!/bin/bash
if [ $# -eq 1 ] && [ $1 = "local" ]
then
cp ./front-end/src/LocalApp.js ./front-end/src/App.js
else
cp ./front-end/src/LabApp.js ./front-end/src/App.js
fi
cd ./front-end
npm run build
cd ..
cd ./back-end
gradle build
cd ..
