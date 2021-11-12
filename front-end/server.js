const express = require('express');
const path = require('path');
const app = express();
app.use(express.static(path.join(__dirname, 'build')));
app.get('/', function(req, res) {
	res.sendFile(path.join(__dirname, 'build', 'index.html'));
});
app.listen(9001);

const WebSocket = require('ws');
const ByteBuffer = require('ByteBuffer');
const frontend = new WebSocket.Server({ port: 6969 });
const exit = new WebSocket.Server({ port: 6970 });
const net = require('net');
frontend.on('connection', function connection(ws) {
  var backend = new net.Socket();
  backend.connect(4545,'localhost', function(){
    console.log('connected');
  })
  backend.on('data', function(data){
    try {
      JSON.parse(data.toString());
    } catch (e) {}
		try {
			ws.send(data);
		} catch (e) {}
  })
  ws.on('message', function incoming(message) {
			try{
				backend.write(message);
			}catch(e){}
  });

  backend.on('error', function(error){
    console.log(error);
  })
  ws.on('error', function(error){
    console.log(error);
  })
});

exit.on('connection', function connection(ws) {
  var backend = new net.Socket();
  backend.connect(4546,'localhost', function(){
    console.log('connected');
  })
  backend.on('data', function(data){
    try {
      JSON.parse(data.toString());
    } catch (e) {}
		try {
			ws.send(data);
		} catch (e) {}
  })
  ws.on('message', function incoming(message) {
			try{
				backend.write(message);
			}catch(e) {}
  });

  backend.on('error', function(error){
    console.log("ERROR CAUGHT");
  })
  ws.on('error', function(error){
    console.log("ERROR CAUGHT");
  })
});
