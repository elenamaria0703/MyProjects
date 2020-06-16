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
$id=($_GET["id"]);
$sql = "SELECT nume,prenume,email FROM Forms WHERE id='$id'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // output data of each row
  while($row = $result -> fetch_array(MYSQLI_NUM)){
     echo  $row[0].' '.$row[1].' '.$row[2];
}

} else {
  echo "0 results";
}
$conn->close();
?>		