<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div id="infos">

    <c:if test="${invalidUser}">
        <div class="errorBox">
            <div>
                <p>
                    <span style="float: left; margin-left: 5px;" class="ui-icon ui-icon-alert"></span>
                    <spring:message code="game.invalid.user"
                                    text="Nom déjà pris!"/>
                </p>
            </div>
        </div>
    </c:if>

    <div class="home-intro">
        <spring:message code="game.nologin.intro" text="Prénom :"/>
        <div class="tagline">Que le meilleur gagne !!!</div>
    </div>

    <form method="post" action="register" data-ajax="false" id="register">
        <div class="center-wrapper login_field">
            <fieldset><spring:message code="game.nologin.name" text="Nom :"/></fieldset>
            <input type="text" id="playerName" name="playerName"/>
        </div>
        <div class="center-wrapper login_field">
            <fieldset><spring:message code="game.nologin.firstname" text="Prénom :"/></fieldset>
            <input type="text" id="playerFirstname" name="playerFirstname"/>
        </div>
        <div class="center-wrapper" id="validateBtn">
            <input type="submit" value="<spring:message code="game.play.validate" text="Valider" />"
                   data-inline="true"/>
        </div>
    </form>
</div>