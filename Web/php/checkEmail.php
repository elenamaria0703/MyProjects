<?php
require_once "connection.php";
session_start();
if (! isset($_SESSION['email']) || ($_SESSION['email'] == "")){
 header("Location: probl4.html");
 return;
}
$email = $_SESSION['email'];
$pass = $_SESSION['password'];
$str=md5($pass);
$sql = "INSERT INTO Useri (email, pass) VALUES (:email,:pass)";
$stmt = $pdo->prepare($sql);
$stmt->bindParam(':email', $email);
$stmt->bindParam(':pass', $str);
$stmt->execute();
echo "V-ati inregistrat cu succes!Veti fi redirectionat in scurt timp!";
session_destroy();
header("Refresh:3; url=probl4.html");
return;
?>