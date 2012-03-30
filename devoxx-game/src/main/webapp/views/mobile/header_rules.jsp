<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div data-role="header" data-nobackbtn="true" data-position="fixed" data-theme="c">
	<h1>Quizz So@t</h1>
    <a id="home" href="<c:url value='/home' />" class="ui-btn-left" data-theme="c">
        Accueil
    </a>
	<a id="about" href="<c:url value='/about' />" class="ui-btn-right" data-theme="c">
        A propos
    </a>
</div>