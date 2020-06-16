<?php
require_once "connection.php";

$email = addslashes(htmlentities($_POST['email'], ENT_COMPAT, "UTF-8"));
$password = addslashes(htmlentities($_POST['pass'], ENT_COMPAT, "UTF-8"));

if(filter_var($email, FILTER_VALIDATE_EMAIL)==False){
  header("Location:probl4.html");
  return;
}
$stmt = $pdo->prepare("SELECT pass FROM Useri WHERE email=:email");
$stmt->bindParam(':email', $email);
$stmt->execute();
$result = $stmt->fetchAll();

if(count($result) == 0){
 session_start();
 $_SESSION['email'] = $email;
 $_SESSION['password']=$password;

 $message = "<div>Accesati link-ul pentru confirmarea inregistrarii!</div><br><a href='https://www.scs.ubbcluj.ro/~emir2508/php/checkEmail.php'>Confirma!</a>";
 $headers  = 'MIME-Version: 1.0' . "\r\n";
 $headers .= 'Content-type: text/html; charset=iso-8859-1' . "\r\n";

 mail($email,"Confirmare inregistrare",$message,$headers);
 echo "S-a trimis un mail pentru confirmarea inregistrarii!(verificati si sectiunea de spam!)";
 return;
}
else if (count($result) != 1) {
 header("Location: probl4.html");
 return;
}
if($result[0]["pass"]==md5($password)){
   echo "Autentificare cu succes!";
   return;
}else{
  header("Location:probl4.html");
  return;
}
?>
