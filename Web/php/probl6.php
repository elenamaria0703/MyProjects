<!DOCTYPE html>
<html>
<head>Roger Federer</head>
<body>

<div>
2016 marks a year plagued with injuries for Roger. Noticeably absent from the ATP was the Swiss Maestro. Playing in just 7 ATP events and 2 majors, Roger found himself against some tough opponents in the likes of Dominic Thiem, Milos Raonic, Novak Djokovic, Jo Wilfried Tsonga and young up-and-comer Alexander Zverev. Competing for just half the year and missing not just one, but two Grand Slams for the first time in his career, Roger calls it the end of the season after Wimbledon and ends up spending the rest of the year recovering and working on coming back stronger for 2017.
Finding himself at his lowest ranking since 2001, Roger began 2016 in Brisbane where he makes it to the finals and loses to Milos Raonic.

Off to Australia, Roger gets deep into the draw. He ends up losing in the semifinals to Novak Djokovic, but more damaging, he later admits to a mishap -- slipping during bath time with his twin daughters a day after the semifinals in Melbourne, which leads to his first career surgery on his left knee.

Federer undergos arthroscopic surgery to repair a torn meniscus, and clarified the cause of the problem, "It happened the day after the Djokovic match," said the 17-time grand slam champion.

Planning to return to the court in Miami, a stomach bug ends up keeping him out of tournament play for a while longer.

Roger rejoins the competition in Monte Carlo at the ATP World Tour Masters 1000 where he makes it to the quarterfinals losing to the talented Frenchman, Jo-Wilfried Tsonga. Next up is Rome where Roger runs into the shot maker Dominic Thiem losing in the round of 16s.

Making a tough decision, Roger withdraws from the French Open, snapping a 65-tournament run in majors reaching back to his Grand Slam debut at Roland Garros 17 years prior. The back injury that plagued him during the clay-court season, combined with the lingering effects of surgery for the freakish knee injury injury in January, as well as a virus that struck him down in Miami, took their toll on him.

Roger makes an appearance in Stuttgart and loses to Dominic Thiem in a heartbreaking semifinal where he had match points. “It was a tough match for both of us,” Federer said. “I could have played better at some of the bigger moments. At the same time, Thiem came up with some really good shots when he needed them. It’s just unfortunate. It’s just a matter of working hard and maybe things will go my way next time.”

In Halle, Germany, Roger finds himself up against one of the talented youngsters on the ATP Tour, Alexander Zverev, a 19-year old German player. Still not up to 100%, Roger is positive about what might come at Wimbledon.

Roger has a great start at the All England Club. He works his way into the semifinals in a winnable match against Canadian, Milos Raonic. With a slip in the fifth set and missed opportunities, Roger was so close to the finals, but looses in 5 sets.

In late July, Roger announces to the world he would miss the rest of the season, including the Olympics. “Considering all options after consulting with my doctors and my team, I have made the very difficult decision to call an end to my 2016 season as I need more extensive rehabilitation following my knee surgery earlier this year,” wrote Federer. “The doctors advised that if I want to play on the ATP World Tour injury free for another few years, as I intend to do, I must give both my knee and body the proper time to fully recover.”

The Swiss star finishes 2016 with a 21-7 record, which includes finishing as runner-up in Brisbane and posting semi-final showings at the Australian Open and Wimbledon. This marks the first year since 2001 that Federer has not won an ATP World Tour title.

Despite the rough year, Roger ends 2016 on a positive note and comes away with awards including GQ's Most Stylish Man, Most Marketable Sports Person, Forbes Fab 40 Top Global Athlete Brand, was named ATP Fan Favorite yet again and receives the Stefan Edberg Sportsmanship Award.
</div>
<br>
Comments:
<br>
<?php
require_once "connection.php";
$stmt = $pdo->prepare("SELECT content,name FROM Comments WHERE activ=1");
$stmt->execute();
$result = $stmt->fetchAll();
foreach($result as $row){
   $name=$row['name'];
   $content=$row['content'];
   echo "<div style='background-color:grey;width:500px;color:white;'>$name: $content</div>";
}
?> 

<br><br>
<form method="post" action="insertComment.php">
Insert comment here:<textarea name="comment" rows="4" cols="50">
</textarea><br>
Name:<input type="text"  name="name"><br><br>
<input id="save" type="submit" value="Comment"><br><br>
<button type="submit" formaction="admin.html">Admin Authentication</button>
</form>

</body>
</html>