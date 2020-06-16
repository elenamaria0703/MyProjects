<?php
require_once "connection.php";
session_start();
$user=$_SESSION['user'];
$stmt = $pdo->prepare("SELECT name FROM images WHERE user=:user");
$stmt->bindParam(':user', $user);
$stmt->execute();
$result = $stmt->fetchAll();
foreach($result as $img){
   $imgname=$img['name'];
   echo "<img src='uploads/$imgname'>";
   echo "<a href='delete.php?image=$imgname'>Sterge aceasta fotografie</a><br><br>";
}
?>