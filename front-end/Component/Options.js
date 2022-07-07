//import {saveFile, config} from "./optionsLoad"

let config = {
    "font-family": "Calibri",
    "font-size": 14,
    "daltonism": false,
    "dyslexia": false,
    "daltonism-type": {
    "tripanopia": false,
        "deuteranopia": false,
        "protanopia": false
}}

let dyslexia = document.getElementById("yes1")
dyslexia.oninput = function (){
    config["dyslexia"] = dyslexia.checked
}

let daltonism = document.getElementById("yes2")
daltonism.oninput = function (){
    config["daltonism"] = daltonism.checked
}

let deutera = document.getElementById("deuteranopia")
deutera.oninput = function (){
    config["daltonism-type"]["deuteranopia"] = deutera.checked
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
}

document.getElementById("saveButton").addEventListener("click", () => {
    console.log(config)
    document.getElementById("optionsBox").style.display = "none";
})

let family = document.getElementById("Family")
family.onchange = function (){
    config["font-family"] = family.value
}