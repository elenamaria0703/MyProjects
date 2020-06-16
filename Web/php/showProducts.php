<!DOCTYPE html>
<html>
<body>
<style>
  div {
    border:1px solid black;
    position: relative;
    height: 300px;
    width: 500px;
    padding: 0px;
    margin: 0px;
    list-style-type: none;
  }

</style>
<form method="post" action="showProducts.php" style="display: inline-block;">
Selectati N: <select id="n" name="n">
             <option>1</option>
             <option>2</option>
             <option>3</option>
             <option>4</option>
             </select><br><br>
<input id="pages" type="submit" value="Show Products">
<?php
$servername = "localhost";
$username = "emir2508";
$password = "emir2508";
$dbname = "emir2508";


$conn = new mysqli($servername, $username, $password, $dbname);
if ($conn->connect_error) {
  die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT count(*) as total from Products";
$result = $conn->query($sql);

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $n=($_POST["n"]);
    $crtPage=1;
}
else if(isset($_GET['crtPage'])){
   $n=($_GET['n']);
   $crtPage=($_GET['crtPage']);
   if(isset($_GET['prev'])){
        $crtPage=$crtPage-1;
   }
   if(isset($_GET['next'])){
        $crtPage=$crtPage+1;
   }
}
else{
echo "<div></div>";
return;
}

$rows=$result -> fetch_array(MYSQLI_NUM);
$nrRows=$rows[0];
$nrPages = ceil($nrRows / $n);
$poz=($crtPage-1)*$n;
$sql = "SELECT * FROM Products LIMIT $poz, $n";
$products=$conn->query($sql);
echo "<div>";
while($product=$products->fetch_array(MYSQLI_NUM)){
 echo $product[0]. " " .$product[1]." ".$product[2]." ".$product[3]."<br>";
}
echo "</div>";
if($crtPage==1){
  echo "<a style='margin:400px;' href='showProducts.php?crtPage=$crtPage&n=$n&next=true'>Next</a>";
  $conn->close();
  return;
}
if($crtPage==$nrPages){
  echo "<a href='showProducts.php?crtPage=$crtPage&n=$n&prev=true'>Previous</a>";
  $conn->close();
  return;
}
echo "<a href='showProducts.php?crtPage=$crtPage&n=$n&prev=true'>Previous</a>";
echo "<a style='margin:400px;' href='showProducts.php?crtPage=$crtPage&n=$n&next=true'>Next</a>";
$conn->close();
?>
</body>
</html>