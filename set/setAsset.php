<?php 
	require "connect.php";

	$assetsn = $_POST['assetsn'];
	$assetname = $_POST['assetname'];
	$departmentlocationid = $_POST['departmentlocationid'];
	$employeeid = $_POST['employeeid'];
	$assetgroupid = $_POST['assetgroupid'];
	$description = $_POST['description'];
	$warrantydate = $_POST['warrantydate'];

	$query = "INSERT INTO assets VALUES (null, '$assetsn', '$assetname', '$departmentlocationid', '$employeeid', '$assetgroupid','$description', '$warrantydate')";

	if (mysqli_query($connect, $query)) {
		# success
		echo "1";
	} else {
		# fail
		echo "0";
	}
?>