<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Список поездок</title>
    <style>
        <%@include file="/WEB-INF/style.css" %>
    </style>
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>
<body class="gray-background">
<div class="header">
    <c:if test="${currentPage == 'passenger_trips' || currentPage == 'moderator_trips'}">
        <h1>Доступные поездки</h1>
    </c:if>
    <c:if test="${currentPage == 'passenger_my_trips'}">
        <h1>Мои поездки</h1>
    </c:if>
    <c:if test="${currentPage == 'driver_my_trips'}">
        <h1>Мои поездки</h1>
    </c:if>

    <div class="right-horizontal-list">
        <c:if test="${currentPage != 'moderator_trips'}">
            <form name="openTripsForm" method="POST" action="controller">
                <c:if test="${currentPage == 'passenger_trips'}">
                    <input type="hidden" name="command" value="open_my_trips"/>
                    <button class="round-white-button text-right-margin">Мои поездки</button>
                </c:if>
                <c:if test="${currentPage == 'passenger_my_trips'}">
                    <input type="hidden" name="command" value="open_trips"/>
                    <button class="round-white-button text-right-margin">Все поездки</button>
                </c:if>
                <c:if test="${currentPage == 'driver_my_trips'}">
                    <input type="hidden" name="command" value="show_creating_trip"/>
                    <button class="round-white-button text-right-margin">Создать поездку</button>
                </c:if>
            </form>
        </c:if>

        <c:if test="${currentPage == 'moderator_trips'}">
            <form name="showAccountsForm" method="POST" action="controller">
                <input type="hidden" name="command" value="show_accounts_for_moderator"/>
                <button class="round-white-button text-right-margin">Пользователи</button>
            </form>

            <form name="showLocationsForm" method="POST" action="controller">
                <input type="hidden" name="command" value="show_localities"/>
                <button class="round-white-button text-right-margin">Населённые пункты</button>
            </form>
        </c:if>

        <form id="logoutForm" name="logoutForm" method="POST" action="controller">
            <input type="hidden" name="command" value="logout"/>
            <button id="logoutButton" class="round-white-button minimum-padding">
                <span class="text-button"><c:out value="${login}"/></span>
                <img class="icon-right" src="./images/logout.png" alt="[->" height="32" width="32">
            </button>
        </form>
    </div>

</div>

<c:forEach var="listTrip" items="${trips}">
    <c:if test="${not empty listTrip.name}">
        <h2 style="margin-left: 10%"><c:out value="${listTrip.name}"/></h2>
    </c:if>
    <c:forEach var="trip" items="${listTrip.data}">
        <div class="vertical-list">
            <div class="white-row">

                <div class="vertical-list" style="width:30%">
                    <div class="block-div">
                        <span><c:out value="${trip.startPoint}"/> - </span>
                        <span><c:out value="${trip.finalPoint}"/></span>
                    </div>
                    <div class="block-div sub-text">
                        <span><c:out value="${trip.dateTime}"/></span>
                    </div>
                </div>

                <c:if test="${currentPage != 'driver_my_trips'}">
                    <div class="block-div" style="width:15%">
                    <span>
                        <c:out value="${trip.driverName}"/> -
                    </span>
                        <span>
                            <fmt:formatNumber type="number" maxFractionDigits="1">
                                <c:out value="${trip.driverCountMarks > 0 ? trip.driverSumMarks / trip.driverCountMarks : 0}"/>
                            </fmt:formatNumber>
                        <span style="color: orange">★</span>
                    </span>
                    </div>
                </c:if>

                <div class="vertical-list" style="width:25%">
                    <div class="block-div">
                        <span><c:out value="${trip.price}"/> ₽</span>
                    </div>
                    <div class="block-div sub-text">
                        <span>Осталось <c:out value="${trip.numberSeats - trip.numberPassengers}"/> мест</span>
                    </div>
                </div>

                <div style="width:30%">
                    <form name="openTripsForm" method="POST" action="controller">
                        <c:if test="${currentPage == 'driver_my_trips'}">
                            <input type="hidden" name="command" value="open_driver_trip"/>
                        </c:if>
                        <c:if test="${currentPage == 'passenger_trips' || currentPage == 'passenger_my_trips'}">
                            <input type="hidden" name="command" value="open_trip"/>
                            <input type="hidden" name="currentPage" value="<c:out value="${currentPage}"/>"/>
                        </c:if>
                        <c:if test="${currentPage == 'moderator_trips'}">
                            <input type="hidden" name="command" value="open_trip_by_moderator"/>
                        </c:if>
                        <input type="hidden" name="tripId" value="<c:out value="${trip.id}"/>"/>

                        <input type="submit" value="Выбрать" class="round-blue-button"/>
                    </form>
                </div>

            </div>
        </div>
    </c:forEach>
</c:forEach>

<c:if test="${empty trips}">
    <div class="white-row">
        <span>Поездки не найдены.</span>
    </div>
</c:if>

<c:if test="${not empty errorMessage}">
    <div id="errorDialog" class="dialog" style="display: flex">
        <div class="dialog-content">
            <h2 class="header-text">Ошибка</h2>
            <p style="margin-bottom: 0; text-align: center">
                <c:out value="${errorMessage}"></c:out>
            </p>
            <div class="center-horizontal-list" style="margin: 1em; margin-top: 2em; margin-bottom: 0.5em">
                <button id="closeErrorDialog" class="round-blue-button">Ок</button>
            </div>
        </div>
    </div>
    <script charset="utf-8">
        document.getElementById('closeErrorDialog').addEventListener('click', function (event) {
            event.preventDefault();
            document.getElementById('errorDialog').style.display = 'none';
        });
    </script>
</c:if>

<jsp:include page="/jsp/confirmation_logout_dialog.jsp"></jsp:include>

<script charset="utf-8">
    <%@include file="/WEB-INF/confirmationLogout.js" %>
</script>

</body>
</html>