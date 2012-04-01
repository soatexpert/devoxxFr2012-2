<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<fieldset class="dlmenu center">
<legend>Menu</legend>
<dl>
	<dd>
		<a href="<c:url value='/admin/user' />" class="ui-btn"><spring:message code="admin.btn.showalluser" text="Back" /></a>
	</dd>
	<dt></dt>
	<dd>
	   <a href="<c:url value='/' />" class="ui-btn"><spring:message code="global.btn.back" text="Back" /></a>
	</dd>
</dl>
</fieldset>