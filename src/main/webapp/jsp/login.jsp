<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Авторизация</title>
    <style><%@include file="/WEB-INF/style.css"%></style>
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>
<body>
    <div class="outer-center-div gray-background">
        <div class="center-div">
            <form name="loginForm" method="POST" action="controller">
                <h1>Попутчики</h1>
                <input type="hidden" name="command" value="login" />

                <div class="input-group">
                    <label class="input-label">Логин:</label>
                    <input type="text" name="login" value="" class="bottom-input" autofocus required/>
                </div>

                <div class="input-group">
                    <label class="input-label">Пароль:</label>
                    <input type="password" name="password" value="" class="bottom-input" required/>
                </div>

                <label class="error-label"><c:out value="${errorLoginPassMessage}"/></label>
                <p/><input type="submit" value="Войти" class="round-blue-button big-button"/></p>
            </form>
        </div>
    </div>
</body>
</html>