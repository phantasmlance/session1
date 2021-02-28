<?php 

	$hostname = "localhost";
	$username = "root";
	$password = "";
	$database = "session1";

	$connect = mysqli_connect($hostname, $username, $password, $database);
	mysqli_query($connect, "SET NAMES 'utf8'");
?>