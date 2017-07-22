~~~~
var JSFunctions = Java.type('io.bms.events.parsing.JSFunctions'); // NEVER remove this; it links the script to the Java
// class so that you can use functions that integrate with Minecraft

// add your event functions here, along the lines of the storm example

// rain/thunder/lightning storm event
var eventStorm = function() {
    	JSFunctions.startRain();
    	return "eventStorm"; // always return the event's function name
};

// spawn a fire
// world IDs: 0 (overworld), 1 (nether), 2 (end)
var eventFire = function() {
	JSFunctions.spawnBlock(0, 0, 2, 0, FIRE);
	return "eventFire";
};

// spawn an explosion (does block damage)
var eventExplosion = function() {
	JSFunctions.explode(0, 0, 0, 0, 4);
	return "eventExplosion";
};

// spawn a chicken horde
var eventChickens = function(){
	for (i = 0; i < 10; i++) {
		JSFunctions.spawnEntity(0, 0, 1, 0, CHICKEN);
	}
	return "eventChickens";
};

var eventArray = [eventStorm] // add the names of your event functions to this array

// don't touch this function; it just calls a random event from the array above
var callEvents = function() {
    	var randomValue = Math.floor(Math.random() * eventArray.length);
    	return eventArray[randomValue]();
};
~~~~