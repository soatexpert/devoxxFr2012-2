<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:if test="${not empty userResponse}">
<table class="center" style="width: 600px; table-layout: fixed;">
    <tr>
    	<td style="width: 120px; padding-right: 10px;">
    		<div>
            <img style="width: 120px; height: 120px;" src="http://www.gravatar.com/avatar/${userResponse.mailHash}?s=120" alt="gravatar" />
            </div>
    	</td>
        <td>
            <table>
                <tr>
                    <td><spring:message code="admin.user.name" text="Username" /></td><td>:</td>
                    <td><em>${userResponse.username}</em></td>
                </tr>
                <tr>
                    <td><spring:message code="admin.user.fullname" text="Name" /></td><td>:</td>
                    <td>${userResponse.userForname}</td>
                </tr>
                <tr>
                    <td><spring:message code="admin.user.mail" text="Email" /></td><td>:</td>
                    <td>${userResponse.userEmail}</td>
                </tr>
                <tr>
                    <td><spring:message code="admin.user.score" text="Score"/></td>
                    <td>:</td>
                    <td>${userResponse.score}</td>
                </tr>
                <tr>
                    <td><spring:message code="admin.user.time" text="Total time (ms)"/></td>
                    <td>:</td>
                    <td>${userResponse.totalTime}</td>
                </tr>
                <tr>
                    <td><spring:message code="admin.label.rules.accepted" text="Rules accepted" /></td><td>:</td>
                    <td><spring:message code="admin.label.${userResponse.rulesApproved ? 'yes' : 'no'}" text="Yes/No ?" /></td>
                </tr>
                <tr>
                    <td><spring:message code="admin.label.enabled" text="Enabled" /></td><td>:</td>
                    <td><spring:message code="admin.label.${userResponse.enabled ? 'yes' : 'no'}" text="Yes/No ?" /></td>
                </tr>
                <tr>
                    <td><spring:message code="admin.label.roles" text="Roles" /></td><td>:</td>
                    <td>${userRolesComma}</td>
                </tr>
            </table>           
        </td>
    </tr>
    <tr>
        <td>
        <a href="<c:url value='/admin/user' />" class="ui-btn"><spring:message code="global.btn.back" text="Back" /></a>
        </td>
    </tr>
</table>
</c:if>