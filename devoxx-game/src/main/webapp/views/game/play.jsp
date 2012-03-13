<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<div id="advancement">Question ${nbOfQuestionsAnswered+1}/ ${nbOfQuestionsTotal}</div>
<div id="elapsed-time">10s</div>

<f:form method="post" action="next" modelAttribute="answerForm">
<f:hidden path="questionId"/>
<fieldset data-role="controlgroup">
	<legend>${question.questionLabel}</legend>
	    <c:forEach items="${question.choices}" var="answer" varStatus="status">
                <f:radiobutton path="answer" id="radio-choice-${status.count}" value="${answer.questionChoiceId}"/>
            <label for="radio-choice-${status.count}">${answer.choiceLabel}</label>
        </c:forEach>
</fieldset>

<c:choose>
    <c:when test="${(nbOfQuestionLeft-1) == 0}"><input type="submit" value="Terminer" data-inline="true"/></c:when>
    <c:otherwise><input type="submit" value="Question suivante" data-inline="true"/></c:otherwise>
</c:choose>

</f:form>