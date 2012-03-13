<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="f"%>

<div id="advancement">Question ${nbOfQuestionsAnswered+1}/ ${nbOfQuestionsTotal}</div>

<div id="elapsed-time"><span id="timeToUpdate">0</span>s</div>

<script type="text/javascript">
var start = ${questionStartDate};

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
	    <c:forEach items="${question.choices}" var="answer" varStatus="status">
                <f:radiobutton path="answer" id="radio-choice-${status.count}" value="${answer.questionChoiceId}"/>
            <label for="radio-choice-${status.count}">${answer.choiceLabel}</label>
        </c:forEach>
</fieldset>

<div class="center-wrapper">
<c:choose>
    <c:when test="${(nbOfQuestionLeft-1) == 0}"><input type="submit" value="Terminer" data-inline="true"/></c:when>
    <c:otherwise>
        <a href="<c:url value='/game/pause'/>" data-role="button" data-inline="true" id="pauseBtn">Faire une pause</a>
        <input type="submit" value="Continuer" data-inline="true"/>
    </c:otherwise>
</c:choose>
</div>

</f:form>

<script type="text/javascript">
$(document).delegate('#pauseBtn', 'click', function() {
  $(this).simpledialog({
    'mode' : 'bool',
    'prompt' : 'La réponse en cours ne sera pas conservée et votre temps de réponse augmente quand même lorsque vous prennez une pause!',
    'useModal': true,
    'buttons' : {
      'Ok': {
        click: function () {
          $('#question').attr('action', 'pause');
          $('#question').submit();
        }
      },
      'Retour': {
        click: function () {
        },
        icon: "delete",
        theme: "c"
      }
    }
  })
})
</script>