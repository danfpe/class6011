"use strict";

let canvases = document.getElementsByTagName("canvas");
let canvas = canvases[0];
let ctx = canvas.getContext('2d');

// width and height of canvas
let cWidth = canvas.width;
let cHeight = canvas.height;

// mouse img
let myImg = new Image(); // Very similar to <img scr = "name.jpg">
myImg.src = "cat.jpg";
myImg.xPos = 0;
myImg.yPos = 0;




// bees array
let bees = [];
for (let i = 0; i < 10; i++) {
    let bee = {};
    bee.img = new Image();
    bee.img.src = "bee.png";
    bee.xPos = Math.floor(Math.random() * 1000); // get random xPos from 0 to 999
    bee.yPos = Math.floor(Math.random() * 800); // get random yPos from 0 to 999
    bees[i] = bee;
}

// bee speed
let beeSpeed = [];
for (let i = 0; i < 10; i++){
    let speed = Math.floor(Math.random() * 5);
    beeSpeed[i] = speed;
}


let touched = false;

// set velocity
let velocity = 0;
function setVelocity(){
    velocity += 0.1;
}

// clean the frame
function erase() {
    ctx.fillStyle = "white";
    ctx.fillRect(0, 0, cWidth, cHeight);
}

// mouse move, img move
function handleMover(e) {
    if (!touched) {
        myImg.xPos = e.x - 40;
        myImg.yPos = e.y - 40;
    }
}

// animation
function animate() {
    erase();
    ctx.drawImage(myImg, myImg.xPos, myImg.yPos);
    //  check is touched
    for (let i = 0; i < bees.length; i++) {
        ctx.drawImage(bees[i].img, bees[i].xPos, bees[i].yPos);
        let distance = Math.sqrt(((myImg.xPos - bees[i].xPos) * (myImg.xPos - bees[i].xPos)) + ((myImg.yPos - bees[i].yPos) * (myImg.yPos - bees[i].yPos)));
        if (distance < 40) {
            touched = true;
        }
    }

    // if !touched move, if touched stop move
    for (let j = 0; j < bees.length; j++) {
        if (!touched) {
            if ((myImg.xPos - bees[j].xPos) > 40) {
                bees[j].xPos += beeSpeed[j] + velocity;
            } else if ((myImg.xPos - bees[j].xPos) < 40) {
                bees[j].xPos -= beeSpeed[j] + velocity;
            }

            if ((myImg.yPos - bees[j].yPos) > 40) {
                bees[j].yPos += beeSpeed[j] + velocity;
            } else if ((myImg.yPos - bees[j].yPos) < 40) {
                bees[j].yPos -= beeSpeed[j] + velocity;
            }
        }

    }
    window.requestAnimationFrame(animate); // <- 60 Hz
}

function main() {
    window.requestAnimationFrame(animate);
}

window.setInterval( setVelocity, 100);
document.onmousemove = handleMover;
window.onload = main;

