const { app, BrowserWindow, ipcRenderer, contextBridge} = require('electron')
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
    Save_nb: (callback) => ipcRenderer.on("SAVE_NB", async (event, args) => {
            let saving = callback(args);
            console.log("saving");
            console.log(saving)
           let string_tmp = "" + saving.delete2+" "+ saving.timing+ "\n";
            console.log(string_tmp)
            await fs.promises.appendFile("tmp_json.txt", string_tmp);
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

    Options : (callback) => ipcRenderer.on("OPTIONS", (event, args) =>
        {
            callback();
        }
    ),

    Colortheme : (callback) => ipcRenderer.on("COLORTHEME", (event, args) =>
        {
            callback(args);
        }
    ),
}

contextBridge.exposeInMainWorld("api", API);