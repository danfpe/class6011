"use strict";

//Call back function
let wsOpen = false;
function handleConnectCB() {
    wsOpen = true;
}

function handleMessageFromWsCB (event){
    wsResult.value = event.data;

}

function handleAjaxErrorCB(event) {
    console.log("there is a error");
    result.vlaue = "server has a problem";
}
function handleAjaxSuccessCB(event){
    result.value = this.responseText;
}

function handleAjaxCB(event) {
    console.log("success");
}
function handleKeyPressCB(event) {
    

    // enter key
    if(event.type == "click" || event.keyCode == 13) {
        console.log(xvalue.value);
        let x = parseFloat(xvalue.value);
        if(isNaN(x)) {
            alert("make sue x is number");
            xvalue.value ="<enter a number>";
            xvalue.select();
            event.preventDefualt();
            return;
        }

        let y = parseFloat(yvalue.value);
        if(isNaN(y)) {
            alert("make sue x is number");
            yvalue.value ="<enter a number>";
            yvalue.select();
            event.preventDefualt();
            return;
        }
        // result.value = x + y;
        let request = new XMLHttpRequest();
        request.open("GET", "http://localhost:8080/calculate?x=" + x + "&y=" + y);
        request.addEventListener("error", handleAjaxErrorCB);
        request.addEventListener("load", handleAjaxSuccessCB);
        request.send();

        // web socket request
        if(wsOpen) {
            ws.send(x + " " + y);
        } else {
            wsResult.value = "connetion is closed"
        }
       
    }
}

let xvalue = document.getElementById("xValue");
xvalue.addEventListener("keypress", handleKeyPressCB);

let yvalue = document.getElementById("yValue");
yvalue.addEventListener("keypress", handleKeyPressCB);

let result = document.getElementById("result");
let wsResult = document.getElementById("wsresult");

let btn = document.getElementById("addbtn");
btn.addEventListener("click", handleKeyPressCB);

let ws = new WebSocket("ws://localhost:8080");
ws.onopen = handleConnectCB;
ws.onmessage = handleMessageFromWsCB;

// pieces = s.split("&");
// Interger.parseInt(peices[0].split("=")[1])