<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<div class="footer-text">
    <div><spring:message code="game.footer.proposed" text="Quizz proposé par"/></div>
    <a href="http://www.developpez.com"><img src="<c:url value='/img/logo%20dev.png'/>" alt="developpez.com"></a>
    <a href="http://www.soat.fr"><img id="soatImg" src="<c:url value='/img/soat.png'/>" alt="Soat"/></a>
    <a href="http://www.cloudbees.org"><img src="<c:url value='/img/logo_cloudbees_55.png'/>" alt="cloudbees"></a>
</div>