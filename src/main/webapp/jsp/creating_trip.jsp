<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <title>Создание поездки</title>
    <style>
        <%@include file="/WEB-INF/style.css" %>
    </style>
    <link rel="icon" href="favicon.ico" type="image/x-icon">
</head>
<body class="gray-background">
<div class="outer-center-div-margin">
    <div class="vertical-list">
        <div class="horizontal-list big-margin" style="width: 100%; margin-top: 0">
            <form name="cancelCreatingTripForm">
                <input type="hidden" name="command" value="open_driver_trips"/>
                <button type="submit" class="round-white-button big-button" style="padding: 1em; margin-right: 1em">
                    <img src="./images/back.png" class="icon-left" alt="<-" height="16" width="16">
                    <span class="text-button">Назад</span>
                </button>
            </form>
        </div>

        <div class="center-div">
            <h1>Создание поездки</h1>
            <form name="creatingTripForm" id="creatingTripForm" method="POST" action="controller">
                <input type="hidden" name="command" value="create_trip"/>

                <div class="input-group">
                    <div class="vertical-left-list">
                        <label class="input-up-label" for="startPoint">Откуда:</label>
                        <select id="startPoint" name="startPoint">
                            <c:forEach var="locality" items="${localities}">
                                <option value="${locality.id}">${locality.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <hr style="width:40px; border:0; height: 2px; background-color: black; margin-top: 2em">
                    <div class="vertical-left-list">
                        <label class="input-up-label" for="finalPoint">Куда:</label>
                        <select id="finalPoint" name="finalPoint">
                            <c:forEach var="locality" items="${localities}">
                                <option value="${locality.id}">${locality.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="input-group">
                    <label class="input-label text-right-margin" for="datetime">Дата и время поездки:</label>
                    <input type="datetime-local" class="round-input" id="datetime" name="datetime" style="width: 20em"
                           required>
                </div>

                <div class="input-group">
                    <label class="input-label text-right-margin" for="numberSeats">Количество пассажиров:</label>
                    <input type="number" class="round-input" id="numberSeats" name="numberSeats" min="1"
                           style="width: 20em" required>
                </div>

                <div class="input-group" style="margin-right: 1em">
                    <label class="input-label text-right-margin" for="price">Стоимость поездки:</label>
                    <div>
                        <input type="number" class="round-input" id="price" name="price" min="0" style="width: 20em"
                               required>
                        <span class="symbol">₽</span>
                    </div>
                </div>

                <div class="input-group">
                    <label class="input-label text-right-margin" for="collectionPoint">Место сбора:</label>
                    <input type="text" class="round-input" id="collectionPoint" name="collectionPoint"
                           style="width: 20em">
                </div>

                <div class="input-group">
                    <label class="input-label text-right-margin" for="description">Описание:</label>
                    <textarea id="description" class="round-input" name="description" cols="60" rows="5"></textarea>
                </div>

                <label id="errorCreatingTripLabel" class="error-label"
                       style="display:block; margin-bottom: 2em"></label>
                <button type="submit" class="round-blue-button big-button">Создать</button>

            </form>
        </div>
    </div>
</div>

<script charset="utf-8">
    document.getElementById('creatingTripForm').addEventListener('submit', function (event) {
        const select1 = document.getElementById('startPoint').value;
        const select2 = document.getElementById('finalPoint').value;
        const errorMessage = document.getElementById('errorCreatingTripLabel');
        const tripDate = new Date(document.getElementById('datetime').value);

        // Сброс сообщения об ошибке
        errorMessage.textContent = '';


        // Проверка на одинаковые значения
        if (select1 && select2 && select1 === select2) {
            event.preventDefault(); // Отменяем отправку формы
            errorMessage.textContent = 'Выберите разные пункты для начала и конца поездки';
        }

        const today = new Date();
        //today.setHours(0, 0, 0, 0);
        const tomorrow = new Date(today.getTime() + 24 * 60 * 60 * 1000);
        if (tripDate < tomorrow) {
            event.preventDefault(); // Отменяем отправку формы
            errorMessage.textContent = 'Задавать дату поездки можно не раньше, чем за 24 часа';
        }
    });
</script>
</body>
</html>
