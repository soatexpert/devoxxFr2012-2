<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title><tiles:insertAttribute name="pageTitle" ignore="true" /></title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="<c:url value='/css/game.css' />" />
        <link rel="stylesheet" href="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.css" />
        <link rel="stylesheet" type="text/css" href="http://dev.jtsage.com/cdn/simpledialog/latest/jquery.mobile.simpledialog.min.css" />
        <script src="http://code.jquery.com/jquery-1.6.4.min.js"></script>
        <script src="http://code.jquery.com/mobile/1.0.1/jquery.mobile-1.0.1.min.js"></script>
        <script type="text/javascript" src="http://dev.jtsage.com/cdn/simpledialog/latest/jquery.mobile.simpledialog.min.js"></script>
        <script type="text/javascript"> 
        $(document).bind('mobileinit', function() {
        	$.mobile.orientationChangeEnabled = true;
        });
        </script>
		<tiles:insertAttribute name="specificHead" ignore="true" />
	</head>
	<body>
		<div data-role="page">
			<tiles:insertAttribute name="header" />
						
			<div data-role="content">
			    <tiles:insertAttribute name="content" />
			</div>
			
			<tiles:insertAttribute name="footer" />
		</div>
	</body>
</html>