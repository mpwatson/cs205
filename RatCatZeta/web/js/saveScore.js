<script>
$(document).ready(function(){
	var difficulty = 0;
	var player_score = 0;
	var ai_score = 0;
	var rounds = 0;
	var initial_hand_score = 0;

	function setDifficulty(ai_difficulty){
		difficulty = ai_difficulty;
	}

	function incrementRound(){
		round ++;
	}

	function addPlayerScore(player_round_score){
		player_score += player_round_score;
	}

	function addAiScore(ai_round_score){
		ai_score += ai_round_scorel
	}

	function addInitialHandScore(ai_initial_hand_value){
		initial_hand_score += ai_initial_hand_value;
	}

	function storeGame(){
		$.ajax({
			url: ,
			type: "POST",
			data: {difficulty: difficulty, player_score: player_score, ai_score: ai_score, rounds: rounds, initial_hand_score: initial_hand_score},
			success: function(response){
				console.log('success');
			},
			error: function(msg){
				console.log(msg);
				console.log('error');
			}
		});
	}
});

</script>