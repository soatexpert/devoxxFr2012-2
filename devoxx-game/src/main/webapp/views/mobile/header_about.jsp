<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div data-role="header" data-nobackbtn="true" data-position="fixed" data-theme="c">
	<h1>Quizz So@t</h1>
    <a id="home" href="<c:url value='/home' />" class="ui-btn-left" data-theme="c">
        Accueil
    </a>
	<a id="rules" href="<c:url value='/rules' />" class="ui-btn-right" data-theme="c">
        R&egrave;gles
    </a>
</div>