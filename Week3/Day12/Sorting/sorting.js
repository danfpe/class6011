function findSmallestIndex (array, iteration, flag) {
    let smallest = array[iteration];
    let smallestIndex = iteration;
    let isObject = false;
    if (typeof(array[0]) === "object") {
        isObject = true;
    }
    for (let i = iteration + 1; i < array.length; i++) {
        if (!isObject){
            if(array[smallestIndex] > array[i]) {
                smallestIndex = i;
            }
        } 
        else {
            if(array[smallestIndex][flag].localeCompare(array[i][flag]) ===1 ) {
                smallestIndex = i;
            }
        }
    }
    return smallestIndex;
}

function selectionSort(array, flag) {
    for (let i= 0; i < array.length; i ++) {
        let sIndex = findSmallestIndex(array, i, flag);
        let temp = array[i];
        array[i] = array[sIndex];
        array[sIndex] = temp;
    }
}

function compareArrays(array1, array2, operator) {
    if (array1.length != array2.length) {
        return false;
    }
    else {
        for (var i = 0; i < array1.length; i++) {
            if (operator === "<"){
                if (array1[i] < array2[i]){
                    return "a less than b";
                }else {
                    return "a is not less than b";
                }
            }
            else if (operator === ">"){
                if (array1[i] > array2[i]){
                    return "a larger than b";
                } else {
                    return "a is not larger than b"
                }
            }
            else {
                return "invalid operator";
            }
            
        }
    }
}


let intArray = [9,5,7,2];
let floatArray = [9.1, 3.4, 5.6, 2.1];
let stringArray = ["hello", "this", "apple", "class"];
let mixArray = ["fix", 5, 3, "banana"];

let peopleList= [{"first_name": "hello", "last_name": "lili"}, 
{"first_name": "bill", "last_name": "fly"}, {"first_name": "apple", "last_name": "cool"}];

selectionSort(intArray);
console.log("after int", intArray);

selectionSort(floatArray);
console.log("after float", floatArray);

selectionSort(stringArray);
console.log("after stringArray", stringArray);

selectionSort(mixArray);
console.log("after mixArray", mixArray);

console.log(compareArrays(intArray, floatArray, "<"));
console.log(compareArrays(intArray, floatArray, ">"));

// compare object;
selectionSort(peopleList, "first_name");
console.log("object comparison", peopleList);
