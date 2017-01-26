<?php 

include "notorm/NotORM.php";

$dsn = "mysql:dbname=api;host=127.0.0.1";
$pdo = new PDO($dsn, "root", "alphaomega*9");
$db = new NotORM($pdo);

header('Content-Type: application/json');

if ($_POST["name"] != null) {
	$data['name'] = $_POST["name"];
	$result = $db->posts->insert($data);

	if ($result) {
		$res['success'] = true;
		$res['message'] = 'Success post data.';
		
		echo json_encode($res);
	}
}
