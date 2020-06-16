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

/*$stmt=$conn->prepare("SELECT sosire FROM Trenuri WHERE plecare=?");
$oras=($_GET["oras"]);
$stmt->bind_param('s',$oras);

$result = $stmt->execute();
$stmt->close();
echo $result
if ($result->num_rows > 0) {
  // output data of each row
  while($row = $result -> fetch_array(MYSQLI_NUM)){
     echo "<li>".$row[0]."</li>";
}

} else {
  echo "0 results";
}*/
$oras=($_GET["oras"]);
$sql = "SELECT sosire FROM Trenuri WHERE plecare='$oras'";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
  // output data of each row
  while($row = $result -> fetch_array(MYSQLI_NUM)){
     echo "<li>".$row[0]."</li>";
}

} else {
  echo "0 results";
}
$conn->close();
?>	