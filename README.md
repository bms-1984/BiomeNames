var JSFunctions = Java.type('io.bms.events.parsing.JSFunctions'); // NEVER remove this; it links the script to the Java
// class so that you can use functions that integrate with Minecraft

// add your event functions here, along the lines of the storm example

// random rain/thunder/lightning storm event
var eventStorm = function() {
    JSFunctions.startRain();
};

var eventArray = [eventStorm] // add the names of your event functions to this array

// don't touch this function; it just calls a random event from the array above
var callEvents = function() {
    eventArray[Math.floor(Math.random() * eventArray.length)]();
};
