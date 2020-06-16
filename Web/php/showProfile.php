<?php
require_once "connection.php";
$user=$_POST['user'];
session_start();
if(!isset($_SESSION["user"])){
  header("Location:probl5.html");
  return;
}
$stmt = $pdo->prepare("SELECT name FROM images WHERE user=:user");
$stmt->bindParam(':user', $user);
$stmt->execute();
$result = $stmt->fetchAll();
foreach($result as $img){
   $imgname=$img['name'];
   echo "<img src='uploads/$imgname'><br>";
}
echo "<a href='otherprofiles.php'>Back</a>";
?>