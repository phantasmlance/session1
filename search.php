<?php 
	$hostname = "localhost";
	$username = "root";
	$password = "";
	$database = "session1";

	$connect = mysqli_connect($hostname, $username, $password, $database);
	mysqli_query($connect, "SET NAMES 'utf8'");

	$assetname = $_GET['assetname'];

	if (strlen($assetname) > 0) {
		$query = "SELECT DISTINCT (SELECT assetphotos.assetphoto FROM assetphotos LIMIT 1) AS assetphoto, a.assetsn, a.assetname, d.name FROM assets a INNER JOIN departmentlocations dl ON dl.id = a.departmentlocationid INNER JOIN departments d ON d.id = dl.departmentid WHERE a.assetname = '$assetname'";

		$data = mysqli_query($connect, $query);

		class Search {
			function Search($assetphoto, $assetname, $name, $assetsn) {		
				$this-> assetphoto = $assetphoto;
				$this-> assetname = $assetname;
				$this-> departmentname = $name;
				$this-> assetsn = $assetsn;
			}
		}

		$arrSearch = array();

		while ($row = mysqli_fetch_assoc($data)) {
			array_push($arrSearch, new Search($row['assetphoto'], $row['assetname'], $row['name'], $row['assetsn']));
		}

		if ($data) {
			echo json_encode($arrSearch);
		} else {
			echo "No assets";
		}
	} else { 
		// if search bar has been clean, server will return all assets
		$query = "SELECT DISTINCT (SELECT assetphotos.assetphoto FROM assetphotos LIMIT 1) AS assetphoto, a.assetsn, a.assetname, d.name FROM assets a INNER JOIN departmentlocations dl ON dl.id = a.departmentlocationid INNER JOIN departments d ON d.id = dl.departmentid";

		$data = mysqli_query($connect, $query);

		class Search {
			function Search($assetphoto, $assetname, $name, $assetsn) {		
				$this-> assetphoto = $assetphoto;
				$this-> assetname = $assetname;
				$this-> departmentname = $name;
				$this-> assetsn = $assetsn;
			}
		}

		$arrSearch = array();

		while ($row = mysqli_fetch_assoc($data)) {
			array_push($arrSearch, new Search($row['assetphoto'], $row['assetname'], $row['name'], $row['assetsn']));
		}

		echo json_encode($arrSearch);
	}
?>