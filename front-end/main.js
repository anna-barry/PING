const { app, BrowserWindow, Menu} = require('electron')
//const nativeImage = require('electron').nativeImage;

//let appIcon = nativeImage.createFromPath(path.join(__dirname, 'img', 'snippetSquare.png'));
let win;


function createWindow() {

  win = new BrowserWindow({
    center: true,
    minHeight: 650,
    minWidth: 600,
    //icon: appIcon,
    frame:false,
    fullscreen:true,
    backgroundColor: "#16181A"
  });

  win.loadFile('index.html')

  win.on('closed', () => {

  })

  win.on('ready-to-show', () => {

    win.show();
    win.focus();

  })

}


app.on('ready', () => {
  createWindow();
})

/*

const template = [
  {
    label: 'File',
    submenu: [
      {
         label: 'New File',
         click () { win.webContents.send("newfile"); }
      },
      {
         label: 'New Folder',
         click () { win.webContents.send("newfolder"); }          
      },
      { type: 'separator' },
      {
        label: 'Save',
        click () { win.webContents.send("save"); }
     },
     
    ]
  },
  {
    label: 'View',
    submenu: [
      { role: 'reload' },
      { role: 'forcereload' },
      { role: 'toggledevtools' },
      { type: 'separator' },
      { role: 'resetzoom' },
      { role: 'zoomin' },
      { role: 'zoomout' },
      { type: 'separator' },
      { role: 'togglefullscreen' }
    ]
  },
  {
    role: 'window',
    submenu: [
      { role: 'minimize' },
      { role: 'close' }
    ]
  },
  {
    role: 'help',
    submenu: [
      {
        label: 'Learn More',
        click () { require('electron').shell.openExternal('https://electronjs.org') }
      }
    ]
  }
]

if (process.platform === 'darwin') {
  template.unshift({
    label: app.getName(),
    submenu: [
      { role: 'about' },
      { type: 'separator' },
      { role: 'services' },
      { type: 'separator' },
      { role: 'hide' },
      { role: 'hideothers' },
      { role: 'unhide' },
      { type: 'separator' },
      { role: 'quit' }
    ]
  })

  // Edit menu
  template[1].submenu.push(
    { type: 'separator' },
    {
      label: 'Speech',
      submenu: [
        { role: 'startspeaking' },
        { role: 'stopspeaking' }
      ]
    }
  )

  // Window menu
  template[3].submenu = [
    { role: 'close' },
    { role: 'minimize' },
    { role: 'zoom' },
    { type: 'separator' },
    { role: 'front' }
  ]
}

const menu = Menu.buildFromTemplate(template)
Menu.setApplicationMenu(menu);

*/

app.on('window-all-closed', () => {


  if (process.platform !== 'darwin') {
    app.quit()

  }
})

app.on('activate', () => {


  if (win === null) {
    createWindow()
  }
})
