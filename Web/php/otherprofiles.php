<!DOCTYPE html>
<html>
<body>
<form method="post" action="showProfile.php" ">
Introduceti un utilizator:<input type="text" name="user"><br><br>
<input id="pages" type="submit" value="View Profile">

<?php
require_once "connection.php";
session_start();
if(!isset($_SESSION["user"])){
  header("Location:probl5.html");
  return;
}
$stmt = $pdo->prepare("SELECT DISTINCT user  FROM images");
$stmt->execute();
$result = $stmt->fetchAll();
echo "The list of Users:";
echo "<ol>";
foreach($result as $row){
   $user=$row['user'];
   echo "<li>$user</li>";
}
echo"</ol>";
echo "<a href='uploadPhoto.html'>Back</a>";
?>
</body>
</html>