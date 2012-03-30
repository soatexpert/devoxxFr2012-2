<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<title>So@t Quizz - classement</title>
		<link type="text/css" href="css/custom-theme/jquery-ui-1.8.17.custom.css" rel="stylesheet" />
		<link type="text/css" href="css/ranking.css" rel="stylesheet" />
		<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
		<script type="text/javascript" src="js/jquery-ui-1.8.17.custom.min.js"></script>
    </head>
    <body>
        <h1>So@t Quizz - Classement</h1>
        <div id="ranking">
            <table width="100%">
		<thead>
			<tr>
				<th>Avatar</th>
				<th width="100%">Name</th>
				<th>score</th>
				<th>Time</th>
			</tr>
		</thead>
		<c:forEach items="${players}" var="player" varStatus="status">
		        <c:choose>
                    <c:when test="${status.count%2 == 0}">
                        <tr id="player${status.count}" class="even">
                    </c:when>
                    <c:otherwise>
                        <tr id="player${status.count}" class="odd">
                    </c:otherwise>
                </c:choose>
                    <td id="picture"><img src="http://www.gravatar.com/avatar/${player.mailHash}?d=mm&s=64" /></td>
                    <td id="name">${player.name}</td>
                    <td id="score">${player.score}pts</td>
                    <td id="time">${player.time}s</td>
                </tr>
        </c:forEach>
 </div>
<script>
    $(document).ready(function() {
        setInterval("updateRanking()",10000);
    });

    function updateRanking() {
        $.getJSON("updateRanking", { }, function(players) {

            var clone = $('#ranking').clone();

            for(var cmp = 0; cmp < players.length; cmp++) {
                clone.find("#player" + (cmp+1) + " > #name").text(players[cmp].name);
                clone.find("#player" + (cmp+1) + " > #picture > img").attr("src","http://www.gravatar.com/avatar/" + players[cmp].mailHash + "?d=mm&s=64");
                clone.find("#player" + (cmp+1) + " > #score").text(players[cmp].score +"pts");
                clone.find("#player" + (cmp+1) + " > #time").text(players[cmp].time +"s");
            }

            $('#ranking').replaceWith(clone);
        }).error(function(jqXHR, textStatus, errorThrown) {
        	alert("error " + textStatus);
        alert("incoming Text " + jqXHR.responseText);
    });
    }
 </script>

    </body>
</html>