<?php
	require "connect.php";

	$query = "SELECT id, name FROM departments";

	$data = mysqli_query($connect, $query);

	class Departments {
		function Departments($id, $name) {		
			$this-> id = $id;
			$this-> name = $name;
		}
	}

	$arrDepartments = array();

	while ($row = mysqli_fetch_assoc($data)) {
		array_push($arrDepartments, new Departments($row['id'], $row['name']));
	}

	echo json_encode($arrDepartments);
?>