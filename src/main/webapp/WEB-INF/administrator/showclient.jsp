<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des clients</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>
<%@include file="../menu.jsp"%>
<div class="container mt-5">
    <!-- Client Information Section -->
    <div class="card">
        <div class="card-header">
            <h5>Client Information</h5>
        </div>
        <div class="card-body">
            <p><strong>Name:</strong> ${client.nom} ${client.prenom}</p>
            <p><strong>Phone Number:</strong> ${client.numTel}</p>
            <p><strong>CIN:</strong> ${client.cin}</p>
        </div>
    </div>

    <!-- Accounts Management Section -->
    <div class="card mt-4">
        <div class="card-header">
            <div class="row">
                <div class="col-md-9">
                    <h5>Gestion des comptes</h5>
                </div>
                <div class="col-md-3">
                    <!-- Button to trigger the modal -->
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                        Ajouter un compte
                    </button>
                    <!-- The Modal -->
                    <div class="modal" id="myModal">
                        <div class="modal-dialog">
                            <div class="modal-content">

                                <!-- Modal Header -->
                                <div class="modal-header">
                                    <h4 class="modal-title">Ajouter un nouveau compte</h4>
                                </div>
                                <!-- Modal Body -->
                                <form action="/BankSystem/admin/addaccount" method="post">
                                    <!-- Common Account Information -->
                                    <div class="mb-3">
                                        <label for="accountType" class="form-label">Type de compte</label>
                                        <select class="form-select" id="accountType" name="accountType" required>
                                            <option selected>Selectionnez un type de compte</option>
                                            <option value="1">Compte courant</option>
                                            <option value="2">Compte d'epargne</option>
                                        </select>
                                    </div>
                                    <!-- Specific Fields for CptCourant -->
                                    <div id="cptCourantFields" style="display: none">
                                        <div class="mb-3">
                                            <label for="decouvert" class="form-label">Decouvert</label>
                                            <input type="text" class="form-control" id="decouvert" name="decouvert">
                                        </div>
                                    </div>

                                    <!-- Specific Fields for CptEpargne -->
                                    <div id="cptEpargneFields" style="display: none">
                                        <div class="mb-3">
                                            <label for="tauxInteret" class="form-label">Taux d'Intérêt</label>
                                            <input type="text" class="form-control" id="tauxInteret" name="tauxInteret">
                                        </div>
                                    </div>

                                    <input type="hidden" name="clientCIN" value="${client.cin}">

                                    <button type="submit" class="btn btn-primary">Ajouter le compte</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-body">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Numero de compte</th>
                    <th>Rib</th>
                    <th>Solde</th>
                    <th>Type de compte</th>
                    <th>Decouvert</th>
                    <th>Taux d'interet</th>
                    <th>date de création</th>
                    <th>Gérer</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="account" items="${client.comptes}">
                    <tr>
                        <td>${account.getNum()}</td>
                        <td>${account.getRIB()}</td>

                        <td>${account.getSolde()}</td>
                        <c:if test="${account.getClass().simpleName eq 'CptCourant'}">
                            <td>Compte courant</td>
                            <td>${account.getDecouvert()}</td>
                            <td></td>
                        </c:if>
                        <c:if test="${account.getClass().simpleName eq 'CptEpargne'}">
                            <td>Compte d'épargne</td>
                            <td></td>
                            <td>${account.getTauxInteret()}</td>
                        </c:if>
                        <td>${account.getDateCreation()}</td>
                        <td>
                            <button onclick="closeAccount('${account.getRIB()}')" class='btn btn-danger'>Fermer le compte</button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript" src="/BankSystem/js/main.js"></script>
<script>

    $(document).ready(function () {
        $("#accountType").change(function () {
            var selectedType = $(this).val();

            if (selectedType == 1) { // CptCourant
                $("#cptCourantFields").show();
                $("#cptEpargneFields").hide();
            } else if (selectedType == 2) { // CptEpargne
                $("#cptCourantFields").hide();
                $("#cptEpargneFields").show();
            } else {
                $("#cptCourantFields").hide();
                $("#cptEpargneFields").hide();
            }
        });
    });

    function closeAccount(rib) {
        var confirmDelete = confirm("est vous sure de fermer le compte?");
        if (confirmDelete) {
            var deleteURL = '/BankSystem/admin/closeaccount?rib=' + rib;
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


</body>
</html>
