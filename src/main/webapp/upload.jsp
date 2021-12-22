<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Поле загрузки файлов, которое мы заслужили</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <c:if test="${user != null}">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/logout.do"><c:out
                        value="${user.name}"/>|Выйти</a>
            </li>
            </c:if>
    </div>
</div>
<div class="container">
    <h2>Загрузить изображение для кандидата:</h2>
    <span style="font-size: 30px; color: darkslateblue; font-family: 'Segoe Script',sans-serif; ">
        <c:out value="${name}"/>
    </span>
    <form method="post" enctype="multipart/form-data">
        <div class="checkbox">
            <input type="file" name="file">
        </div>
        <input type="button" class="btn btn-primary" onclick="history.back();" value="Назад"/>
        <button type="submit" class="btn btn-primary">Загрузить</button>
    </form>
</div>
<div class="container">
    <table class="table">
        <thead>
        <tr>
            <th>Ссылка для скачивания</th>
            <th>Фотография</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${images}" var="image" varStatus="status">
            <tr valign="top">
                <td><a href="<c:url value='/download?name=${image}'/>">Скачать</a></td>
                <td>
                    <img src="<c:url value='/download?name=${image}'/>" width="100px" height="100px"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>