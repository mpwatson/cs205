Final Project: Rat-a-tat-Cat

Team Members:

Michael Watson
Samantha Kern
Adam Savard
Scott MacEwan

ROOT FOLDERS:

src- holds all the java source code for the applet
web- all the code used to hose the applet and connect to the database


SUBFOLDERS:

src-
	gameMain-	|
	instructions-	|	All contain images used on
	mainMenu-	|	the GUI
	modeMenu-	|

	gameplay- Houses the object classes for gameplay
		

		Card.java-		Basic card class
		ComputerPlayer.java	Computer AI Class
		Deck.java		Basic deck class
		Hand.java		Basic hand class
		Player.java		Player class 
	
	

	gui- Holds the intial applet that is deployed
	
		
		GameApplet.java		Inital applet starting class
		GraphicPanel.java	Panel that the GUI is drawn to

	
	main- Folder that contains all the class that maintain and switch
		the game state


		GameState.java		Contains all the game states
		StateManager.java	Dictates the flow between states
		Switchboard.java	Handle for drawin th game, directs
					input to the current game state

	misc- Random classes used to draw objects


	objects- GUI objects that are drawn to the panel

	
	states- 4 main screens for the applet

		
		GameMain.java		The parent state for gameplay
		InstructionsScreen,java	State for the instructions screen
		MainMenu.java		The main state first accessed by the applet
		ModeMenu.java		State for difficulty selecting screen


web-
	css- folder for styling files

	js- folder for all JS plugins

	DatabaseHelper.php-  Class that controls access to the database

	FinalProject.hmtl- Page that hosts the applet

	FinalProject2.jar- Archive for all the java files/images for use 
			   in the applet

	GameTracker.php- Class for ease of use inputing stats into database

	GameTrackerBundle.php- Class that handles queries to retreive stats

	saveGameStats.php- AJAX endpoint for saving stats 

	StatisticsPage.php- page that displays grpahs for the recoreded statistics	
						