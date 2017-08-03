<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sample Application JSP Page</title>
</head>

<body>

<h1>Sample Application JSP Page</h1>

<br />

<p>This is the output of a JSP page that is part of the HelloWorld application.</p>

<%= new String("Hello! I'm here!") %>

<c:if test="${not empty message}">
    <h1>${message}</h1>
</c:if>
</body>
</html> 