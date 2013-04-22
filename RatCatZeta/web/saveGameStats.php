<?php
include('GameTracker.php');
//end point for the AJAX call, saves all the posted stats.
$gameTracker = new GameTracker();
$gameTracker->setAIScore($_POST['ai_score']);
$gameTracker->setPlayerScore($_POST['player_score']);
$gameTracker->setDifficulty($_POST['difficulty']);
$gameTracker->setRounds($_POST['rounds']);
$gameTracker->setHandImprovement($_POST['initial_hand_score']);
$gameTracker->saveGame();

return json_encode('SUCESS');

?>