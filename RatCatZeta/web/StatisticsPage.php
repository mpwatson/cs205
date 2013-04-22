<?php
include('GameTrackerBundle.php');
?>
<html>
<head>
<link href="js/jqPlots/jquery.jqplot.min.css" rel="stylesheet" type="text/css">
<link href="css/main.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqPlots/jquery.jqplot.min.js"></script>
<script type="text/javascript" src="js/jqPlots/plugins/jqplot.barRenderer.min.js"></script>
<script type="text/javascript" src="js/jqPlots/plugins/jqplot.categoryAxisRenderer.min.js"></script>
<script type="text/javascript" src="js/jqPlots/plugins/jqplot.canvasTextRenderer.min.js"></script>
<script type="text/javascript" src="js/jqPlots/plugins/jqplot.canvasAxisTickRenderer.min.js"></script>
<script type="text/javascript" src="js/jqPlots/plugins/jqplot.canvasAxisLabelRenderer.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		//////////////GRAPH 1//////////////////////////////
		//uses a jquery plugin, jqplot to graph all the data. All the code below is just data and settings for the graphs
		<?php 
			$wins =  GameTrackerBundle::getAIWins();
			echo "console.log(".$wins[1]['wins'].");";
			echo "console.log(".(intval($wins[1]['total'])-intval($wins[1]['wins'])).");";
			echo "var stack1 = [".$wins[0]['wins'].",".$wins[1]['wins'].", ".$wins[2]['wins']."];\n";
			echo "var stack2 = [".$wins[0]['draws'].",".$wins[1]['draws'].", ".$wins[2]['draws']."];\n";
			echo "var stack3 = [".(intval($wins[0]['total'])-intval($wins[0]['draws']) - intval($wins[0]['wins'])).",".(intval($wins[1]['total'])-intval($wins[1]['draws'])-intval($wins[1]['wins'])).", ".(intval($wins[2]['total'])-intval($wins[2]['draws'])-intval($wins[2]['wins']))."];\n";
		?>
		console.log(stack1);
		console.log(stack2);
		var difficulties = ["Easy", "Medium", "Hard"]
		var plot1 = $.jqplot('divchart1',[stack1, stack2, stack3],{
			title: "AI Wins vs Losses",
			stackSeries: true,
			seriesDefaults:{
				renderer:$.jqplot.BarRenderer,
				rendererOptions: {
					// Put a 30 pixel margin between bars.
				},
				pointLabels: {show: true}
			},
			series: [
				{label: "Wins"},{label: "Draws"}, {label: 'Losses'}
			],
			axesDefaults: {
				tickRenderer: $.jqplot.CanvasAxisTickRenderer
			},
			axes: {
				xaxis: {
					label: "Difficulty",
					labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
					ticks: difficulties,
          			renderer: $.jqplot.CategoryAxisRenderer
				},
				yaxis: {
					min: 0,
					label: "# of Games",
					labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
					padMin: 0
				}
			},
			legend: {
				show: true
			}
		});


		//////////////GRAPH 2//////////////////////////////
		<?php
			$averageHand = GameTrackerBundle::getAIAverageHandScore();
			$averageImprovement = GameTrackerBundle::getAIAverageHandImprovement();
			$averageEasyHand =(floatval($averageHand[0]['total_score'])/ floatval($averageHand[0]['total_rounds']));
			$averageMediumHand = (floatval($averageHand[1]['total_score'])/ floatval($averageHand[1]['total_rounds']));
			$averageHardHand = (floatval($averageHand[2]['total_score'])/ floatval($averageHand[2]['total_rounds']));
			$averageEasyImprov = (floatval($averageImprovement[0]['sum_initial_hands'])/floatval($averageImprovement[0]['total_rounds']) - $averageEasyHand);
			$averageMediumImprov = (floatval($averageImprovement[1]['sum_initial_hands'])/floatval($averageImprovement[1]['total_rounds']) - $averageMediumHand);
			$averageHardImprov = (floatval($averageImprovement[2]['sum_initial_hands'])/floatval($averageImprovement[2]['total_rounds']) - $averageHardHand);
			echo "var averageHand =[".$averageEasyHand.", ".$averageMediumHand.", ".$averageHardHand."];\n";
			echo "var averageImprov = [".$averageEasyImprov.", ".$averageMediumImprov.", ".$averageHardImprov."];\n";
		?>

		var plot2 = $.jqplot('divchart2', [averageHand, averageImprov], {
			title: "AI Average Hand and Average Hand Improvement",
			seriesDefaults:{
				renderer:$.jqplot.BarRenderer,
				rendererOptions: {fillToZero: true},
				pointLabels: {show: true}
			},
			series: [
				{label: "Average Hand"}, {label: 'Average Hand Improvement'}
			],
			axesDefaults: {
				tickRenderer: $.jqplot.CanvasAxisTickRenderer
			},
			axes: {
				xaxis: {
					label: "Difficulty",
					ticks: difficulties,
					labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
          			renderer: $.jqplot.CategoryAxisRenderer
				},
				yaxis: {
					label: "Hand Score",
					labelRenderer: $.jqplot.CanvasAxisLabelRenderer,
					padMin: 0
				}
			},
			legend: {
				show: true
			}
		});

	});
</script>

<title>Final Project</title>
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<meta http-equiv="pragma" content="no-cache" />
</head>
<body>
<div class= "main-container">
	<h1>Statistics Page</h1>
	<div id = "left" class = "chart-container">
		<div id="divchart1" class="divchart" >
		</div>
		<h3>Graph Description:</h3>
		<p>The bottom blue bar is how many wins each AI difficulty has gotten. The middle orange bar is how many draws the AI and the player have had. The top grey bar is how many losses the AI has experienced. Together they add up to the total number of games played by each AI difficulty.</p>
	</div>

	<div  id="right" class = "chart-container">
		<div id="divchart2" class="divchart">
		</div>
		<h3>Graph Description:</h3>
		<p><b>Average Hand:</b> This is the average finishing hand score for each difficulty</p>
		<p><b>Average Hand Improvement:</b> This is the average difference between the point value of the hand the AI is dealt and their finishing hand score</p>
	</div>
</div>

</body>


<html>