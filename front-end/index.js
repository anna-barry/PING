

<!-- index.html -->
window.api.OpenFile((path, text) => {
    document.getElementById("markdown").innerHTML = text;
    document.getElementById("title").innerText = path;
})

window.api.SaveFile(() => {
    console.log(document.getElementById("markdown").value);
    return document.getElementById("markdown").value;
})

// opening directory arborescence
window.api.OpenDirectory((path, root, tree) => {
    tree.on('selected', item => {
        // adding a new children to every selected item
        item.children.push({ name: 'foo', children: [] })
        tree.loop.update({ root })
        console.log('item selected')
    })
})


window.api.Other((mode) => {
    if (mode === "dyslexique") {
        document.getElementById("fenetre_dyslexique").style.display = true;
    }
    if (mode === "daltonien") {

    }
})
window.api.Mode_th((args) => {
    if (args.toString() === "light") {
        document.body.background = "#FFFFFF";
        document.body.color = "#000000";
        document.getElementById("markdown").style.background = "#FFFFFF";
        document.getElementById("markdown").style.color = "#000000";
        document.getElementById("resizableExplorer").style.background = "#D1D1D1";
        document.getElementById("resizableExplorer").style.backgroundImage = "url(./img/logo-light.png)";
        document.getElementById("resizableExplorer").style.color = "#FFFFFF";
        document.getElementById("barstyle").style.background = "#D1D1D1";
        document.getElementById("barstyle").style.color = "#000000";
        document.getElementById("ouep-ici").style.background = "#D1D1D1";
        document.getElementById("ouep-ici").style.color = "#000000";
        document.getElementById("ouep-ici2").style.background = "#D1D1D1";
        document.getElementById("ouep-ici2").style.color = "#000000";
        document.getElementById("folderName").style.background = "#FFFFFF";
        document.getElementById("folderName").style.color = "#000000";
        document.getElementById("syncFiles").style.background = "#444242";
        document.getElementById("syncFiles").style.color = "#000000";
        document.getElementById("test").style.background = "#444242";
        document.getElementById("test").style.color = "#FFFFFF";
    }
    if (args.toString() === "dark"){
        document.body.background = "#636363";
        document.body.color = "#FFFFFF";
        document.getElementById("markdown").style.background = "#313131";
        document.getElementById("markdown").style.color = "#FFFFFF";
        document.getElementById("resizableExplorer").style.background = "#636363";
        document.getElementById("resizableExplorer").style.backgroundImage = "url(./img/logo.png)";
        document.getElementById("resizableExplorer").style.color = "#FFFFFF";
        document.getElementById("barstyle").style.background = "#636363";
        document.getElementById("barstyle").style.color = "#FFFFFF";
        document.getElementById("ouep-ici").style.background = "#636363";
        document.getElementById("ouep-ici").style.color = "#000000";
        document.getElementById("ouep-ici2").style.background = "#636363";
        document.getElementById("ouep-ici2").style.color = "#000000";
        document.getElementById("folderName").style.background = "#444242";
        document.getElementById("folderName").style.color = "#FFFFFF";
        document.getElementById("changeFolder").style.background = "#D1D1D1";
        document.getElementById("changeFolder").style.color = "#FFFFFF";
        document.getElementById("test").style.background = "#D1D1D1";
        document.getElementById("test").style.color = "#FFFFFF";
    }
    if (args.toString() === "epita") {
        document.body.background = "#0e1e5d";
        document.body.color = "#FFFFFF";
        document.getElementById("markdown").style.background = "#05113b";
        document.getElementById("markdown").style.color = "#FFFFFF";
        document.getElementById("resizableExplorer").style.background = "#0e1e5d";
        document.getElementById("resizableExplorer").style.backgroundImage = "url(./img/epita-final.png)";
        document.getElementById("resizableExplorer").style.color = "#42f50d";
        document.getElementById("barstyle").style.background = "#0e1e5d";
        document.getElementById("barstyle").style.color = "#42f50d";
        document.getElementById("ouep-ici").style.background = "#0e1e5d";
        document.getElementById("ouep-ici").style.color = "#42f50d";
        document.getElementById("ouep-ici2").style.background = "#0e1e5d";
        document.getElementById("ouep-ici2").style.color = "#42f50d";
        document.getElementById("folderName").style.background = "#05113b";
        document.getElementById("folderName").style.color = "#FFFFFF";
        document.getElementById("changeFolder").style.background = "#0e1e5d";
        document.getElementById("changeFolder").style.color = "#42f50d";
        document.getElementById("test").style.background = "#102db2";
        document.getElementById("test").style.color = "#42f50d";
    }
})

