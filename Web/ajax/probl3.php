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
$id=($_POST["id"]);
$nume=($_POST["nume"]);
$prenume=($_POST["prenume"]);
$email=($_POST["email"]);
$sql = "UPDATE Forms SET nume='$nume',prenume='$prenume',email='$email' WHERE id='$id'";
$result = $conn->query($sql);
echo 'Datele primite sunt: '.$id.' '.$nume.' '.$prenume.' '.$email;
$conn->close();
?>		

