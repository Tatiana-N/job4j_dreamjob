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
                <input type="text" class="form-control" id="exampleName" aria-describedby="emailHelp">
            </div>
            <div class="form-group">
                <label for="exampleSurname">Фамилия</label>
                <input type="text" class="form-control" id="exampleSurname">
            </div>
            <div class="form-group">
                <label for="exampleInput">Пол</label>
                <input type="text" class="form-control" id="exampleInput">
            </div>
            <div class="form-group">
                <label for="exampleText">Описание</label>
                <input type="text" class="form-control" id="exampleText">
            </div>
        </form>
        <table class="table">
            <thead class="thead-dark">
            <tr>
                <th scope="col">#</th>
                <th scope="col">Имя</th>
                <th scope="col">Фамилия</th>
                <th scope="col">Пол</th>
                <th scope="col">Описание</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th scope="row">1</th>
                <td>Mark</td>
                <td>Otto</td>
                <td>M</td>
                <td>@mdo</td>
            </tr>
            <tr>
                <th scope="row">2</th>
                <td>Jacob</td>
                <td>Thornton</td>
                <td>M</td>
                <td>@fat</td>
            </tr>
            <tr>
                <th scope="row">3</th>
                <td>Larry</td>
                <td>the Bird</td>
                <td>M</td>
                <td>@twitter</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>

