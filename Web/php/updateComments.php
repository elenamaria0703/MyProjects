<?php
require_once "connection.php";
session_start();
if (! isset($_SESSION['user']) || ($_SESSION['user'] == "")){
 header("Location: probl6.php");
 return;
}
$sql = "UPDATE Comments SET activ=1 WHERE activ=0";
$stmt = $pdo->prepare($sql);
$stmt->execute();
echo "Comentariile au fost aprobate cu succes!";
header("Refresh:2; url=probl6.php");
session_destroy();
?>