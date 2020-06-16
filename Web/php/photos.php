<?php
require_once "connection.php";
session_start();
$user=$_SESSION['user'];
$uploaddir = '/home/scs/licenta/an2/gr223/emir2508/public_html/php/uploads/';
$uploadfile = $uploaddir . basename($_FILES['picture']['name']);
$filetype=$_FILES['picture']['type'];
$photo=$_FILES['picture']['name'];
if($filetype!="image/jpeg" and $filetype!="image/png"){
  header("Location:uploadPhoto.html");
  return;
}
$sql = "INSERT INTO images (name,user) VALUES (:name,:user)";
$stmt = $pdo->prepare($sql);
$stmt->bindParam(':name', $photo);
$stmt->bindParam(':user', $user);
$stmt->execute();

if (move_uploaded_file($_FILES['picture']['tmp_name'], $uploadfile)) {
  header("Location:uploadPhoto.html");
  return;
} else {
   echo "Upload failed";
}
?> 

