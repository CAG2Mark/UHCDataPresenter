/* jshint esversion:6 */

const deathsWidth = 270;
const playersWidth = 240;

const playersHeight = 36;

var deathContainer = new DataContainer(3, deathsWidth, 108, 36, false, {top:14, right:16});

const leftPadding = 0;

const playersCount = 4;
var playersContainer = new DataContainer(playersCount, playersWidth, playersHeight * playersCount, playersHeight, false, {top:14, left:16 + leftPadding});

const damageCount = 8;
var damageContainer = new DataContainer(damageCount, playersWidth, playersHeight * damageCount, playersHeight, false, {top:14, left:16*2 + playersWidth + leftPadding});