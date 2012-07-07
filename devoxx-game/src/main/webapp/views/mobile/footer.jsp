<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div class="footer-text">
    <div><spring:message code="game.footer.proposed" text="Quizz proposé par"/></div>
    <span><spring:message code="game.footer.created" text="Crée par "/></span>
    <a href="http://www.soat.fr"><img src="<c:url value='/img/soat.png'/>" alt="Soat"/></a>
    <span><spring:message code="game.footer.collaboration" text=" avec la collaboration de "/></span>
    <a href="http://www.developpez.com"><img src="<c:url value='/img/logo%20dev.png'/>" alt="developpez.com"></a>
    <span><spring:message code="game.footer.hosted" text=" hébergé par "/></span>
    <a href="http://www.cloudbees.org"><img src="<c:url value='/img/cloudbees03.png'/>" alt="cloudbees"></a>
</div>