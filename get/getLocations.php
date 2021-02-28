<?php
	require "connect.php";

	$query = "SELECT id, name FROM locations";

	$data = mysqli_query($connect, $query);

	class Locations {
		function Locations($id, $name) {		
			$this-> id = $id;
			$this-> name = $name;
		}
	}

	$arrLocations = array();

	while ($row = mysqli_fetch_assoc($data)) {
		array_push($arrLocations, new Locations($row['id'], $row['name']));
	}

	echo json_encode($arrLocations);
?>