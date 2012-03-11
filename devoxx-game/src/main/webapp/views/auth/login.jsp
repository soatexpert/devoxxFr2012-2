<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
    openid.img_path = '<c:url value="/img/openid/" />';
    $(document).ready(function() {        
        openid.init('openid_identifier');
    });
</script>
<c:url value="/j_spring_openid_security_check" var="openIdActionUrl" />
<!-- Simple OpenID Selector -->
<form action="${openIdActionUrl}" method="post" id="openid_form" class="center">
    <input type="hidden" name="action" value="verify" />
    <fieldset>
        <legend><spring:message code="login.form.fieldset.legend" text="Sign-in or Create New Account" /></legend>
		<c:if test="${not empty param.error}">
		  <div class="ui-widget">
			<div style="padding: 0 .7em;" class="ui-state-error ui-corner-all">
				<p>
					<span style="float: left; margin-right: .3em;" class="ui-icon ui-icon-alert"></span>
					<spring:message code="login.error.message.notsuccessful" text="Your login attempt was not successful, try again.<br/>&nbsp;Reason:" />
	                <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.
				</p>
			</div>
		  </div>
		</c:if>
		<div id="openid_choice">
            <p><spring:message code="login.form.openid_choice.p" text="Please click your account provider:" /></p>
            <div id="openid_btns"></div>
        </div>
        <div id="openid_input_area">
            <input id="openid_identifier" name="openid_identifier" type="text" value="http://" />
            <input id="openid_submit" type="submit" value='<spring:message code="login.form.btn.submit" text="Sign-In" />' />
        </div>
        <noscript>
            <p>OpenID is service that allows you to log-on to many different websites using a single indentity.
            Find out <a href="http://openid.net/what/">more about OpenID</a> and <a href="http://openid.net/get/">how to get an OpenID enabled account</a>.</p>
        </noscript>
    </fieldset>
</form>
<!-- /Simple OpenID Selector -->