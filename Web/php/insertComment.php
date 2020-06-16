<?php
require_once "connection.php";

$content=$_POST['comment'];
$name=$_POST['name'];
$sql = "INSERT INTO Comments (activ, content,name) VALUES (0,:content,:name)";
$stmt = $pdo->prepare($sql);
$stmt->bindParam(':content', $content);
$stmt->bindParam(':name', $name);
$stmt->execute();
header("Location:probl6.php");
?>