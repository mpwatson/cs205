<?php
include('DatabaseHelper.php');
class GameTracker{

	//Simple class with getters and setters to store the stats for a game
	private $id = null;
	private $ai_score = null;
	private $player_score = null;
	private $difficulty = null;
	private $rounds = null;
	private $hand_improvement = null;

	public function setId($id){
		$this->id = $id;
	}

	public function setAIScore($ai_score){
		$this->ai_score = $ai_score;
	}

	public function setPlayerScore($player_score){
		$this->player_score = $player_score;
	}

	public function setDifficulty($difficulty){
		$this->difficulty = $difficulty;
	}

	public function setRounds($rounds){
		$this->rounds = $rounds;
	}
	
	public function setHandImprovement($hand_improvement){
		$this->hand_improvement = $hand_improvement;
	}
	
	public function getId(){
		return $this->id;
	}

	public function getAIScore(){
		return $this->ai_score;
	}

	public function getPlayerScore(){
		return $this->player_score;
	}

	public function getDifficulty(){
		return $this->difficulty;
	}

	public function getRounds(){
		return $this->rounds;
	}

	public function getHandImprovement(){
		return $this->hand_improvement;
	}

	public function toJSON(){
		$trackerJSON = array('id' => $this->id,
					  	'ai_score'=> $this->ai_score,
				  		'player_score'=> $this->player_score,
			  			'difficulty'=> $this->difficulty,
			  			'rounds'=> $this->rounds,
			  			'hand_improvement'=> $this->hand_improvement);
		return json_encode($trackerJSON);
	}


	//instantiate the database helper to execute the query
	public function saveGame(){
		$dbObject = new DatabaseHelper();
		$query = "INSERT INTO gametracker (ai_score, player_score, difficulty, rounds, initial_hand_score) VALUES (".$this->ai_score.", ".$this->player_score.", ".$this->difficulty.", ".$this->rounds.", ".$this->hand_improvement.")";
		$dbObject->executeInsert($query);
	}
}

?>