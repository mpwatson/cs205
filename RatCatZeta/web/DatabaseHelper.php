<?php
//Author: Scott MacEwan

class DatabaseHelper{

	private $dbname = 'WMACEWAN_ratcatbeta';
	private $dbhost = 'webdb.uvm.edu';
	private $dbuser = 'wmacewan_writer';
	private $dbpass = 'Wg1tETnA1iVaAfpk';
	private $connection = null;
	private $dsn = null;
	public $returnedRows = array();
	public function __construct(){
		$this->dsn = "mysql:dbname=".$this->dbname.";host=".$this->dbhost;
		$this->connection = $this->dbConnect();
	}

	//Connect the Databse to the UVM webdb server... ignore the credentials above....
	public function dbConnect(){
		//Connect to the database and select the database		
		try{
			$connection = new PDO($this->dsn, $this->dbuser, $this->dbpass);
			$connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
			$connection->setAttribute(PDO::ATTR_EMULATE_PREPARES, false);
			return $connection;
		}catch(PDOException $err){$this->error = true; echo $err;}	// if we cant connect, return null	
	} // end dbConnect

	//next two functions excute implemented queries 
	public function executeSelect($query){
		//prepare
		$stmt = $this->connection->prepare($query);
		//execute it
		$stmt->execute();
		//set the returned rows
		$this->returnedRows = $stmt->fetchAll(PDO::FETCH_ASSOC);
	}

	public function executeInsert($query){
		$stmt = $this->connection->prepare($query);
		$stmt->execute();
	}

}

?>