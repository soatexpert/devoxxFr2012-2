<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${not empty userResponse}">
<table class="center" style="width: 400px;">
    <tr>
    	<td style="width: 120px; padding-right: 10px;">
    		<div>
            <img style="width: 120px; height: 120px;" src="http://www.gravatar.com/avatar/${player.mailHash}?s=120" alt="gravatar" />
            </div>
    	</td>
        <td>            
            <div>Username : ${userResponse.username}</div>
            <div>Name : ${userResponse.userForname}</div>
            <div>Mail : ${userResponse.userEmail}</div>
            <div>Role : ${userRolesComma}</div>
        </td>
    </tr>
</table>
</c:if>