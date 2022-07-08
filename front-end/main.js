const { app, BrowserWindow,  Menu, dialog, ipcMain} = require('electron');
const fs = require("fs");
const ipc = require('electron').ipcMain;
const {join} = require('path');
const os = require('os');
const {options} = require("marked");
//const pty = require('node-pty');
const isMac = process.platform === 'darwin'
var shell = os.platform() === "win32" ? "powershell.exe" : "bash";

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
        accelerator:'CmdOrCtrl+P',
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
        label: 'Monokai',
        click: async () => {
          window.webContents.send('COLORTHEME', "monokai");
        },
      },
      {
        label: 'Dark',
        click: async () => {
          window.webContents.send('COLORTHEME', "dark");
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


