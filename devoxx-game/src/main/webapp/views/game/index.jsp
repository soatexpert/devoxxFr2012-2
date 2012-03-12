<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<p><spring:message code="game.index.welcome" text="Bienvenue" /> ${userName}!</p>

<p><spring:message code="game.index.ranking.prefix" text="Vous êtes classé" /> ${rank} <spring:message code="game.index.ranking.separator" text="sur" /> ${nbUsers}.</p>
<p><spring:message code="game.index.waitingQuestions.prefix" text="Vous avez" /> ${waitingQuestions} <spring:message code="game.index.waitingQuestions.suffix" text="question(s) en attente." /> </p>

<c:if test="${waitingQuestions > 0}">
    <p><a id="play-btn" href="<c:url value='/game/play' />" data-role="button"><spring:message code="game.index.playNow" text="Jouez Maintenant!" /></a></p>
</c:if>