let config = {
    "font-family": "Courier New",
    "font-size": 14,
    "daltonism": false,
    "dyslexia": false,
    "daltonism-type": {
        "tripanopia": false,
        "deuteranopia": false,
        "protanopia": false
    },
    "colortheme": 'light'
}


window.api.OpenFile((path, text) => {
    document.getElementById("markdown").innerHTML = text;
    document.getElementById("title").innerText = path;
})

window.api.SaveFile(() => {
    console.log(document.getElementById("markdown").value);
    return document.getElementById("markdown").value;
})

window.api.OpenDirectory((path, root, tree) => {
    tree.on('selected', item => {
        // adding a new children to every selected item
        item.children.push({ name: 'foo', children: [] })
        tree.loop.update({ root })
        console.log('item selected')
    })
})

window.api.Options(() => {
    document.getElementById("optionsBox").style.display = "inline";

});

window.api.Mode_th((args) => {
    changeTheme(args.toString())
})

window.api.Colortheme((args) => {
    changeSyntaxColor(args)
})

function changeTheme(args){
    config["colortheme"]= args
    if (args === "light") {
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
    if (args === "dark"){
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
    if (args === "epita") {
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
}

const changeSelected = (name) => {
    const select = document.querySelector('#Family');
    const options = Array.from(select.options);
    const optionToSelect = options.find(item => item.text === name);
    optionToSelect.selected = true;
};

let dyslexia = document.getElementById("yes1")
dyslexia.oninput = function (){
    config["dyslexia"] = dyslexia.checked
    if (config['dyslexia']){
        changeTheme("dark")
        let textArea = document.getElementsByTagName('textarea')
        for (let i = 0; i < textArea.length; i++) {
            textArea[i].style.fontSize = "20"
            textArea[i].style.fontFamily = "Courier New"
            changeSelected('Courier New')
            document.getElementById('fontSize').setAttribute('value', '20')
        }
    }
    else{
        let textArea = document.getElementsByTagName('textarea')
        for (let i = 0; i < textArea.length; i++) {
            textArea[i].style.fontSize = config['font-size']
            textArea[i].style.fontFamily = config['font-family']
            changeSelected(config['font-family'])
            document.getElementById('fontSize').setAttribute('value', config['font-size'])
        }
    }
}

let daltonism = document.getElementById("yes2")
daltonism.oninput = function (){
    config["daltonism"] = daltonism.checked
    if (config["daltonism"])
    {
        document.getElementById("daltonismBox").style.display = 'flex'
        document.getElementById('optionsBox').style.height = "500px";

        changeTheme("light")
    }
    else
    {
        document.getElementById("daltonismBox").style.display = 'none'
        document.getElementById('optionsBox').style.height = "350px";
    }
}

let deutera = document.getElementById("deuteranopia")
deutera.oninput = function (){
    config["daltonism-type"]["deuteranopia"] = deutera.checked
    if (config["daltonism-type"]["deuteranopia"])
        changeSyntaxColor('monokai')
    else
        changeSyntaxColor('dark')
}

let tripano = document.getElementById("tripanopia")
tripano.oninput = function (){
    config["daltonism-type"]["tripanopia"] = tripano.checked
}

let protano = document.getElementById("protanopia")
protano.oninput = function (){
    config["daltonism"]["protanopia"] = protano.checked
}

let fontSize = document.getElementById("fontSize")
fontSize.oninput = function (){
    config["font-size"] = fontSize.value
    let textArea = document.getElementsByTagName('textarea')
    for (let i = 0; i < textArea.length; i++) {
        textArea[i].style.fontSize = config['font-size']
    }
}

let family = document.getElementById("Family")
family.onchange = function () {
    config["font-family"] = family.value
    let textArea = document.getElementsByTagName('textarea')
    for (let i = 0; i < textArea.length; i++) {
        textArea[i].style.fontFamily = config['font-family']
    }
}

document.getElementById("saveButton").addEventListener("click", () => {
    document.getElementById("optionsBox").style.display = "none";

    if (!config["dyslexia"] && !config["daltonism"]) {
        let textArea = document.getElementsByTagName('textarea')
        for (let i = 0; i < textArea.length; i++) {
            textArea[i].style.fontSize = config['font-size']
            textArea[i].style.fontFamily = config['font-family']
        }
    }
    else if (config["dyslexia"]){
    }
})

function changeSyntaxColor(theme){
    (async ({chrome, netscape}) => {
        // add Safari polyfill if needed
        if (!chrome && !netscape)
            await import('https://unpkg.com/@ungap/custom-elements');

        const {default: HighlightedCode} =
            await import('https://unpkg.com/highlighted-code');

        // bootstrap a theme through one of these names
        // https://github.com/highlightjs/highlight.js/tree/main/src/styles
        HighlightedCode.useTheme(theme);
        changeTheme(config["colortheme"])
    })(self);
}

changeTheme(config['colortheme'])

