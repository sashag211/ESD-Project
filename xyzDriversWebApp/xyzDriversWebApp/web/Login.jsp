<%-- 
    Document   : Login
    Created on : 13-Nov-2017, 11:44:11
    Author     : owner
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login Page</h1>
        <form>
            Username:<br>
            <input type="text" name="User ID"><br>
            Password:<br>
            <input type="password" name="Password"><br>
            
            <br>
            <input method = "get" action = "/LoginServlet" class="btn" type="submit" value="Submit">
            <input class="btn" type="reset" value="Reset">
        </form>
    </body>
</html>
