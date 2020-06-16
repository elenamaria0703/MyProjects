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
$rows=$result -> fetch_array(MYSQLI_NUM);
$nrRows=$rows[0];
$nrPages = ceil($nrRows / 3);
echo $nrPages;
$conn->close();
?>