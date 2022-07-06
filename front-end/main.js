const { app, BrowserWindow, Menu, shell, dialog} = require('electron');
const fs = require("fs");
const ipc = require('electron').ipcMain;
const {join} = require('path');
const isMac = process.platform === 'darwin'


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
          if (files)
          {
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

const root = {
  name: 'foo',
  children: [{
    name: 'bar',
    children: [{
      name: 'bar',
      children: []
    }, {
      name: 'baz',
      children: []
    }]
  }]
}

const tree = require('electron-tree-view')({
  root,
  container: .querySelector('.explorerContainer'),
  children: c => c.children,
  label: c => c.name
})

tree.on('selected', item => {
  // adding a new children to every selected item
  item.children.push({ name: 'foo', children: [] })

  tree.loop.update({ root })

  console.log('item selected')
})


