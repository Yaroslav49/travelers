<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <h1>Все пользователи</h1>
    <div class="right-horizontal-list">
        <form name="showTripsForm" method="POST" action="controller">
            <input type="hidden" name="command" value="open_trips_by_moderator"/>
            <button class="round-white-button text-right-margin">Поездки</button>
        </form>

        <form name="showLocationsForm" method="POST" action="controller">
            <input type="hidden" name="command" value="show_localities"/>
            <button class="round-white-button text-right-margin">Населённые пункты</button>
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

<div class="vertical-list">
    <c:forEach var="user" items="${users}">
        <div class="white-row">

            <div class="vertical-left-list" style="width:20%">
                <div class="block-div sub-text">
                    <span>Имя</span>
                </div>
                <div class="block-div">
                    <span><c:out value="${user.name}"/></span>
                </div>
            </div>

            <div class="vertical-left-list" style="width:20%">
                <div class="block-div sub-text">
                    <span>Роль</span>
                </div>
                <div class="block-div">
                    <c:choose>
                        <c:when test="${user.groupId == 1}">
                            <span>Администратор</span>
                        </c:when>
                        <c:when test="${user.groupId == 2}">
                            <span>Модератор</span>
                        </c:when>
                        <c:when test="${user.groupId == 3}">
                            <span>Водитель</span>
                        </c:when>
                        <c:when test="${user.groupId == 4}">
                            <span>Пассажир</span>
                        </c:when>
                    </c:choose>
                </div>
            </div>

            <div class="vertical-left-list" style="width:15%">
                <div class="block-div sub-text">
                    <span>Рейтинг</span>
                </div>
                <div class="block-div">
                    <span>
                        <fmt:formatNumber type="number" maxFractionDigits="1">
                            <c:out value="${user.countMarks > 0 ? user.sumMarks / user.countMarks : 0}"/>
                        </fmt:formatNumber>
                        <span style="color: orange">★</span>
                    </span>
                </div>
            </div>

            <div class="vertical-left-list" style="width:22%">
                <div class="block-div sub-text">
                    <span>Статус</span>
                </div>
                <div class="block-div">
                    <c:choose>
                        <c:when test="${user.isBlocked == true}">
                            <span style="color: red">Заблокирован</span>
                        </c:when>
                        <c:otherwise>
                            <span>Активен</span>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

            <c:if test="${user.isBlocked == false}">
                <div class="block-div" style="width:23%">
                    <form name="blockUserForm" method="POST" action="controller">
                        <input type="hidden" name="command" value="block_user"/>
                        <input type="hidden" name="accountId" value="<c:out value="${user.id}"/>"/>
                        <input type="submit" value="Заблокировать" class="round-border-red-button minimum-padding"/>
                    </form>
                </div>
            </c:if>

            <c:if test="${user.isBlocked == true}">
                <div class="block-div" style="width:23%">
                    <form id="unblockUserForm" name="deleteAccountForm" method="POST" action="controller">
                        <input type="hidden" name="command" value="unblock_user"/>
                        <input type="hidden" name="accountId" value="<c:out value="${user.id}"/>"/>
                        <input id="deleteButton" type="submit" value="Разблокировать"
                               class="round-border-green-button minimum-padding"/>
                    </form>
                </div>
            </c:if>

        </div>

    </c:forEach>
</div>

<jsp:include page="/jsp/confirmation_logout_dialog.jsp"></jsp:include>

<script charset="utf-8">
    <%@include file="/WEB-INF/confirmationLogout.js" %>
</script>

</body>
</html>
