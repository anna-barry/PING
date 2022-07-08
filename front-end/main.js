const { app, BrowserWindow, Menu, dialog, ipcMain} = require('electron');
const fs = require("fs");
const ipc = require('electron').ipcMain;
const {join} = require('path');
const os = require('os');
const axios = require('axios');
//const pty = require('node-pty');
const isMac = process.platform === 'darwin'
var shell = os.platform() === "win32" ? "powershell.exe" : "bash";


// _____________________________________
/*
  Linking the backend and front-end
 */


class MyAPI {
  #protocol;
  #host;
  #port;

  constructor(protocol = "http", host = "127.0.0.1", port = 4567) {
    this.#protocol = protocol;
    this.#host = host;
    this.#port = port;
  }

  get protocol() {
    return this.#protocol;
  }

  set protocol(value) {
    this.#protocol = value;
  }

  get host() {
    return this.#host;
  }

  set host(value) {
    this.#host = value;
  }

  get port() {
    return this.#port;
  }

  set port(value) {
    this.#port = value;
  }

  urlFromPath(path) {
    //const url = `${this.#protocol}://${simplifyUrl(`${this.#host}:${this.#port}/${path}`)}`;
    const url = "http://localhost:4000/"+path;
    console.warn("http://localhost:4000/"+path);
    return url;
  }

  get(path) {
    const url = this.urlFromPath(path);
    return axios.get(url);
  }

  post(path, object) {
    const url = this.urlFromPath(path);
    return axios.post(url, object);
  }
}
let API_test = new MyAPI()
// _____________________________________


let window;
let path_open;
//app.whenReady().then(createWindow);

let menu_final =  [
  // { role: 'appMenu' }
  ...(isMac ? [{
    label: app.name,
    submenu : [
      { role: 'about' },
      { type: 'separator' },
      { role: 'services' },
      { type: 'separator' },
      { role: 'hide' },
      { role: 'hideOthers' },
      { role: 'unhide' },
      { type: 'separator' },
      { role: 'quit' },
    ]
  }] : []),
  // { role: 'fileMenu' }
  {
    label: 'File',
    submenu : [
      {
        label: 'Open File',
        click: async () => {
          let files = await dialog.showOpenDialog({
            properties: ['openFile']
          });

          if (files) {
            console.log(files);
            path_open = files.filePaths[0];
            window.webContents.send('FILE_OPEN', path_open);
          }
        },
        accelerator:'CmdOrCtrl+O',
      },
      {
        label: 'Open Directory',
        click: async () => {
          let files = await dialog.showOpenDialog({
            properties: ['openDirectory']
          });
          if (files)
          {
            console.log(files);
            window.webContents.send('DIRECTORY_OPEN', path_open);
          }
        },
        accelerator:'',
      },
      {
        label: 'New File',
        click: async () => {

        },
        accelerator:'',
      },
      { type: 'separator' },
      {
        label: 'Save',
        click:  () => {
          if (!path_open || path_open === undefined)
            return;
          window.webContents.send('SAVE_FILE', path_open);
        },
        accelerator:'',
      },
      {
        label: 'Save As',
        click: async () => {

        },
        accelerator:'',
      },
      {
        label: "Options",
        click: async () => {
          window.webContents.send("OPTIONS")
        },
        accelerator: 'Ctrl+O',
      },
    ]},
  {
    label: 'Features',
    submenu : [
      {
        label: 'Any Clean',
        click: async () => {
          API_test.get('feature/any/clean').then((response) =>{
            console.log("Any Clean");
          }).catch((error) => console.error((error)))
        },
      },
      { type: 'separator' },
      {
        label: 'Maven Clean',
        click: async () => {
          API_test.get('feature/maven/clean').then((response) =>{
            console.log("Maven Clean");
          }).catch((error) => console.error((error)))
        },
      },
      {
        label: 'Maven Compile',
        click: async () => {
          API_test.get('feature/maven/compile').then((response) =>{
            console.log("Maven compile");
          }).catch((error) => console.error((error)))
        },
      },
      {
        label: 'Maven Exec',
        click: async () => {
          API_test.get('feature/maven/exec').then((response) =>{
            console.log("Maven exec");
          }).catch((error) => console.error((error)))
        },
      },
      {
        label: 'Maven Install',
        click: async () => {
          API_test.get('feature/maven/install').then((response) =>{
            console.log("Maven install");
          }).catch((error) => console.error((error)))
        },
      },
      {
        label: 'Maven Package',
        click: async () => {
          API_test.get('feature/maven/package').then((response) =>{
            console.log("Maven package");
          }).catch((error) => console.error((error)))
        },
      },
      {
        label: 'Maven Test',
        click: async () => {
          API_test.get('feature/maven/test').then((response) =>{
            console.log("Maven test");
          }).catch((error) => console.error((error)))
        },
      },
      {
        label: 'Maven Tree',
        click: async () => {
          API_test.get('feature/maven/tree').then((response) =>{
            console.log("Maven tree");
          }).catch((error) => console.error((error)))
        },
      },

    ]},
  {
    label: 'Fatigue Detector',
    submenu : [
      { type: 'separator' },
      {
        label: 'Send Data',
        click: async () => {
          await window.webContents.send('SAVE_NB');

          let res ;
          await fs.promises.readFile("tmp_json.txt").then(function(result) {
            res = result;
          })
              .catch(function(error) {
                console.log(error);
              })
        }
      },
      { type: 'separator' },
      {
        label: 'Diagnostic',
        click: async () => {
          let res = "";
          await fs.promises.readFile("tmp_json.txt").then(function (result) {
            res = ""+ result;
            let numbers = [];
            let deleted = [];
            let tired = false;
            let average = 0;
            let splitString = res.split('\n')
            let l = splitString.length;
            if (l > 1){
              for (let a of splitString){
                numbers.push(parseFloat(a.split(' ')[1]));
                deleted.push(parseFloat(a.split(' ')[0]));
              }
              for (let a = l - 1; a > 0; a--){
                numbers[a] = numbers[a] - numbers[a - 1];
              }
              numbers[0] = 0;
              average += deleted[0];
              for (let a = 1; a < l - 1; a++){
                average += 10000*deleted[a]/numbers[a];
              }
              average = average/(l - 1);
              let tmp = 10000*deleted[l - 1]/numbers[l - 1];
              if (tmp > average*1.5){
                tired = true;
              }
            }
            if (tired)
            {
              dialog.showErrorBox('Analysis', 'You seem to be too tired too work. Please take a break and come back');
            }
            else
              dialog.showErrorBox('Analysis!', 'Nothing anormal detected.');
          })
              .catch(function (error) {
                console.log(error);
              })
        }
      },
    ]},

      // { role: 'editMenu' }
  {
    label: 'Modifier',
    submenu : [
      { role: 'undo' },
      { role: 'redo' },
      { type: 'separator' },
      { role: 'cut' },
      { role: 'copy' },
      { role: 'paste' },
      ...(isMac ? [
        { role: 'pasteAndMatchStyle' },
        { role: 'delete' },
        { role: 'selectAll' },
        { type: 'separator' },
        {
          label: 'Speech',
          submenu: [
            { role: 'startSpeaking' },
            { role: 'stopSpeaking' }
          ]
        }
      ] : [
        { role: 'delete' },
        { type: 'separator' },
        { role: 'selectAll' }
      ])
    ]
  },
  // { role: 'viewMenu' }
  {
    label: 'View',
    submenu: [
      { role: 'reload' },
      { role: 'forceReload' },
      { role: 'toggleDevTools' },
      { type: 'separator' },
      { role: 'resetZoom' },
      { role: 'zoomIn' },
      { role: 'zoomOut' },
      { type: 'separator' },
      { role: 'togglefullscreen' }
    ]
  },
  // { role: 'windowMenu' }
  {
    label: 'Window',
    submenu: [
      {
        label: 'Dark Mode',
        click: () => {
          window.webContents.send('MODE', "dark");
        },
      },
      {
        label: 'Light Mode',
        click: () => {
          window.webContents.send('MODE', "light");
        },
      },
      {
        label: 'Epita Mode',
        click:  () => {
            window.webContents.send('MODE', "epita");
        },
      },
      { type: 'separator' },
      {
        label: 'Dyslexique',
        click: async () => {
          window.webContents.send('OTHER', "dyslexique");
        },
      },
      {
        label: 'Daltoniens',
        click: async () => {
          window.webContents.send('OTHER', "daltonien");
        },
      },
      { type: 'separator' },
      { role: 'minimize' },
      { role: 'zoom' },
      ...(isMac ? [
        { type: 'separator' },
        { role: 'front' },
        { type: 'separator' },
        { role: 'window' }
      ] : [
        { role: 'close' }
      ])
    ]
  },
  {
    role: 'help',
    submenu : [
      {
        label: 'More about us',
        click : async () => {
          const { shell } = require('electron')
          await shell.openExternal("https://github.com/anna-barry/PING")
        },
      }]
  },
];

function createWindow() {
  window = new BrowserWindow({
    center: true,
    minHeight: 650,
    minWidth: 600,
    //icon: ,  //à faire
    backgroundColor: "#16181A",
    webPreferences: {
      nodeIntegration: true,
      preload: join(__dirname, "./preload.js"),
    }
  });
  //window.loadFile(join(__dirname, "./in"))
  window.loadFile('index.html');
  const menu = Menu.buildFromTemplate(menu_final);
  Menu.setApplicationMenu(menu);

  window.on('closed', () => {
    window = null;
  })

  window.on('ready-to-show', () => {
    window.show();
  })
}

  /*
  var ptyProcess = pty.spawn(shell, [], {
    name : "xterm-color",
    cols : 80,
    rows : 24,
    cwd : process.env.HOME,
    env : process.env
  });

  ptyProcess.on("data", function (data)
  {
    window.webContents.send("terminal.incData", data);
  })
  ipcMain.on("terminal.toTerm", function (event, data)
  {
    ptyProcess.write(data);
  })
}
*/

  app.on('ready', () => {
    createWindow();
    window.webContents.send('COLORTHEME', 'light')
  })

  app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') {
      app.quit()
    }
  })

  app.on('activate', () => {
    if (window === null) {
      createWindow()
    }
  })
/*
const interval = setInterval(async () => {
  let payload = {nb_delete: nb_delete};
  console.log("set interval js")
  let res = await API_test.post('timing', payload).catch((error) => console.error((error)));

  let data = res.data;
  console.log(data);
  nb_delete = 0;
}, 1000 * 60 * 5);*/

//clearInterval(interval);


