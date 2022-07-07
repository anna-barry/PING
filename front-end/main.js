const { app, BrowserWindow, Menu, dialog, ipcMain} = require('electron');

//import {MyAPI} from './testAPI.js';
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
    const url = "http://localhost:4567/"+path;
    console.warn("http://localhost:4567/"+path);
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
          API_test.get('hello').then((response) =>{
            console.log("worked?");
          }).catch((error) => console.error((error)))
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


