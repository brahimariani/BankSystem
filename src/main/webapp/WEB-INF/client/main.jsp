<html>
<head>
    <title>Page d'acceuil</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
    <script src="/BankSystem/js/main.js"></script>
</head>
<body>
<%@include file="../menu.jsp"%>
<div class="container mt-5">
    <!-- Client Information Section -->
    <div class="card">
        <div class="card-header">
            <h5>Informations de client</h5>
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
                <div class="col-md-12">
                    <h5>Comptes</h5>
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
                            <a  href="/BankSystem/client/showaccount?rib=${account.getRIB()}" class='btn btn-danger '>Afficher le détaille</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    var $j = jQuery.noConflict();
    $(document).ready(function() {
        $(".showaccount").click(function(event) {
            event.preventDefault();

            // Get the rib from the data attribute
            var rib = $(this).data("rib");
            alert(rib)
            // Your AJAX logic here
            showAccount(rib);

        });
    });
</script>
</body>
</html>
