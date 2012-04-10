<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<fieldset style="width: 400px;" class="center">
	<legend>Import Questions From File</legend>
	<form:form method="post" modelAttribute="importQuestions" enctype="multipart/form-data">
	    <c:if test="${not empty param.success}">
	      <div class="ui-widget">
	        <div style="padding: 0.4em 0.7em;" class="ui-state-highlight ui-corner-all">
	            <p>
	                <span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-circle-check"></span>
	                <spring:message code="admin.question.import.success" text="Questions are successfully imported !" />
	            </p>
	        </div>
	      </div>
	    </c:if>
	    <form:errors path="*" element="div" cssClass="ui-state-error ui-corner-all" cssStyle="padding: 0.7em;" htmlEscape="false" />
	    <table>
	    <tr>
	       <td><form:input path="fileData" type="file" /></td>
	       <td><input type="submit" value='<spring:message code="admin.btn.upload" text="Upload" />' /></td>
           <td><a href="<c:url value='/admin/' />" class="ui-btn"><spring:message code="global.btn.back" text="Back" /></a></td>
        </tr>
        </table>
	</form:form>
</fieldset>