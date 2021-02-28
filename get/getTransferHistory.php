<?php
	require "connect.php";

	$query = "SELECT transferdate, fromassetsn, toassetsn, fromdepartmentlocationid, todepartmentlocationid FROM assettransferlogs";

	$data = mysqli_query($connect, $query);

	class AssetTransferLogs {
		function AssetTransferLogs($transferdate, $fromassetsn, $toassetsn, $fromdepartmentlocationid, $todepartmentlocationid) {		
			$this-> transfer_date = $transferdate;
			$this-> old_assetsn = $fromassetsn;
			$this-> new_assetsn = $toassetsn;
			$this-> old_dl = $fromdepartmentlocationid;
			$this-> new_dl = $todepartmentlocationid;
		}
	}

	$arrAssetTransferLogs = array();

	while ($row = mysqli_fetch_assoc($data)) {
		array_push($arrAssetTransferLogs, new AssetTransferLogs($row['transferdate'], $row['fromassetsn'], $row['toassetsn'], $row['fromdepartmentlocationid'], $row['todepartmentlocationid']));
	}

	echo json_encode($arrAssetTransferLogs);
?>