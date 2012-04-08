<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>So@t Quizz - classement</title>
		<link type="text/css" href="<c:url value='/css/custom-theme/jquery-ui-1.8.18.custom.css'/>" rel="stylesheet" />
		<link type="text/css" href="<c:url value='/css/ranking.css'/>" rel="stylesheet" />
		<script type="text/javascript" src="<c:url value='/js/jquery-1.7.1.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/js/jquery-ui-1.8.18.custom.min.js'/>"></script>
    </head>
    <body>
        <h1><img src="<c:url value='/img/soat.png'/>"/><span>Quizz - Classement</span></h1>
        <div id="ranking">
            <table style="width: 100%;">
			<thead>
				<tr>
					<th>Avatar</th>
					<th style="width: 40%;">Name</th>
					<th>score</th>
					<th>Time</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${players}" var="player" varStatus="status">
				<tr id="player${status.count}" class="${status.count % 2 == 0 ? 'even' : 'odd'}">
					<td id="picture"><c:if test="${not empty player.name}"><img src="${player.avatarUrl}" /></c:if></td>
					<td id="name">${player.name}</td>
					<td id="score"><c:if test="${not empty player.name}">${player.score}pts</c:if></td>
					<td id="time"><c:if test="${not empty  player.name}">${player.totalTime}s</c:if></td>
				</tr>
			</c:forEach>
			</tbody>
	        </table>
        </div>
<script type="text/javascript">
    $(document).ready(function() {
        setInterval("updateRanking()",10000);
    });

    function updateRanking() {
        $.getJSON("ranking/update", { }, function(players) {

            var clone = $('#ranking').clone();

            for(var cmp = 0; cmp < players.length; cmp++) {
                if(!jQuery.isEmptyObject(players[cmp].name)) {
                    clone.find("#player" + (cmp+1) + " > #name").text(players[cmp].name);
                    var avatarParent = clone.find("#player" + (cmp+1) + " > #picture");
                    var avatarImg = avatarParent.children("img");

                    if(avatarImg.length != 0) {
                        avatarImg.attr("src", players[cmp].avatarUrl);
                    } else {
                        avatarParent.append("<img src='" + players[cmp].avatarUrl + "'/>");
                    }
                    clone.find("#player" + (cmp+1) + " > #score").text(players[cmp].score +"pts");
                    clone.find("#player" + (cmp+1) + " > #time").text(players[cmp].totalTime +"s");
                }
            }

            $('#ranking').replaceWith(clone);
        }).error(function(jqXHR, textStatus, errorThrown) {
    });
    }
</script>

    </body>
</html>