<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <c:if test="${currentPage == 'creating_account'}">
        <title>Создание аккаунта</title>
    </c:if>
    <c:if test="${currentPage == 'editing_account'}">
        <title>Изменение аккаунта</title>
    </c:if>
    <style>
        <%@include file="/WEB-INF/style.css" %>
    </style>
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>
<body class="gray-background">
<div class="outer-center-div-margin">
    <div class="vertical-list">
        <div class="horizontal-list big-margin" style="width: 100%; margin-top: 0">
            <form name="cancelCreatingAccountForm">
                <input type="hidden" name="command" value="show_accounts"/>
                <button type="submit" class="round-white-button big-button" style="padding: 1em; margin-right: 1em">
                    <img src="./images/back.png" class="icon-left" alt="<-" height="16" width="16">
                    <span class="text-button">Назад</span>
                </button>
            </form>
        </div>

        <div class="center-div">
            <c:if test="${currentPage == 'creating_account'}">
                <h1>Создание аккаунта</h1>
            </c:if>
            <c:if test="${currentPage == 'editing_account'}">
                <h1>Изменение аккаунта</h1>
            </c:if>
            <form name="creatingAccountForm" method="POST" action="controller">
                <c:if test="${currentPage == 'creating_account'}">
                    <input type="hidden" name="command" value="create_account"/>
                </c:if>
                <c:if test="${currentPage == 'editing_account'}">
                    <input type="hidden" name="command" value="edit_account"/>
                    <input type="hidden" name="previousPage" value="<c:out value="${previousPage}"/>"/>
                    <input type="hidden" name="accountId" value="<c:out value="${account.id}" default="-1"/>"/>
                </c:if>

                <div class="input-group">
                    <label class="input-label text-right-margin" for="login">Логин:</label>
                    <input type="text" class="round-input" id="login" name="login" style="width: 13em"
                           value="<c:out value="${account.login}" default=""/>" required>
                </div>

                <div class="input-group">
                    <label class="input-label text-right-margin" for="password">Пароль:</label>
                    <input type="text" class="round-input" id="password" name="password" style="width: 13em"
                           value="<c:out value="${account.password}" default=""/>" required>
                </div>

                <div class="input-group">
                    <label class="input-up-label" for="group">Роль:</label>
                    <select id="group" name="group" style="width: 14em">
                        <c:forEach var="userGroup" items="${userGroups}">
                            <option value="<c:out value="${userGroup.id}"/>"
                                    <c:if test="${account.groupId == userGroup.id}">
                                        selected
                                    </c:if>
                            >
                                <c:out value="${userGroup.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="input-group" id="input-name" style="
                <c:if test="${account.groupId > 2}">
                        display: flex
                </c:if>
                <c:if test="${currentPage == 'creating_account' || account.groupId <= 2}">
                        display: none
                </c:if>
                        ">
                    <label class="input-label text-right-margin" for="name">Имя:</label>
                    <input type="text" class="round-input" id="name" name="name" style="width: 13em"
                           value="<c:out value="${account.name}" default=""/>">
                </div>

                <c:if test="${currentPage == 'creating_account'}">
                    <button type="submit" class="round-blue-button big-button">Создать</button>
                </c:if>
                <c:if test="${currentPage == 'editing_account'}">
                    <button type="submit" class="round-blue-button big-button">Сохранить</button>
                </c:if>

            </form>
        </div>
    </div>
</div>

<script charset="utf-8">
    document.getElementById('group').addEventListener('change', function (event) {
        const inputName = document.getElementById("input-name");
        const inputNameTextField = document.getElementById("name");
        if (parseInt(event.target.value) > 2) {
            inputName.style.display = "flex";
            inputNameTextField.required = true;
        } else {
            inputNameTextField.required = false;
            inputName.style.display = "none";
        }
    });
</script>

</body>
</html>
