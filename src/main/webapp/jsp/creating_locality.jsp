<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <c:if test="${currentPage == 'creating_locality'}">
        <title>Создание населённого пункта</title>
    </c:if>
    <c:if test="${currentPage == 'editting_locality'}">
        <title>Редактирование населённого пункта</title>
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
            <form name="cancelCreatingLocalityForm">
                <input type="hidden" name="command" value="show_localities"/>
                <button type="submit" class="round-white-button big-button" style="padding: 1em; margin-right: 1em">
                    <img src="./images/back.png" class="icon-left" alt="<-" height="16" width="16">
                    <span class="text-button">Назад</span>
                </button>
            </form>
        </div>

        <div class="center-div">
            <h1>Населённый пункт</h1>
            <form name="creatingLocalityForm" method="POST" action="controller">
                <c:if test="${currentPage == 'creating_locality'}">
                    <input type="hidden" name="command" value="create_locality"/>
                </c:if>
                <c:if test="${currentPage == 'editing_locality'}">
                    <input type="hidden" name="command" value="edit_locality"/>
                    <input type="hidden" name="localityId" value="<c:out value="${locality.id}"/>"/>
                </c:if>

                <div class="input-group">
                    <label class="input-label text-right-margin" for="name">Название:</label>
                    <input type="text" class="round-input" id="name" name="name" style="width: 13em"
                           value="<c:out value="${locality.name}" default=""/>" required>
                </div>

                <div class="input-group">
                    <label class="input-up-label" for="type">Тип:</label>
                    <select id="type" name="type" style="width: 14em">
                        <c:forEach var="typeLocality" items="${typeLocalities}">
                            <option value="<c:out value="${typeLocality.id}"/>"
                                    <c:if test="${typeLocality.id == locality.type}">
                                        selected
                                    </c:if>
                            >
                                <c:out value="${typeLocality.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <c:if test="${currentPage == 'creating_locality'}">
                    <button type="submit" class="round-blue-button big-button">Создать</button>
                </c:if>
                <c:if test="${currentPage == 'editing_locality'}">
                    <button type="submit" class="round-blue-button big-button">Сохранить</button>
                </c:if>

            </form>
        </div>
    </div>
</div>

</body>
</html>
