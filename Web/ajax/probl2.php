<?php
$servername = "localhost";
$username = "emir2508";
$password = "emir2508";
$dbname = "emir2508";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);
// Check connection
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT count(*) as total from Users";
$result = $conn->query($sql);
$crtPage=($_GET["page"]);
$rows=$result -> fetch_array(MYSQLI_NUM);
$nrRows=$rows[0];
$nrPages = ceil($nrRows / 3);
$poz=($crtPage-1)*3;
$sql = "SELECT * FROM Users LIMIT $poz, 3";
$users=$conn->query($sql);
while($user=$users->fetch_array(MYSQLI_NUM)){
 echo $user[0]. " " .$user[1]." ".$user[2]." ".$user[3]."<br>";
}
$conn->close();
?>