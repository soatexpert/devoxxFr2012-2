<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form:form method="post" modelAttribute="userForm">
	<form:errors path="*" element="div" cssClass="ui-state-error ui-corner-all" cssStyle="padding: 0 .7em;" />
	<table class="center" style="width: 400px;">
	    <tr>
	        <td style="width: 120px; padding-right: 10px;">
	            <div>
	            <img style="width: 120px; height: 120px;" src="http://www.gravatar.com/avatar/${mailHash}?s=120" alt="gravatar" />
	            </div>
	        </td>
			<td>
				<div>
					<spring:message code="admin.user.name" text="Username" /> : ${username}
				</div>
				<div>
					<spring:message code="admin.user.fullname" text="Name" /> :
					<form:input path="userForname" />
				</div>
				<div>
					<spring:message code="admin.user.mail" text="Email" /> :
					<form:input path="userEmail" />
				</div>
				<div>
                    <spring:message code="admin.label.enabled" text="Enabled" /> :
                    <form:checkbox path="userActive" cssErrorClass="error" />Yes
                    <form:errors path="userActive" cssClass="ui-state-error ui-corner-all" cssStyle="padding: 0 .7em;" element="div" />
                </div>
				<div>
					<spring:message code="admin.label.roles" text="Roles" /> :
					<form:input path="userRoles" cssErrorClass="error" />
					<form:errors path="userRoles" cssClass="ui-state-error ui-corner-all" cssStyle="padding: 0 .7em;" element="div" />
				</div>				
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
			 <input style="float: left;" type="submit" value='<spring:message code="admin.btn.modify" text="Modify" />' />
			 <a style="margin-left: 6px; float: left;" href="<c:url value='/admin/user' />" class="ui-btn"><spring:message code="global.btn.back" text="Back" /></a>
			</td>
		</tr>
	</table>
</form:form>