const dgram = require("dgram");
const server = dgram.createSocket("udp4");

const PORT = 1234;

server.on("message", (msg, rinfo) => {
    console.log(`Received message: "${msg}" from ${rinfo.address}:${rinfo.port}`);
});

server.on("listening", () => {
    const address = server.address();
    console.log(`Listening for UDP messages on ${address.address}:${address.port}`);
});

server.bind(PORT);

