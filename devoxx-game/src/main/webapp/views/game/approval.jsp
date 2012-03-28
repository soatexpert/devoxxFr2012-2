<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div>
<spring:message code="game.approval.intro" text="Vous devez accepter les règles suivantes" />
</div>

<tiles:insertAttribute name="rules" />

<div class="center-wrapper">
    <a id="approve-btn" href="<c:url value='/approveRules' />" data-role="button" data-inline="true" data-iconpos="top" data-icon="check">
            <spring:message code="game.approval.ok" text="Je suis d'accord" />
    </a>
</div>