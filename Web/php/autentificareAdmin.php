<?php
require_once "connection.php";

$email = addslashes(htmlentities($_POST['email'], ENT_COMPAT, "UTF-8"));
$password = addslashes(htmlentities($_POST['pass'], ENT_COMPAT, "UTF-8"));

if(filter_var($email, FILTER_VALIDATE_EMAIL)==False){
  header("Location:admin.html");
  return;
}
if($email!="emir2508@scs.ubbcluj.ro"){
  header("Location:admin.html");
  return;
}    
$stmt = $pdo->prepare("SELECT pass FROM Useri WHERE email=:email");
$stmt->bindParam(':email', $email);
$stmt->execute();
$result = $stmt->fetchAll();

if (count($result) != 1) {
 header("Location: admin.html");
 return;
}
if($result[0]["pass"]==md5($password)){
   session_start();
   $_SESSION['user']=$email;
   header("Location:updateComments.php");
   return;
}else{
  header("Location:admin.html");
  return;
}
?>

