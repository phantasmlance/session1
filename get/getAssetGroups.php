<?php
	require "connect.php";

	$query = "SELECT id, name FROM assetgroups";

	$data = mysqli_query($connect, $query);

	// create a class and a constructor
	class AssetGroups {
		function AssetGroups($id, $name) {
			$this-> id = $id;
			$this-> name = $name;
		}
	}

	// create array 
	$arrAssetGroups = array();

	// add item to array
	while ($row = mysqli_fetch_assoc($data)) {
		array_push($arrAssetGroups, new AssetGroups($row['id'], $row['name']));
	}

	// convert array to json type
	echo json_encode($arrAssetGroups);
?>