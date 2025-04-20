<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Населённые пункты</title>
    <style>
        <%@include file="/WEB-INF/style.css" %>
    </style>
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>
<body class="gray-background">
<div class="header">
    <h1>Населённые пункты</h1>
    <div class="right-horizontal-list">
        <form name="showTripsForm" method="POST" action="controller">
            <input type="hidden" name="command" value="open_trips_by_moderator"/>
            <button class="round-white-button text-right-margin">Поездки</button>
        </form>

        <form name="showLocationsForm" method="POST" action="controller">
            <input type="hidden" name="command" value="show_accounts_for_moderator"/>
            <button class="round-white-button text-right-margin">Пользователи</button>
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

<form name="createAccountForm" method="POST" action="controller">
    <input type="hidden" name="command" value="show_creating_locality"/>
    <button class="round-white-button" style="margin-left: calc(10% - 1em); margin-bottom: 2em">
        <span class="text-button">Добавить населённый пункт</span>
        <img src="./images/add.png" class="icon-right" alt="+" height="16" width="16">
    </button>
</form>

<div class="vertical-list">

    <c:forEach var="locality" items="${localities}">
        <div class="white-row">

            <div class="vertical-left-list" style="width:20%">
                <div class="block-div sub-text">
                    <span>Название</span>
                </div>
                <div class="block-div">
                    <span><c:out value="${locality.name}"/></span>
                </div>
            </div>

            <div class="vertical-left-list" style="width:20%">
                <div class="block-div sub-text">
                    <span>Тип</span>
                </div>
                <div class="block-div">
                    <c:forEach var="typeLocality" items="${typeLocalities}">
                        <c:if test="${typeLocality.id == locality.type}">
                            <span><c:out value="${typeLocality.name}"/></span>
                        </c:if>
                    </c:forEach>
                </div>
            </div>

            <div class="block-div" style="width:30%">
                <form name="editLocalityForm" method="POST" action="controller">
                    <input type="hidden" name="command" value="show_editing_locality"/>
                    <input type="hidden" name="localityId" value="<c:out value="${locality.id}"/>"/>
                    <input type="submit" value="Изменить" class="round-blue-button"/>
                </form>
            </div>

            <div class="block-div" style="width:30%">
                <form id="deleteLocalityForm" name="deleteLocalityForm" method="POST" action="controller">
                    <input type="hidden" name="command" value="delete_locality"/>
                    <input type="hidden" name="localityId" value="<c:out value="${locality.id}"/>"/>
                    <input id="deleteButton" type="submit" value="Удалить"
                           class="round-red-button"/>
                </form>
            </div>

        </div>

    </c:forEach>
</div>

<div id="confirmationDialog" class="dialog">
    <div class="dialog-content">
        <h2 class="header-text">Подтверждение удаления</h2>
        <p style="margin-bottom: 0">Вы уверены, что хотите удалить населённый пункт?</p>
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
