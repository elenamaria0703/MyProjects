<?php
$image = $_GET["image"];
require_once "connection.php";
session_start();
if(!isset($_SESSION["user"])){
  header("Location:probl5.html");
  return;
}
$user=$_SESSION['user'];
$stmt=$pdo->prepare("SELECT name FROM images WHERE user=:user");
$stmt->bindParam(':user',$user);
$stmt->execute();
$result=$stmt->fetchAll();
$exist=0;
foreach($result as $row){
 $img=$row['name'];
 if($img==$image){
    $exist=1;
 }
}
if($exist==1){
 $stmt = $pdo->prepare("DELETE FROM images WHERE user=:user and name=:name");
 $stmt->bindParam(':user', $user);
 $stmt->bindParam(':name', $image);
 $stmt->execute();
 unlink("uploads/" . $image);
 header("Location: photos.php");
 return;
}
else{
header("Location: photos.php");
 return;
}
?>
