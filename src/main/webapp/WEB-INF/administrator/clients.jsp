<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des clients</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="/BankSystem/js/main.js"></script>
</head>
<body>
<%@include file="../menu.jsp"%>
<div class="container-fluid mt-5">
<div class="row">
    <div class="col-md-9 ">
        <form action="/BankSystem/admin" method="post" style="display: flex; align-items: center;">
                <input class="form-control" type="text" placeholder="Rechercher"  name="searchCleint" id="searchCleint" required>
                <input class="btn btn-warning" type="submit" name="searchClient" value="Rechercher">
        </form>
    </div>
    <div class="col-md-3">
        <!-- Button to trigger the modal -->
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
            Ajouter Client
        </button>
        <!-- The Modal -->
        <div class="modal" id="myModal">
            <div class="modal-dialog">
                <div class="modal-content">

                    <!-- Modal Header -->
                    <div class="modal-header">
                        <h4 class="modal-title">Ajouter un nouveau client</h4>
                    </div>
                    <!-- Modal Body -->
                    <form action="/BankSystem/admin" method="post">

                    <div class="modal-body">
                            <label class="form-label" for="nomClient">Nom :</label>
                            <input class="form-control" type="text"  name="nomClient" id="nomClient" required><br>

                            <label class="form-label" for="prenomClient">Prénom :</label>
                            <input class="form-control" type="text" name="prenomClient" id="prenomClient" required><br>

                            <label class="form-label" for="cinClient">CIN :</label>
                            <input class="form-control" type="text" name="cinClient" id="cinClient" required><br>

                            <label class="form-label" for="numTelClient">Numéro de téléphone :</label>
                            <input class="form-control" type="text" name="numTelClient" id="numTelClient" required><br>

                            <label class="form-label" for="passClient">Mot de passe :</label>
                            <input class="form-control" type="password" name="passClient" id="passClient" required><br>
                            <input type="hidden" name="formType" value="addClient">

                    </div>
                    <!-- Modal Footer -->
                    <div class="modal-footer">
                        <input class="btn btn-primary" type="submit" name="addClient" value="Ajouter le client">
                        <button type="button" class="btn btn-secondary"  data-dismiss="modal">Annuler</button>
                    </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
<div class=" card col-md-12">
    <h3 class=" card-header mb-4">Liste des clients</h3>
    <div class="card-body">
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>Code Client</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>CIN</th>
                <th>Numéro de téléphone</th>
            </tr>
            </thead>
            <tbody>
                <c:forEach var="client" items="${clients}">
                    <tr>
                        <td><c:out value="${client.getCode()}"/></td>
                        <td><c:out value="${client.getNom()}"/></td>
                        <td><c:out value="${client.getPrenom()}"/></td>
                        <td><c:out value="${client.getCin()}"/></td>
                        <td><c:out value="${client.getNumTel()}"/></td>
                        <td>
                            <a href="/BankSystem/admin/showclient?client=${client.getCin()}" class="btn btn-sm btn-primary" >Afficher</a>
                            <a href="/BankSystem/admin/updateclient?client=${client.getCin()}" class="btn btn-sm btn-primary" >Modifier</a>
                            <button class="btn btn-sm btn-danger" id="deleteClientButton" onclick="deleteClient('${client.getCin()}')">Supprimer</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</div>
</div>
<script>
    var $j = jQuery.noConflict();
    function deleteClient(cin) {
        var confirmDelete = confirm("est vous sure de supprimer le client?");
        if (confirmDelete) {
            var deleteURL = '/BankSystem/admin/deleteclient?client=' + cin;
            fetch(deleteURL, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                },
            })
                .then(response => {
                })
                .then(data => {
                    location.reload();
                })
                .catch(error => {
                    alert('Un erreur est produit: ' + error.message);
                });
        }
    }
</script>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
