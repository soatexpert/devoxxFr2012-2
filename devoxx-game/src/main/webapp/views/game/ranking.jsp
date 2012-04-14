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
        <div class="devoxx"><img src="<c:url value="/img/devoxx.png"/> "></div>
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
					<td id="name">${status.count}. ${player.name}</td>
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

            for(var cmp = 0; cmp < 10; cmp++) {
                var player = players[cmp];
                var rank = cmp+1;

                if(!jQuery.isEmptyObject(player.name)) {
                    clone.find("#player" + rank + " > #name").text(rank + ". " + player.name);
                    var avatarParent = clone.find("#player" + rank + " > #picture");
                    var avatarImg = avatarParent.children("img");

                    if(avatarImg.length != 0) {
                        avatarImg.attr("src", player.avatarUrl);
                    } else {
                        avatarParent.append("<img src='" + player.avatarUrl + "'/>");
                    }
                    clone.find("#player" + rank + " > #score").text(player.score +"pts");
                    clone.find("#player" + rank + " > #time").text(player.totalTime +"s");
                } else {
                    clone.find("#player" + rank + " > #name").text(rank + ". ");
                    clone.find("#player" + rank + " > #picture").text("");
                    clone.find("#player" + rank + " > #score").text("");
                    clone.find("#player" + rank + " > #time").text("");
                }
            }

            $('#ranking').replaceWith(clone);
        }).error(function(jqXHR, textStatus, errorThrown) {
    });
    }
</script>

    </body>
</html>