const encryptionKey = window.crypto.getRandomValues(new Uint8Array(16));

const keyString = btoa(String.fromCharCode.apply(null, encryptionKey));
socket.send(JSON.stringify({type: "key", data: keyString}));

const plaintext = "Hello, world!";
const iv = window.crypto.getRandomValues(new Uint8Array(16));
const cipher = await window.crypto.subtle.encrypt({name: "AES-CBC", iv: iv},
    encryptionKey, new TextEncoder().encode(plaintext));
const ciphertext = iv + new Uint8Array(cipher).toString();
socket.send(JSON.stringify({type: "message", data: ciphertext}));

socket.onmessage = async (event) => {
    const message = JSON.parse(event.data);
    if (message.type === "message") {
        const ciphertext = message.data;
        const iv = ciphertext.slice(0, 16);
        const cipher = ciphertext.slice(16);
        const plaintext =
            new TextDecoder().decode(await window.crypto.subtle.decrypt({name: "AES-CBC", iv: iv},
                encryptionKey, cipher));
        console.log("Received message: " + plaintext);
    }
};




