<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div data-role="header" data-nobackbtn="true" data-position="fixed" data-theme="c">
	<h1>Quizz Sp&eacute;cial Java</h1>
	<a id="rules" href="<c:url value='/rules' />" class="ui-btn-left" data-role="button" data-inline="true" data-theme="c">
        R&egrave;gles
    </a>
    <a id="about" href="<c:url value='/about' />" class="ui-btn-right" data-role="button" data-inline="true" data-theme="c">
        A propos
    </a>
</div>