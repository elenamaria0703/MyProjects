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

$lin=($_POST["i"]);
$c=($_POST["j"]);
$col="col".$c;
$l=intval($lin);

$sql=" UPDATE XO SET $col='X' WHERE lin='$l'";
$conn->query($sql);
$sql = "SELECT col0,col1,col2 FROM XO";
$result = $conn->query($sql);
$game_board=array();
$free=array();
$poz=0;
while($rows=$result -> fetch_array(MYSQLI_NUM)){
   $row=array();
  for($j=0;$j<3;$j++){
    if($rows[$j] != 'X' && $rows[$j] != 'O')
          array_push($free,array($poz,$j));
    array_push($row,$rows[$j]);
  }
  array_push($game_board,$row);
 $poz+=1;
}
if(checkWin($game_board)==1)
   echo 'Felicitari!';
else{
if(count($free)==0) 
  echo 'Remiza!';
else{
$r=rand(0,count($free)-1);
$col="col".$free[$r][1];
$l=intval($free[$r][0]);
$sql=" UPDATE XO SET $col='O' WHERE lin='$l'";
$conn->query($sql);
echo $free[$r][0].' '. $free[$r][1];
}
}

function checkWin($board) {
  if($board[0][0]==$board[0][1] && $board[0][1]==$board[0][2] && $board[0][2]=='X')
        return 1;
  if($board[1][0]==$board[1][1] && $board[1][1]==$board[1][2] && $board[1][2]=='X')
        return 1;
  if($board[2][0]==$board[2][1] && $board[2][1]==$board[2][2] && $board[2][2]=='X')
        return 1;
  if($board[0][0]==$board[1][0] && $board[1][0]==$board[2][0] && $board[2][0]=='X')
        return 1;
  if($board[0][1]==$board[1][1] && $board[1][1]==$board[2][1] && $board[2][1]=='X')
        return 1;
  if($board[0][2]==$board[1][2] && $board[1][2]==$board[2][2] && $board[2][2]=='X')
        return 1;
  if($board[0][0]==$board[1][1] && $board[1][1]==$board[2][2] && $board[2][2]=='X')
        return 1;
  if($board[0][2]==$board[1][1] && $board[1][1]==$board[2][0] && $board[2][0]=='X')
        return 1;
}
$conn->close();
?>	