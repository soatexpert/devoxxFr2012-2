<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty userResponse}">
<form:form method="post" modelAttribute="userForm">

<table class="center" style="width: 400px;">
    <tr>
        <td style="width: 120px; padding-right: 10px;">
            <div>
            <img style="width: 120px; height: 120px;" src="http://www.gravatar.com/avatar/${mailHash}?s=120" alt="gravatar" />
            </div>
        </td>
        <td>
            <div>Username : ${userResponse.userName}</div>
            <div>Name : <form:input path="userForname" value="${userResponse.userForname}" /></div>    
            <div>Mail : <form:input path="userForname" value="${userResponse.userEmail}" /></div>
			<div>
				Admin :
				<form:radiobutton path="isAdmin" value="true" /><spring:message code="admin.label.yes" text="Yes" />
				<form:radiobutton path="isAdmin" value="false" /><spring:message code="admin.label.no" text="No" />
			</div>
		</td>
    </tr>
</table>
</form:form>
</c:if>