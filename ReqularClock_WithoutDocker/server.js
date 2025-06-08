const http = require('http');
const fs = require('fs');
const url = require('url');

// Состояние часов
let state = {
    hours: new Date().getHours(),
    minutes: new Date().getMinutes(),
    seconds: new Date().getSeconds(),
    alarmHours: 7,
    alarmMinutes: 0,
    alarmOn: false,
    settingAlarm: false,
    alarmTriggered: false
};

// Обновляем время каждую секунду
setInterval(() => {
    const now = new Date();
    state.hours = now.getHours();
    state.minutes = now.getMinutes();
    state.seconds = now.getSeconds();
    
    // Проверка будильника
    if (state.alarmOn && !state.settingAlarm && 
        state.hours === state.alarmHours && 
        state.minutes === state.alarmMinutes &&
        state.seconds === 0) {
        state.alarmTriggered = true;
        console.log("ALARM! ALARM! ALARM!");
    }
}, 1000);

const server = http.createServer((req, res) => {
    const parsedUrl = url.parse(req.url, true);
    
    if (req.method === 'GET' && parsedUrl.pathname === '/') {
        fs.readFile('./index.html', (err, data) => {
            if (err) {
                res.writeHead(500);
                return res.end('Error loading index.html');
            }
            res.writeHead(200, {'Content-Type': 'text/html'});
            res.end(data);
        });
    } 
    else if (req.method === 'GET' && parsedUrl.pathname === '/style.css') {
        fs.readFile('./style.css', (err, data) => {
            if (err) {
                res.writeHead(500);
                return res.end('Error loading style.css');
            }
            res.writeHead(200, {'Content-Type': 'text/css'});
            res.end(data);
        });
    }
    else if (req.method === 'GET' && parsedUrl.pathname === '/state') {
        res.writeHead(200, {'Content-Type': 'application/json'});
        res.end(JSON.stringify(state));
    }
    else if (req.method === 'POST' && parsedUrl.pathname === '/button') {
        let body = '';
        req.on('data', chunk => {
            body += chunk.toString();
        });
        req.on('end', () => {
            const { button } = JSON.parse(body);
            
            if (button === 'H') {
                if (state.settingAlarm) {
                    state.alarmHours = (state.alarmHours + 1) % 24;
                }
            } 
            else if (button === 'M') {
                if (state.settingAlarm) {
                    state.alarmMinutes = (state.alarmMinutes + 1) % 60;
                }
            } 
            else if (button === 'A') {
                if (state.alarmTriggered) {
                    // Выключаем сработавший будильник
                    state.alarmTriggered = false;
                } else if (state.settingAlarm) {
                    // Выходим из режима установки будильника
                    state.settingAlarm = false;
                } else if (state.alarmOn) {
                    // Выключаем будильник
                    state.alarmOn = false;
                } else {
                    // Включаем будильник и переходим в режим установки
                    state.alarmOn = true;
                    state.settingAlarm = true;
                }
            }
            
            res.writeHead(200, {'Content-Type': 'application/json'});
            res.end(JSON.stringify(state));
        });
    }
    else {
        res.writeHead(404);
        res.end('Not found');
    }
});

server.listen(3000, () => {
    console.log('Server running at http://localhost:3000/');
});