<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Bootstrap Example</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script>
        function validateName() {
            if ($('#exampleName').val() === '') alert($('#exampleName').attr('title'));
            if ($('#exampleSurname').val() === '') alert($('#exampleSurname').attr('title'));
            if ($('#exampleInput').val() === '') alert($('#exampleInput').attr('title'));
            if ($('#exampleText').val() === '') alert($('#exampleText').attr('title'));
            return false;
        }
        function addRow() {
            //получаем значение поля usr
            const name = $('#exampleName').val();
            const surName = $('#exampleSurname').val();
            const p = $('#exampleInput').val();
            const text = $('#exampleText').val();

            //получаем ссылку на последний элемент в таблице.
            //и после него добавляем html
            $('#table tr:last').after('<tr><td>' + name +'</td><td>' + surName +'</td><td>' + p +'</td><td>' + text +'</td></tr>');
        }
    </script>
</head>
<body>

<div class="container">
    <h1>My First Bootstrap Page</h1>
    <p>This is some text.</p>
</div>

<div class="container pt-3">
    <div class="row">
        <form>
            <div class="form-group">
                <label for="exampleName">Имя</label>
                <input type="text" class="form-control" id="exampleName" title="Введите свое имя" aria-describedby="emailHelp">
            </div>
            <div class="form-group">
                <label for="exampleSurname">Фамилия</label>
                <input type="text" class="form-control" title="Введите свою Фамилию" id="exampleSurname">
            </div>
            <div class="form-group">
                <label for="exampleInput">Пол</label>
                <input type="text" class="form-control" title="Введите свой пол" id="exampleInput">
            </div>
            <div class="form-group">
                <label for="exampleText">Описание</label>
                <input type="text" class="form-control" title="Введите описание" id="exampleText">
            </div>
            <button type="button" class="btn btn-default" onclick="return validateName();">Submit</button>
        </form>
        <table class="table" id='table'>
            <thead class="thead-dark">
            <tr>
                <th scope="col">Имя</th>
                <th scope="col">Фамилия</th>
                <th scope="col">Пол</th>
                <th scope="col">Описание</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>Mark</td>
                <td>Otto</td>
                <td>M</td>
                <td>@mdo</td>
            </tr>
            <tr>
                <td>Jacob</td>
                <td>Thornton</td>
                <td>M</td>
                <td>@fat</td>
            </tr>
            <tr>
                <td>Larry</td>
                <td>the Bird</td>
                <td>M</td>
                <td>@twitter</td>
            </tr>
            </tbody>
        </table>
        <button type="button" class="btn btn-default" onclick="addRow()">Add row</button>
    </div>
</div>
</body>
</html>

