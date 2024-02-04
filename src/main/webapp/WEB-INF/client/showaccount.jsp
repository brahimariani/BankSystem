<html>
<head>
    <meta charset="UTF-8">
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
            <p><strong>Compte:</strong> ${compte.getRIB()}</p>
            <p><strong>Solde Actuel:</strong> ${compte.solde}</p>
        </div>
    </div>

    <!-- Operations List Section -->
    <div class="card mt-4">
        <div class="card-header">
            <div class="row">
                <div class="col-md-9">
                    <h5>Liste des opérations</h5>
                </div>

                <div class="col-md-3">
                    <!-- Button to trigger the modal -->
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
                        Ajouter une opération
                    </button>
                    <!-- The Modal -->
                    <div class="modal" id="myModal">
                        <div class="modal-dialog">
                            <div class="modal-content">

                                <!-- Modal Header -->
                                <div class="modal-header">
                                    <h4 class="modal-title">Ajouter une nouvelle Operation</h4>
                                </div>
                                <!-- Modal Body -->
                                <form id="FormAddTransaction">
                                    <div id="loginError"  role="alert"></div>
                                    <div class="modal-body">
                                        <!-- Other form fields -->
                                        <label class="form-label" for="currentAccount">Compte actuel :</label>
                                        <input class="form-control" type="text" name="currentAccount" id="currentAccount" value="${compte.getRIB()}" readonly><br>

                                        <label class="form-label" for="transactionType">Type de transaction :</label>
                                        <select class="form-control" name="transactionType" id="transactionType" required>
                                            <option value="1">Dépôt</option>
                                            <option value="2">Retrait</option>
                                        </select>

                                        <label class="form-label" for="montant">Montant :</label>
                                        <input class="form-control" type="text" name="montant" id="montant" required><br>

                                        <label class="form-label" for="description">Description :</label>
                                        <input class="form-control" type="text" name="description" id="description" required><br>

                                    </div>
                                    <!-- Modal Footer -->
                                    <div class="modal-footer">
                                        <input class="btn btn-primary" type="submit" name="addTransaction" value="Ajouter la transaction">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
                                    </div>
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
                    <th>Date</th>
                    <th>Montant</th>
                    <th>Description</th>
                    <th>Compte</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="operation" items="${compte.operations}">
                    <tr class="${operation.getClass().simpleName eq 'Depot' ? 'table-success' : 'table-danger'}">
                        <td>${operation.date}</td>
                        <td>${operation.montant}</td>
                        <td>${operation.getDesciption()}</td>
                        <td>${operation.compte.getRIB()}</td>
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
    function addTransaction() {
        var account = $('#currentAccount').val();
        var transactionType = $('#transactionType').val();
        var montant = $('#montant').val();
        var description = $('#description').val();

        $.ajax({
            type: 'POST',
            url: '/BankSystem/client/showaccount',
            data: {
                account: account,
                transactionType: transactionType,
                montant:montant,
                description:description
            },
            success: function(response) {
                if (response === 'success') {
                    window.location.reload()
                } else if(response==='amount') {
                    $('#loginError').text('le solde est inssufisant,veuillez taper un autre montant.').addClass('alert alert-danger');
                }else if(response==='error') {
                    $('#loginError').text('Un erreur est produit , veuillez résssayer.').addClass('alert alert-danger');
                }else if (response==='epargne'){
                    $('#loginError').text('Vous ne pouvez pas effectuer un operation de retrait sur un compte d\'épargne').addClass('alert alert-danger');
                }

            },
            error: function(error) {
                console.error(error);
            }
        });
    }

    $(document).ready(function() {
        $('#FormAddTransaction').submit(function(event) {
            event.preventDefault();
            addTransaction();
        });
    });


</script>
</body>
</html>
