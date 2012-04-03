<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:if test="${not empty userResponse}">
<table class="center" style="width: 400px;">
    <tr>
    	<td style="width: 120px; padding-right: 10px;">
    		<div>
            <img style="width: 120px; height: 120px;" src="http://www.gravatar.com/avatar/${userResponse.mailHash}?s=120" alt="gravatar" />
            </div>
    	</td>
        <td>            
            <div>Username : ${userResponse.username}</div>
            <div>Name : ${userResponse.userForname}</div>
            <div>Mail : ${userResponse.userEmail}</div>
            <div>Rules accepted : ${userResponse.reglementAccepted ? 'Yes' : 'No'}</div>
            <div>Enabled : ${userResponse.enabled ? 'Yes' : 'No'}</div>
            <div>Role : ${userRolesComma}</div>
        </td>
    </tr>
    <tr>
        <td>
        <a href="<c:url value='/admin/user' />" class="ui-btn"><spring:message code="global.btn.back" text="Back" /></a>
        </td>
    </tr>
</table>
</c:if>