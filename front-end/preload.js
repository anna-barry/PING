const { app, ipcRenderer, contextBridge} = require('electron')
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
    Other : (callback) => ipcRenderer.on("OTHER", (event, args) =>
        {
            callback(args);
        }
    ),
    OpenDirectory : (callback) => ipcRenderer.on("DIRECTORY_OPEN", (event, args) =>
        {
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
                container: document.querySelector('.explorerContainer'),
                children: c => c.children,
                label: c => c.name
            })
            callback(args, root, tree);
        }
    ),
}

contextBridge.exposeInMainWorld("api", API);