const { ipcRenderer, contextBridge } = require('electron')
const fs = require("fs");

const API = {
    OpenFile : (callback) => ipcRenderer.on("FILE_OPEN", (events, args) =>
        {
            //console.log(args)
            const content = fs.readFileSync(args, 'utf8').toString();
            callback(args, content);
        }
    ),
    SaveFile : (callback) => ipcRenderer.on("SAVE_FILE", (events, args) =>
        {
            let con = callback();
            fs.promises.writeFile(args, con.toString());
        }
    ),
    Mode_th : (callback) => ipcRenderer.on("MODE", (event , args) =>
        {
            callback(args);
        }
    ),
}

contextBridge.exposeInMainWorld("api", API);