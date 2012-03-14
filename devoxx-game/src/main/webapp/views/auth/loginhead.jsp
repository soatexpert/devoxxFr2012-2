<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<c:url value="/" var="baseUrl" />
<spring:message code="login.openid.locale.filename" text="openid-en" var="openid_locale" />
<link type="text/css" rel="stylesheet" href="${baseUrl}css/openid/openid-shadow.css" />
<script type="text/javascript" src="${baseUrl}js/openid/openid-jquery.js"></script>
<script type="text/javascript" src="${baseUrl}js/openid/${openid_locale}.js"></script>