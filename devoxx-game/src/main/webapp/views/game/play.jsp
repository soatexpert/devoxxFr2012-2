<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<div id="advancement">
    <spring:message code="game.play.steps" arguments="${nbOfQuestionsAnswered+1}§${nbOfQuestionsTotal}" argumentSeparator="§" text="Question {0} / {1}" />
</div>

<div id="elapsed-time"><span id="timeToUpdate">0</span>s</div>

<script type="text/javascript">
var start = Date.now();

setTimeout("refreshElapsedTime()",1000);

function refreshElapsedTime() {

    var now = Date.now();
    $("#timeToUpdate").html( Math.floor((now-start)/1000) );

    setTimeout("refreshElapsedTime()",1000);

}
</script>

<f:form method="post" action="next" modelAttribute="answerForm" id="question">
<f:hidden path="questionId"/>
<fieldset data-role="controlgroup">
	<legend>${question.questionLabel}</legend>
	    <c:forEach items="${question.shuffledChoices}" var="answer" varStatus="status">
                <f:radiobutton path="answer" id="radio-choice-${status.count}" value="${answer.questionChoiceId}"/>
            <label for="radio-choice-${status.count}">${answer.choiceLabel}</label>
        </c:forEach>
</fieldset>

<div class="center-wrapper" style="display:none" id="validateBtn">
        <input type="submit" value="<spring:message code="game.play.validate" text="Valider" />" data-inline="true"/>
</div>

</f:form>

<script type="text/javascript">
$('[id^="radio-choice-"]').on('click', function(e) {	
  $('#validateBtn').show();
});
</script>