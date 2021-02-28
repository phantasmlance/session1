<?php 
	require "connect.php";

	$assetid = $_POST['assetid'];
	$assetphoto = $_POST['assetphoto'];

	$query = "INSERT INTO assetphotos VALUES (null, '$assetid', '$assetphoto')";

	if (mysqli_query($connect, $query)) {
		# success
		echo "1";
	} else {
		# fail
		echo "0";
	}
?>