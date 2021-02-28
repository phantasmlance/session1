<?php
	require "connect.php";

	$query = "SELECT DISTINCT (SELECT assetphotos.assetphoto FROM assetphotos LIMIT 1) AS assetphoto, a.assetsn, a.assetname, d.name FROM assets a INNER JOIN departmentlocations dl ON dl.id = a.departmentlocationid INNER JOIN departments d ON d.id = dl.departmentid";

	$data = mysqli_query($connect, $query);

	class AssetCatalogues {
		function AssetCatalogues($assetphoto, $assetname, $name, $assetsn) {		
			$this-> assetphoto = $assetphoto;
			$this-> assetname = $assetname;
			$this-> departmentname = $name;
			$this-> assetsn = $assetsn;
		}
	}

	$arrAssetCatalogues = array();

	while ($row = mysqli_fetch_assoc($data)) {
		array_push($arrAssetCatalogues, new AssetCatalogues($row['assetphoto'], $row['assetname'], $row['name'], $row['assetsn']));
	}

	echo json_encode($arrAssetCatalogues);
?>