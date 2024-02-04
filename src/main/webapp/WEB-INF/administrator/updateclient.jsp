<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des clients</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="/BankSystem/js/main.js"></script>
</head>
<body>
<%@include file="../menu.jsp"%>
<div class="container">
    <h2>Update Client</h2>

    <form action="/BankSystem/admin/updateclient" method="post">
        <div class="mb-3">
            <label for="nomClient" class="form-label">Nom :</label>
            <input type="text" id="nomClient" name="nomClient" class="form-control" value="<c:out value='${client.getNom()}'/>" required>
        </div>

        <div class="mb-3">
            <label for="prenomClient" class="form-label">Prénom :</label>
            <input type="text" id="prenomClient" name="prenomClient" class="form-control" value="<c:out value='${client.getPrenom()}'/>" required>
        </div>

        <div class="mb-3">
            <label for="cinClient" class="form-label">CIN :</label>
            <input type="text" id="cinClient" name="cinClient" class="form-control" value="<c:out value='${client.getCin()}'/>" readonly>
        </div>

        <div class="mb-3">
            <label for="numTelClient" class="form-label">Numéro de téléphone :</label>
            <input type="text" id="numTelClient" name="numTelClient" class="form-control" value="<c:out value='${client.getNumTel()}'/>" required>
        </div>

        <input type="hidden" name="formType" value="updateClient">
        <button type="submit" class="btn btn-primary">Mettre à jour le client</button>
    </form>

</div>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
