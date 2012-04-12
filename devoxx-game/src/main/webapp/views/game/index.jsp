<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="welcome">
<spring:message code="game.index.welcome" text="Bienvenue" />
<c:if test="${not empty username}">${username} !</c:if>
</div>

<c:if test="${!approved}">
  <div class="errorBox">
    <div>
        <p>
            <span style="float: left; margin-left: 5px;" class="ui-icon ui-icon-alert"></span>
            <spring:message code="game.index.notApprouved" text="N'oubliez pas de valider votre participation sur le stand So@t." />
        </p>
    </div>
  </div>
</c:if>

<div id="infos">
    <c:if test="${approved}">
        <p><spring:message code="game.index.ranking.label" arguments="${rank}§${nbUsers}" argumentSeparator="§" text="Vous êtes classé {0} sur {1}" />.</p>
    </c:if>
    <p><spring:message code="game.index.waitingQuestions.label" arguments="${waitingQuestions}" argumentSeparator="§" text="Vous avez {0} question(s) en attente." /></p>
</div>

<c:if test="${waitingQuestions > 0}">
    <div class="center-wrapper">
        <a id="play-btn" href="<c:url value='/play' />" data-ajax="false" data-role="button" data-inline="true" data-iconpos="top" data-icon="play">
            <spring:message code="game.index.playNow" text="Jouez Maintenant!" />
        </a>
    </div>
</c:if>
<div class="center-wrapper">
    <a id="logout-btn" href="<c:url value='/auth/logout' />" data-ajax="false" data-role="button" data-inline="true" data-iconpos="top" data-icon="play">
        <spring:message code="global.btn.logout" text="Logout" />
    </a>
</div>
