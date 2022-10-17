document.writeln("Hello World!");
console.log("Hello World");
let myArray = ["hello", true, 4, 3.4, {"name": "David"}];
console.log(myArray);

function myFunction(param1, param2) {
    console.log("this is standard form");
    console.log(param1, param2);
}

let myFunction2 = function(param1, param2) {
    console.log("this is the second way!");
    console.log(param1, param2);
} 

myFunction(3, 4);
myFunction2("hello world", 223);