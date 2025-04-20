<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Пользователи</title>
    <style>
        <%@include file="/WEB-INF/style.css" %>
    </style>
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>
<body class="gray-background">
<div class="header">
    <c:if test="${currentPage == 'all_accounts'}">
        <h1>Все пользователи</h1>
    </c:if>
    <c:if test="${currentPage == 'authorized_accounts'}">
        <h1>Авторизованные пользователи</h1>
    </c:if>

    <div class="right-horizontal-list">
        <form name="showAccountsForm" method="POST" action="controller">
            <c:if test="${currentPage == 'all_accounts'}">
                <input type="hidden" name="command" value="show_authorized_accounts"/>
                <button class="round-white-button text-right-margin">Авторизованные пользователи</button>
            </c:if>
            <c:if test="${currentPage == 'authorized_accounts'}">
                <input type="hidden" name="command" value="show_accounts"/>
                <button class="round-white-button text-right-margin">Все пользователи</button>
            </c:if>
        </form>

        <form id="logoutForm" name="logoutForm" method="POST" action="controller">
            <input type="hidden" name="command" value="logout"/>
            <button id="logoutButton" class="round-white-button minimum-padding">
                <span class="text-button"><c:out value="${login}"/></span>
                <img class="icon-right" src="./images/logout.png" alt="[->" height="32" width="32">
            </button>
        </form>
    </div>
</div>

<c:if test="${currentPage == 'all_accounts'}">
    <form name="createAccountForm" method="POST" action="controller">
        <input type="hidden" name="command" value="show_creating_account"/>
        <button class="round-white-button" style="margin-left: calc(10% - 1em); margin-bottom: 2em">
            <span class="text-button">Добавить пользователя</span>
            <img src="./images/add.png" class="icon-right" alt="+" height="16" width="16">
        </button>
    </form>
</c:if>

<div class="vertical-list">
    <c:forEach var="account" items="${accounts}">
        <div class="white-row">

            <div class="vertical-left-list" style="width:13%">
                <div class="block-div sub-text">
                    <span>Логин</span>
                </div>
                <div class="block-div">
                    <span><c:out value="${account.login}"/></span>
                </div>
            </div>

            <div class="vertical-left-list" style="width:10%">
                <div class="block-div sub-text">
                    <span>Пароль</span>
                </div>
                <div class="block-div">
                    <span><c:out value="${account.password}"/></span>
                </div>
            </div>

            <div class="vertical-left-list" style="width:22%">
                <div class="block-div sub-text">
                    <span>Роль</span>
                </div>
                <div class="block-div">
                    <c:forEach var="group" items="${userGroups}">
                        <c:if test="${group.id == account.groupId}">
                            <span>
                                <c:out value="${group.name}"></c:out>
                            </span>
                        </c:if>
                    </c:forEach>
                </div>
            </div>

            <c:if test="${currentPage != 'authorized_accounts'}">
                <div class="vertical-left-list" style="width:23%">
                    <div class="block-div sub-text">
                        <span>Статус</span>
                    </div>
                    <div class="block-div">
                        <c:choose>
                            <c:when test="${account.isBlocked == true}">
                                <span style="color: red">Заблокирован</span>
                            </c:when>
                            <c:when test="${account.isAuthorized == true}">
                                <span style="color: green">Авторизован</span>
                            </c:when>
                            <c:otherwise>
                                <span>Не авторизован</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </c:if>

            <div class="block-div" style="width:16%">
                <form name="showEditAccountForm" method="POST" action="controller">
                    <input type="hidden" name="command" value="show_editing_account"/>
                    <input type="hidden" name="accountId" value="<c:out value="${account.id}"/>"/>
                    <input type="hidden" name="previousPage" value="<c:out value="${currentPage}"/>"/>
                    <input type="submit" value="Изменить" class="round-blue-button minimum-padding"/>
                </form>
            </div>

            <div class="block-div" style="width:16%">
                <form id="deleteAccountForm" name="deleteAccountForm" method="POST" action="controller">
                    <input type="hidden" name="command" value="delete_account"/>
                    <input type="hidden" name="accountId" value="<c:out value="${account.id}"/>"/>
                    <input type="hidden" name="previousPage" value="<c:out value="${currentPage}"/>"/>
                    <input name="deleteButton" type="submit" value="Удалить" class="round-red-button minimum-padding"/>
                </form>
            </div>

        </div>

    </c:forEach>
</div>

<div id="confirmationDialog" class="dialog">
    <div class="dialog-content">
        <h2 class="header-text">Подтверждение удаления</h2>
        <p style="margin-bottom: 0">Вы уверены, что хотите удалить пользователя?</p>
        <p style="margin-top: 0">Это действие необратимо.</p>
        <div class="input-group" style="margin-bottom: 1em">
            <button id="confirmButton" class="round-red-button">Удалить</button>
            <button id="cancelButton" class="round-green-button">Отмена</button>
        </div>
    </div>
</div>

<c:if test="${not empty errorMessage}">
    <div id="errorDeletingDialog" class="dialog" style="display: flex">
        <div class="dialog-content">
            <h2 class="header-text">Ошибка</h2>
            <p style="margin-bottom: 0; text-align: center">
                <c:out value="${errorMessage}"></c:out>
            </p>
            <div class="center-horizontal-list" style="margin: 1em; margin-top: 2em; margin-bottom: 0.5em">
                <button id="closeErrorDeleting" class="round-blue-button">Ок</button>
            </div>
        </div>
    </div>
    <script charset="utf-8">
        document.getElementById('closeErrorDeleting').addEventListener('click', function(event) {
            event.preventDefault();
            document.getElementById('errorDeletingDialog').style.display = 'none';
        });
    </script>
</c:if>


<jsp:include page="/jsp/confirmation_logout_dialog.jsp"></jsp:include>

<script charset="utf-8">
    <%@include file="/WEB-INF/confirmationLogout.js" %>
</script>

<script charset="utf-8">
    const deleteButtons = document.getElementsByName('deleteButton');
    var currentDeleteButton;
    for(let i = 0; i < deleteButtons.length; i++){
        deleteButtons[i].addEventListener('click', function(event) {
            const confirmationDialog = document.getElementById('confirmationDialog');
            event.preventDefault();
            currentDeleteButton = event.target;
            confirmationDialog.style.display = 'flex';
        });
    }

    document.getElementById('cancelButton').addEventListener('click', function() {
        document.getElementById('confirmationDialog').style.display = 'none';
    });

    document.getElementById('confirmButton').addEventListener('click', function() {
        currentDeleteButton.form.submit();
    });
</script>
</body>
</html>
