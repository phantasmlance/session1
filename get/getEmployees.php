<?php
	require "connect.php";

	$query = "SELECT id, firstname, lastname, phone FROM employees";

	$data = mysqli_query($connect, $query);

	class Emloyees {
		function Emloyees($id, $firstname, $lastname, $phone) {		
			$this-> id = $id;
			$this-> firstname = $firstname;
			$this-> lastname = $lastname;
			$this-> phone = $phone;
		}
	}

	$arrEmployees = array();

	while ($row = mysqli_fetch_assoc($data)) {
		array_push($arrEmployees, new Emloyees($row['id'], $row['firstname'], $row['lastname'], $row['phone']));
	}

	echo json_encode($arrEmployees);
?>