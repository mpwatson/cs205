<?php

require_once('DatabaseHelper.php');

class GameTrackerBundle{
	
	//grabs all the wins/draws/losses for each difficulty
	public static function getAIWins(){
		$query = "SELECT difficulty, COUNT(id) AS total, SUM(CASE WHEN(ai_score < player_score) THEN 1 ELSE 0 END) AS wins, SUM(CASE WHEN(ai_score = player_score) THEN 1 ELSE 0 END) AS draws FROM gametracker GROUP BY difficulty";
		$dbObject = new DatabaseHelper();
		$dbObject->executeSelect($query);
		return $dbObject->returnedRows;
	}

	//Gets the average score for a game
	public static function getAIAverageGameScore(){
		$query = "SELECT difficulty, COUNT(id) AS total, SUM(ai_score) AS total_score FROM gametracker GROUP BY difficulty";
		$dbObject = new DatabaseHelper();
		$dbObject->executeSelect($query);
		return $dbObject->returnedRows;
	}

	//Gets the average hand score
	public static function getAIAverageHandScore(){
		$query = "SELECT difficulty, SUM(rounds) AS total_rounds, SUM(ai_score) AS total_score FROM gametracker GROUP BY difficulty";
		$dbObject = new DatabaseHelper();
		$dbObject->executeSelect($query);
		return $dbObject->returnedRows;
	}


	//get the average hand improvement for the AI difficulties
	public static function getAIAverageHandImprovement(){
		$query = "SELECT difficulty, SUM(rounds) AS total_rounds, SUM(initial_hand_score) AS sum_initial_hands, SUM(ai_score) AS sum_final_hands FROM gametracker GROUP BY difficulty";
		$dbObject = new DatabaseHelper();
		$dbObject->executeSelect($query);
		return $dbObject->returnedRows;
	}
}


?>