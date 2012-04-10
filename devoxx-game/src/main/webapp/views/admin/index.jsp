<%@ page import="java.util.Date" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<script type="text/javascript">
$(document).ready(function() {
	$("#user_search").autocomplete({
        source: "<c:url value='/admin/user/search' />",
        minLength: 2,
        select: function( event, ui ) {
        	document.location.href = "<c:url value='/admin/user/' />" + ui.item.userId + "/update";
        }
    }).data("autocomplete")._renderItem = function( ul, item ) {
		return $( "<li></li>" )
		.data( "item.autocomplete", item )
		.append( "<a><div style='padding: 2px;'><img src='http://www.gravatar.com/avatar/"+ item.mailHash +"?d=mm&s=48' alt='thumb' class='thumb' /><div class='userInfo'>" + item.userForname + "<br/><em>" + item.userEmail + "</em></div></div></a>" )
		.appendTo( ul );
    };
});
</script>
<fieldset class="dlmenu center">
<legend>Menu</legend>
<dl>
    <dd>
        Time : <%= new Date() %>
    </dd>
    <dd>
        <spring:message code="admin.label.search" text="Search" /> <input type="text" placeholder="Username or Email" id="user_search" />
    </dd>
    <dt></dt>
	<dd>
		<a href="<c:url value='/admin/user' />" class="ui-btn"><spring:message code="admin.btn.showalluser" text="Show All Users" /></a>
	</dd>
	<dt></dt>
	<dd>
        <a href="<c:url value='/admin/question/import' />" class="ui-btn"><spring:message code="admin.btn.importquestions" text="Import Questions" /></a>
    </dd>
    <dt></dt>
	<dd>
	   <a href="<c:url value='/' />" class="ui-btn"><spring:message code="global.btn.back" text="Back" /></a>
	</dd>
</dl>
</fieldset>