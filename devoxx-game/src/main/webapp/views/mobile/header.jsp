<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div data-role="header" data-nobackbtn="true" data-position="fixed" data-theme="c">
	<h1>Quizz So@t</h1>
	<a id="rules" href="<c:url value='/rules' />" class="ui-btn-left" data-icon="rules" data-theme="c">
        R&egrave;gles
    </a>
    <a id="about" href="<c:url value='/about' />" class="ui-btn-right" data-icon="about" data-theme="c">
        A propos
    </a>
</div>