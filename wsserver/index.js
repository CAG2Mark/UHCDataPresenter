const http=require('http');
const WebSocket = require('ws');
const server=http.createServer((req,res)=>{
	const message=decodeURIComponent(req.url.split('=')[1]);
	connections.forEach(connection=>connection.send(message));
	res.end('ok');
})
const wss = new WebSocket.Server({ server });
const connections=[];
wss.on("connection",ws=>{
	connections.push(ws);
})
server.listen(8080);
