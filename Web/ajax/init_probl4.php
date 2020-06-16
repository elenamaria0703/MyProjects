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

$sql=" UPDATE XO SET col0='0', col1='0',col2='0' WHERE lin=0";
$conn->query($sql);

$sql=" UPDATE XO SET col0='0', col1='0',col2='0' WHERE lin=1";
$conn->query($sql);

$sql=" UPDATE XO SET col0='0', col1='0',col2='0' WHERE lin=2";
$conn->query($sql);

echo 'Start Joc!';
$conn->close();
?>