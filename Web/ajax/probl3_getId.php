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

$sql = "SELECT id FROM Forms";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // output data of each row
  while($row = $result -> fetch_array(MYSQLI_NUM)){
     echo '<option>'. $row[0] .'</option>';
}

} else {
  echo "0 results";
}
$conn->close();
?>		