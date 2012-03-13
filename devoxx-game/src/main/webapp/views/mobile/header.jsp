<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div data-role="header" data-nobackbtn="true" data-position="fixed" data-theme="b">
	<h1>Devoxx Game</h1>
	<a id="rules" href="<c:url value='/game/rules' />" class="ui-btn-right" data-icon="rules">
        R&egrave;gles
    </a>
    <a id="about" href="<c:url value='/game/about' />" class="ui-btn-right" data-icon="about">
        A propos
    </a>
</div>