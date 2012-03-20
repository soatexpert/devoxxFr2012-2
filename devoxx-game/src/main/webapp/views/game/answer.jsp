<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<div id="advancement">Question ${nbOfQuestionsAnswered+1}/ ${nbOfQuestionsTotal}</div>

<div id="answer">
    <c:choose>
        <c:when test="${isAnswerCorrect}">
            <img src="<c:url value='/img/correct.png' />"/>
            <div class="imgText correct">Bravo!</div>
        </c:when>
        <c:otherwise>
            <img src="<c:url value='/img/wrong.png' />"/>
            <div class="imgText wrong">Pas de chance!</div>
            <div class="rightAnswer">La bonne r&eacute;ponse &eacute;tait : <span>${rightAnswer.choiceLabel}</span>.
        </c:otherwise>
    </c:choose>
    <div class="delay">Vous avez mis <span>${answerDelayInSeconds}</span>s pour r&eacute;pondre.</div>
</div>

<div class="center-wrapper">
        <a href="<c:url value='/game/play' />" data-role="button" data-inline="true">
<c:choose>
    <c:when test="${(nbOfQuestionLeft-1) == 0}">
        Terminer
    </c:when>
    <c:otherwise>
        Continuer
    </c:otherwise>
</c:choose>
</a>
<c:if test="${(nbOfQuestionLeft-1) != 0}">
<br/>
<a href="<c:url value='/game' />" data-role="button" data-inline="true">Faire une pause</a>
</c:if>
</div>
