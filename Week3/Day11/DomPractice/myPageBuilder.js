function main() {
    let myh1 = document.createElement( 'h1');
    let myh1Text = document.createTextNode( "this is the header" );
    addChildren(myh1Text, myh1);
    let myPar = document.createElement( 'p');
    let myText = document.createTextNode( "this is the first paragraph" );
    addChildren(myText, myPar);
    let myPar1 = document.createElement( 'p');
    let myText1 = document.createTextNode( "this is the second paragraph" );
    addChildren(myText1, myPar1);
    document.body.style.backgroundColor = "green";
}

function addChildren(myText, myPar) {
    myPar.appendChild(myText);
    document.body.appendChild(myPar);
}

window.onload = main;
