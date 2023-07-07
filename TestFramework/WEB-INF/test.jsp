<%-- 
    Document   : index
    Created on : 17 avr. 2023, 23:45:13
    Author     : TOAVINA
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <% out.print(request.getAttribute("test")); %> 
        
        <form action="add.do" method="post">
            First Name: <input type="text" name="firstName"><br>
            Last Name: <input type="text" name="lastName"><br>
            Age: <input type="number" name="age"><br>
    <input type="submit" value="Save">
</form>

    </body>
</html>
