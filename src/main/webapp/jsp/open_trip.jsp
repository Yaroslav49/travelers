<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Поездка</title>
    <style>
        <%@include file="/WEB-INF/style.css" %>
    </style>
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>
<body class="gray-background">
<div class="header">
    <h1>
        <c:if test="${trip.id < 1}">
            Поездка не найдена
        </c:if>
        <c:if test="${trip.id >= 1}">
            <c:out value="${trip.startPoint}"/> - <c:out value="${trip.finalPoint}"/>
        </c:if>
    </h1>
    <form name="openTripsForm" method="POST" action="controller">
        <c:if test="${currentPage == 'passenger_trip'}">
            <c:if test="${previousPage != 'passenger_my_trips'}">
                <input type="hidden" name="command" value="open_trips"/>
            </c:if>
            <c:if test="${previousPage == 'passenger_my_trips'}">
                <input type="hidden" name="command" value="open_my_trips"/>
            </c:if>
        </c:if>
        <c:if test="${currentPage == 'driver_trip'}">
            <input type="hidden" name="command" value="open_driver_trips"/>
        </c:if>
        <c:if test="${currentPage == 'moderator_trip'}">
            <input type="hidden" name="command" value="open_trips_by_moderator"/>
        </c:if>

        <button type="submit" class="round-white-button">
            <img src="./images/back.png" class="icon-left" alt="<-" height="16" width="16">
            <span class="text-button">Назад</span>
        </button>
    </form>

</div>
<div class="outer-div">
    <div class="horizontal-list" style="width: calc(80% + 2em)">
        <div class="white-cloud" style="margin-left: 0">
            <div class="vertical-list">

                <div class="text-group">
                    <span class="text-right-margin">Дата поездки:</span>
                    <span><c:out value="${trip.dateTime}"/></span>
                </div>

                <c:if test="${currentPage == 'passenger_trip' or (currentPage == 'driver_trip' and not isDriver)}">
                    <div class="text-group">
                    <span class="text-right-margin">
                        Водитель:
                    </span>
                        <span>
                        <c:out value="${trip.driverName}"/>
                    </span>
                    </div>

                    <div class="text-group">
                    <span class="text-right-margin">
                        Рейтинг водителя:
                    </span>
                        <span>
                        <fmt:formatNumber type="number" maxFractionDigits="1">
                            <c:out value="${trip.driverCountMarks > 0 ? trip.driverSumMarks / trip.driverCountMarks : 0}"/>
                        </fmt:formatNumber>
                        <span style="color: orange">★</span>
                    </span>
                    </div>
                </c:if>

                <div class="text-group">
                    <span class="text-right-margin">Цена:</span>
                    <span><c:out value="${trip.price}"/> ₽</span>
                </div>

                <div class="text-group">
                    <span class="text-right-margin">Участников:</span>
                    <span><c:out value="${trip.numberPassengers}"/>/<c:out value="${trip.numberSeats}"/></span>
                </div>

                <div class="text-group">
                    <span class="text-right-margin">Статус поездки:</span>
                    <span>
                        <c:choose>
                            <c:when test="${trip.state == 0}">
                                удалена
                            </c:when>
                            <c:when test="${trip.state == 1}">
                                активна
                            </c:when>
                            <c:when test="${trip.state == 2}">
                                завершена
                            </c:when>
                            <c:when test="${trip.state == 3}">
                                отменена
                            </c:when>
                            <c:otherwise>
                                не определён
                            </c:otherwise>
                        </c:choose>
                    </span>
                </div>

                <c:if test="${currentPage == 'passenger_trip'}">
                    <!-- Поездка активна -->
                    <c:if test="${trip.state == 1}">
                        <!-- Чувак участвует в ней (и не отказался) -->
                        <c:if test="${participantTrip.id > 0}">
                            <c:if test="${participantTrip.passengerRefusal == false}">
                                <div class="block-div">
                                    <form name="refusalTripForm" method="POST" action="controller">
                                        <input type="hidden" name="command" value="refusal_trip"/>
                                        <input type="hidden" name="trip_id" value="<c:out value="${trip.id}"/>"/>
                                        <input type="submit" value="Отказаться от поездки" class="round-blue-button"/>
                                    </form>
                                </div>
                            </c:if>
                            <c:if test="${participantTrip.passengerRefusal == true}">
                                <div class="text-group">
                                    <span>Вы отказались от поездки</span>
                                </div>
                            </c:if>
                        </c:if>
                        <!-- Чувак не участвует в поездке и места есть -->
                        <c:if test="${participantTrip.id < 0 and trip.numberSeats > trip.numberPassengers}">
                            <div class="block-div">
                                <form name="bookTripForm" method="POST" action="controller">
                                    <input type="hidden" name="command" value="book_trip"/>
                                    <input type="hidden" name="trip_id" value="<c:out value="${trip.id}"/>"/>
                                    <input type="submit" value="Забронировать" class="round-blue-button"/>
                                </form>
                            </div>
                        </c:if>
                    </c:if>

                    <!-- Поездка не активна и пассажир в ней участвовал-->
                    <c:if test="${trip.state != 1 and participantTrip.id > 0}">
                        <c:if test="${participantTrip.passengerMark > 0}">
                            <div class="text-group">
                                <span class="text-right-margin">Ваша оценка:</span>
                                <span><c:out value="${participantTrip.passengerMark}"/> <span
                                        style="color: orange">★</span></span>
                            </div>
                        </c:if>
                        <c:if test="${participantTrip.passengerMark == 0}">
                            <div class="block-div">
                                <form name="ratingForm" method="POST" action="controller">
                                    <input type="hidden" name="command" value="rate_driver"/>
                                    <input type="hidden" name="trip_id" value="<c:out value="${trip.id}"/>"/>
                                    <input name="rate_button" type="submit" value="Оценить водителя"
                                           class="round-blue-button"/>
                                </form>
                            </div>
                        </c:if>
                    </c:if>
                </c:if>

                <c:if test="${currentPage == 'driver_trip' and trip.state == 1 and isDriver}">
                    <div class="block-div">
                        <form name="finishTripForm" method="POST" action="controller">
                            <input type="hidden" name="command" value="close_trip"/>
                            <input type="hidden" name="trip_id" value="<c:out value="${trip.id}"/>"/>
                            <input type="submit" value="Завершить поездку" class="round-blue-button"/>
                        </form>
                    </div>
                    <div class="block-div">
                        <form name="cancelTripForm" method="POST" action="controller">
                            <input type="hidden" name="command" value="cancel_trip"/>
                            <input type="hidden" name="trip_id" value="<c:out value="${trip.id}"/>"/>
                            <input type="submit" value="Отменить поездку" class="round-blue-button"/>
                        </form>
                    </div>
                </c:if>

                <c:if test="${currentPage == 'moderator_trip'}">
                    <div class="block-div">
                        <form id="deleteTripForm" name="deleteTripForm" method="POST" action="controller">
                            <input type="hidden" name="command" value="delete_trip"/>
                            <input type="hidden" name="trip_id" value="<c:out value="${trip.id}"/>"/>
                            <input id="deleteButton" type="submit" value="Удалить" class="round-red-button"/>
                        </form>
                    </div>
                </c:if>

            </div>
        </div>

        <div class="vertical-left-list" style="width:100%">
            <div class="white-cloud" style="width: calc(100% - 3em); margin-right:0">
                <div class="vertical-left-list">

                    <div id="textCollectionPoint" class="block-div">
                        <div class="vertical-left-list">
                            <label class="big-label">Место сбора:</label>
                            <c:if test="${trip.collectionPoint == '' and currentPage != 'moderator_trip'}">
                                <span class="long-text">Уточняйте у водителя</span>
                            </c:if>
                            <c:if test="${trip.collectionPoint != '' or currentPage == 'moderator_trip'}">
                                <span class="long-text"><c:out value="${trip.collectionPoint}"/></span>
                            </c:if>
                        </div>
                    </div>

                    <div id="textDescription" class="block-div">
                        <div class="vertical-left-list">
                            <label class="big-label">Описание:</label>
                            <c:if test="${trip.description == '' and currentPage != 'moderator_trip'}">
                                <span class="long-text">Описание не заполнено</span>
                            </c:if>
                            <c:if test="${trip.description != '' or currentPage == 'moderator_trip'}">
                                <span class="long-text"><c:out value="${trip.description}"/></span>
                            </c:if>
                        </div>
                    </div>

                    <c:if test="${currentPage == 'moderator_trip'}">
                        <div id="editTripModeratorDiv" class="block-div" style="display: none">
                            <form id="editTripModeratorForm" name="editTripModeratorForm" method="POST"
                                  action="controller">
                                <input type="hidden" name="command" value="edit_trip"/>
                                <input type="hidden" name="trip_id" value="<c:out value="${trip.id}"/>"/>
                                <div class="block-div">
                                    <div class="vertical-left-list">
                                        <label class="big-label">Место сбора:</label>
                                        <input type="text" class="round-input" id="collectionPoint"
                                               name="collectionPoint" maxlength="50"
                                               style="width: 20em" value="<c:out value="${trip.collectionPoint}"/>">
                                    </div>
                                </div>

                                <div class="block-div">
                                    <div class="vertical-left-list">
                                        <label class="big-label">Описание:</label>
                                        <textarea id="description" class="round-input" name="description" cols="60"
                                                  rows="5" maxlength="255"
                                        ><c:out value="${trip.description}"/></textarea>
                                    </div>
                                </div>

                                <div class="center-horizontal-list" style="margin-top: 1em;">
                                    <button id="saveEditTripButton" type="submit"
                                            class="round-blue-button text-right-margin">Сохранить
                                    </button>
                                    <button id="cancelEditTripButton" type="button" class="round-blue-button">Отмена
                                    </button>
                                </div>
                            </form>
                        </div>

                        <div class="block-div" id="editTripButtonDiv">
                            <button id="editTripButton" class="round-blue-button" style="display: flex">Редактировать
                            </button>
                        </div>
                    </c:if>
                </div>
            </div>

            <!-- Строка водителя для модератора -->
            <c:if test="${currentPage == 'moderator_trip'}">
                <h3 style="margin-left: 1em">Водитель:</h3>
                <div class="white-cloud" style="width: calc(100% - 3em); margin-right:0">
                    <div class="block-div">
                        <span>
                            <c:out value="${driver.name}"/> -
                        </span>
                        <span>
                            <fmt:formatNumber type="number" maxFractionDigits="1">
                                <c:out value="${trip.driverCountMarks > 0 ? trip.driverSumMarks / trip.driverCountMarks : 0}"/>
                            </fmt:formatNumber>
                            <span style="color: orange">★</span>
                        </span>
                    </div>

                    <c:if test="${driver.isBlocked == true}">
                        <div class="block-div">
                            <span style="color: red">Заблокирован</span>
                        </div>
                    </c:if>

                    <c:if test="${driver.isBlocked == false}">
                        <div class="block-div">
                            <form name="moderatorDisApproveForm" method="POST" action="controller">
                                <input type="hidden" name="command" value="block_user"/>
                                <input type="hidden" name="accountId" value="<c:out value="${driver.id}"/>"/>
                                <input type="hidden" name="trip_id" value="<c:out value="${trip.id}"/>"/>
                                <input type="submit" value="Заблокировать"
                                       class="round-border-red-button minimum-padding"/>
                            </form>
                        </div>
                    </c:if>

                    <c:if test="${driver.isBlocked == true}">
                        <div class="block-div">
                            <form name="moderatorApproveForm" method="POST" action="controller">
                                <input type="hidden" name="command" value="unblock_user"/>
                                <input type="hidden" name="trip_id" value="<c:out value="${trip.id}"/>"/>
                                <input type="hidden" name="accountId"
                                       value="<c:out value="${driver.id}"/>"/>
                                <input type="submit" value="Разблокировать"
                                       class="round-border-green-button minimum-padding"/>
                            </form>
                        </div>
                    </c:if>
                </div>
            </c:if>

            <c:if test="${currentPage == 'driver_trip' || currentPage == 'moderator_trip'}">
                <h3 style="margin-left: 1em">Участники поездки:</h3>
                <c:if test="${empty passengers}">
                    <h4 style="margin-left: 1em; margin-top: 0">Здесь пока нет участников</h4>
                </c:if>

                <c:forEach var="passenger" items="${passengers}">
                    <div class="white-cloud" style="width: calc(100% - 3em); margin-right:0">

                        <div class="block-div">
                            <span>
                                <c:out value="${passenger.name}"/> -
                            </span>
                            <span>
                                <fmt:formatNumber type="number" maxFractionDigits="1">
                                    <c:out value="${passenger.countMarks > 0 ? passenger.sumMarks / passenger.countMarks : 0}"/>
                                </fmt:formatNumber>
                                <span style="color: orange">★</span>
                            </span>
                        </div>

                        <!-- Состояния для водителя -->
                        <c:if test="${currentPage == 'driver_trip'}">
                            <c:if test="${passenger.driverApprove == 1 && passenger.passengerRefusal == false}">
                                <div class="block-div">
                                    <c:if test="${trip.state == 1}">
                                        <span>Участвует в поездке</span>
                                    </c:if>
                                    <c:if test="${trip.state != 1}">
                                        <span>Участвовал(а) в поездке</span>
                                    </c:if>
                                </div>
                            </c:if>

                            <c:if test="${passenger.passengerRefusal == true}">
                                <div class="block-div">
                                    <span>Отказался(ась) от поездки</span>
                                </div>
                            </c:if>
                        </c:if>

                        <!-- Состояния для модератора -->
                        <c:if test="${currentPage == 'moderator_trip'}">
                            <c:if test="${passenger.isBlocked == true}">
                                <div class="block-div">
                                    <span style="color: red">Заблокирован(а)</span>
                                </div>
                            </c:if>
                            <c:if test="${passenger.isBlocked == false}">
                                <c:if test="${passenger.driverApprove < 0}">
                                    <div class="block-div">
                                        <span style="color: red">Отклонен(а)</span>
                                    </div>
                                </c:if>

                                <c:if test="${passenger.driverApprove >= 0 && passenger.passengerRefusal == true}">
                                    <div class="block-div">
                                        <span style="color: red">Отказался(ась)</span>
                                    </div>
                                </c:if>

                                <c:if test="${passenger.driverApprove == 1 && passenger.passengerRefusal == false}">
                                    <div class="block-div">
                                        <c:if test="${trip.state == 1}">
                                            <span style="color: green">Участвует</span>
                                        </c:if>
                                        <c:if test="${trip.state != 1}">
                                            <span style="color: green">Участвовал(а) в поездке</span>
                                        </c:if>
                                    </div>
                                </c:if>

                                <c:if test="${passenger.driverApprove == 0 && passenger.passengerRefusal == false}">
                                    <div class="block-div">
                                        <c:if test="${trip.state == 1}">
                                            <span>В ожидании</span>
                                        </c:if>
                                        <c:if test="${trip.state != 1}">
                                            <span>Подавал(а) заявку</span>
                                        </c:if>
                                    </div>
                                </c:if>
                            </c:if>

                            <!-- Кнопки модератора -->
                            <c:if test="${passenger.isBlocked == false}">
                                <div class="block-div">
                                    <form name="moderatorDisApproveForm" method="POST" action="controller">
                                        <input type="hidden" name="command" value="block_user"/>
                                        <input type="hidden" name="trip_id" value="<c:out value="${trip.id}"/>"/>
                                        <input type="hidden" name="accountId"
                                               value="<c:out value="${passenger.id}"/>"/>
                                        <input type="submit" value="Заблокировать"
                                               class="round-border-red-button minimum-padding"/>
                                    </form>
                                </div>
                            </c:if>

                            <c:if test="${passenger.isBlocked == true}">
                                <div class="block-div">
                                    <form name="moderatorApproveForm" method="POST" action="controller">
                                        <input type="hidden" name="command" value="unblock_user"/>
                                        <input type="hidden" name="trip_id" value="<c:out value="${trip.id}"/>"/>
                                        <input type="hidden" name="accountId"
                                               value="<c:out value="${passenger.id}"/>"/>
                                        <input type="submit" value="Разблокировать"
                                               class="round-border-green-button minimum-padding"/>
                                    </form>
                                </div>
                            </c:if>
                        </c:if>

                        <!-- Кнопки водителя -->
                        <c:if test="${currentPage == 'driver_trip'}">
                            <c:if test="${passenger.driverApprove == 0 && passenger.passengerRefusal == false}">
                                <c:if test="${trip.state == 1 && isDriver}">
                                    <div class="block-div">
                                        <form name="driverApproveForm" method="POST" action="controller">
                                            <input type="hidden" name="command" value="driver_approve"/>
                                            <input type="hidden" name="passenger_id"
                                                   value="<c:out value="${passenger.id}"/>"/>
                                            <input type="hidden" name="trip_id" value="<c:out value="${trip.id}"/>"/>
                                            <input type="submit" value="Принять" class="round-green-button"/>
                                        </form>
                                    </div>

                                    <div class="block-div">
                                        <form name="driverDisApproveForm" method="POST" action="controller">
                                            <input type="hidden" name="command" value="driver_disapprove"/>
                                            <input type="hidden" name="passenger_id"
                                                   value="<c:out value="${passenger.id}"/>"/>
                                            <input type="hidden" name="trip_id" value="<c:out value="${trip.id}"/>"/>
                                            <input type="submit" value="Отклонить" class="round-red-button"/>
                                        </form>
                                    </div>
                                </c:if>

                                <c:if test="${trip.state != 1}">
                                    <div class="block-div">
                                        <span>Подавал(а) заявку</span>
                                    </div>
                                </c:if>
                            </c:if>

                            <c:if test="${trip.state != 1 && passenger.driverApprove == 1 && isDriver}">
                                <c:if test="${passenger.driverMark == 0}">
                                    <form name="ratingForm" method="POST" action="controller">
                                        <input type="hidden" name="command" value="rate_passenger"/>
                                        <input type="hidden" name="trip_id" value="<c:out value="${trip.id}"/>"/>
                                        <input type="hidden" name="participant_id"
                                               value="<c:out value="${passenger.id}"/>"/>
                                        <input name="rate_button" type="submit" value="Оценить"
                                               class="round-blue-button"/>
                                    </form>
                                </c:if>
                                <c:if test="${passenger.driverMark > 0}">
                                    <div class="block-div">
                                        <span>Ваша оценка: <c:out value="${passenger.driverMark}"/> <span
                                                style="color: orange">★</span></span>
                                    </div>
                                </c:if>
                            </c:if>
                        </c:if>

                    </div>
                </c:forEach>

            </c:if>
        </div>

    </div>
</div>

<div id="confirmationDialog" class="dialog">
    <div class="dialog-content">
        <h2 class="header-text">Подтверждение удаления</h2>
        <p style="margin-bottom: 0">Вы уверены, что хотите удалить поездку?</p>
        <p style="margin-top: 0">Это действие необратимо.</p>
        <div class="input-group" style="margin-bottom: 1em">
            <button id="confirmButton" class="round-red-button text-right-margin">Удалить</button>
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
        document.getElementById('closeErrorDeleting').addEventListener('click', function (event) {
            event.preventDefault();
            document.getElementById('errorDeletingDialog').style.display = 'none';
        });
    </script>
</c:if>

<div id="rateDialog" class="dialog">
    <div class="dialog-content">
        <h2 class="header-text" style="margin-bottom: 1em">Ваша оценка</h2>
        <div class="stars" id="stars">
            <span class="star" data-value="1">&#9733;</span>
            <span class="star" data-value="2">&#9733;</span>
            <span class="star" data-value="3">&#9733;</span>
            <span class="star" data-value="4">&#9733;</span>
            <span class="star" data-value="5">&#9733;</span>
        </div>
        <input type="hidden" id="rating" name="rating" value="0">
        <div class="center-horizontal-list">
            <label id="errorRating" class="error-label"></label>
        </div>
        <div class="input-group" style="margin-bottom: 1em">
            <button id="confirmRatingButton" type="submit" class="round-green-button text-right-margin">Оценить</button>
            <button id="cancelRatingButton" class="round-red-button">Отмена</button>
        </div>
    </div>
</div>

<!-- Выставление оценок -->
<script charset="utf-8">
    const rateButtons = document.getElementsByName('rate_button');
    var currentRateButton;
    for (let i = 0; i < rateButtons.length; i++) {
        rateButtons[i].addEventListener('click', function (event) {
            const rateDialog = document.getElementById('rateDialog');
            event.preventDefault();
            currentRateButton = event.target;
            rateDialog.style.display = 'flex';
        });
    }

    document.getElementById('cancelRatingButton').addEventListener('click', function () {
        document.getElementById('rateDialog').style.display = 'none';
    });

    //логика формы оценки
    const stars = document.querySelectorAll('.star');
    const ratingInput = document.getElementById('rating');

    stars.forEach(star => {
        star.addEventListener('click', function () {
            const ratingValue = this.getAttribute('data-value');
            ratingInput.value = ratingValue;

            // Убираем класс selected у всех звёзд
            stars.forEach(s => s.classList.remove('selected'));

            // Закрашиваем звёзды до выбранной включительно
            for (let i = 0; i < ratingValue; i++) {
                stars[i].classList.add('selected');
            }
        });
    });

    document.getElementById('confirmRatingButton').addEventListener('click', function () {
        const rating = ratingInput.value;
        if (rating > 0) {
            var input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'mark';
            input.value = rating;
            currentRateButton.form.appendChild(input);
            currentRateButton.form.submit();
        } else {
            document.getElementById('errorRating').textContent = 'Выберите оценку'
        }
    });
</script>


<c:if test="${currentPage == 'moderator_trip'}">
    <script charset="utf-8">
        //удаление поездки
        document.getElementById('deleteButton').addEventListener('click', function (event) {
            event.preventDefault(); // Предотвращаем отправку формы
            document.getElementById('confirmationDialog').style.display = 'flex'; // Показываем диалог
        });

        document.getElementById('cancelButton').addEventListener('click', function () {
            document.getElementById('confirmationDialog').style.display = 'none'; // Скрываем диалог
        });

        document.getElementById('confirmButton').addEventListener('click', function () {
            document.getElementById('deleteTripForm').submit(); // Отправляем форму
        });

        //редактирование поездки
        document.getElementById('editTripButton').addEventListener('click', function (event) {
            document.getElementById('textCollectionPoint').style.display = "none";
            document.getElementById('textDescription').style.display = "none";
            document.getElementById('editTripModeratorDiv').style.display = "flex";
            document.getElementById('editTripButtonDiv').style.display = "none";
        });

        document.getElementById('cancelEditTripButton').addEventListener('click', function (event) {
            document.getElementById('textCollectionPoint').style.display = "flex";
            document.getElementById('textDescription').style.display = "flex";
            document.getElementById('editTripModeratorDiv').style.display = "none";
            document.getElementById('editTripButtonDiv').style.display = "flex";
        });
    </script>
</c:if>
</body>
</html>
