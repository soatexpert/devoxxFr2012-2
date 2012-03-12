<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<div id="advancement">Question 1/ 3</div>
<div id="elapsed-time">Temps écoulé : 10 secondes</div>

<f:form method="post" action="next">
<input type="hidden" id="questionId" value="${question.idQuestion}"/>
<fieldset data-role="controlgroup">
	<legend>${question.questionLabel}</legend>
	    <c:forEach items="${question.choices}" var="answer" varStatus="status">
            <c:if test="${status.count == 1}">
                <input type="radio" name="answer" id="radio-choice-${status.count}" value="${answer.questionChoiceId}" checked="checked"/>
            </c:if>
            <c:if test="${status.count != 1}">
                <input type="radio" name="answer" id="radio-choice-${status.count}" value="${answer.questionChoiceId}" />
            </c:if>
            <label for="radio-choice-${status.count}">${answer.choiceLabel}</label>
        </c:forEach>
</fieldset>

<input type="submit" value="Question suivante"/>
</f:form>