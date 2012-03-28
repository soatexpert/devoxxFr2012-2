<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<div id="advancement">
    <spring:message code="game.play.steps" arguments="${nbOfQuestionsAnswered+1}§${nbOfQuestionsTotal}" argumentSeparator="§" text="Question {0} / {1}" />
</div>

<div id="answer">
    <c:choose>
        <c:when test="${isAnswerCorrect}">
            <img src="<c:url value='/img/correct.png' />"/>
            <div class="imgText correct"><spring:message code="game.answer.ok" text="Bravo!"/></div>
        </c:when>
        <c:otherwise>
            <img src="<c:url value='/img/wrong.png' />"/>
            <div class="imgText wrong"><spring:message code="game.answer.ko" text="Pas de chance!"/></div>
            <div class="rightAnswer"><spring:message code="game.answer.rightAnswer" arguments="${rightAnswer.choiceLabel}" text="La bonne r&eacute;ponse &eacute;tait : <span>{0}</span>."/></div>
        </c:otherwise>
    </c:choose>
    <div class="delay"><spring:message code="game.answer.answerDelay" arguments="${answerDelayInSeconds}" text="Vous avez mis <span>{0}</span>s pour r&eacute;pondre."/></div>
</div>

<div class="center-wrapper">
<c:choose>
    <c:when test="${(nbOfQuestionLeft-1) == 0}">
        <a href="<c:url value='/' />" data-role="button" data-inline="true"><spring:message code="game.answer.done"/></a>
    </c:when>
    <c:otherwise>
        <a href="<c:url value='/play' />" data-role="button" data-inline="true"><spring:message code="game.answer.continue"/></a>
    </c:otherwise>
</c:choose>
<c:if test="${(nbOfQuestionLeft-1) != 0}">
<br/>
<a href="<c:url value='/' />" data-role="button" data-inline="true"><spring:message code="game.answer.break"/></a>
</c:if>
</div>
