<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<form:form method="post" modelAttribute="userForm">
	<form:errors path="*" element="div" cssClass="ui-state-error ui-corner-all" cssStyle="padding: 0 .7em;" />
	<table class="center" style="width: 600px; table-layout: fixed;">
	    <tr>
	        <td style="width: 120px; padding-right: 10px;">
	            <div>
	            <img style="width: 120px; height: 120px;" src="http://www.gravatar.com/avatar/${mailHash}?s=120" alt="gravatar" />
	            </div>
	        </td>
			<td>
				<table>
					<tr>
						<td><spring:message code="admin.user.name" text="Username" /></td><td>:</td>
						<td>${username}</td>
					</tr>
					<tr>
						<td><spring:message code="admin.user.fullname" text="Name" /><td>:</td>
						<td><form:input path="userForname" /></td>
					</tr>
					<tr>
						<td><spring:message code="admin.user.mail" text="Email" /><td>:</td>
						<td><form:input path="userEmail" /></td>
					</tr>
					<tr>
					    <spring:message code="admin.label.yes" text="Yes" var="labelEnabled" />
	                    <td><spring:message code="admin.label.enabled" text="Enabled" /><td>:</td>
	                    <td><form:checkbox id="userActive" path="userActive" cssErrorClass="error" label="${labelEnabled}" />
	                    <form:errors path="userActive" cssClass="ui-state-error ui-corner-all" cssStyle="padding: 0 .7em;" element="div" /></td>
	                </tr>
					<tr>
						<td><spring:message code="admin.label.roles" text="Roles" /><td>:</td>
						<td><div id="allUserRoles"><form:checkboxes items="${allUserRoles}" path="userRoles" cssErrorClass="error"/></div>
						<form:errors path="userRoles" cssClass="ui-state-error ui-corner-all" cssStyle="padding: 0 .7em;" element="div" /></td>
					</tr>
				</table>			
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