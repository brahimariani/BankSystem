

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="#">BankSystem</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item active">
                    <a class="nav-link" href="/BankSystem/client">Client</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/BankSystem/admin">Administrateur</a>
                </li>
                <!-- Add more navigation links as needed -->
                <!-- Add Disconnect button -->
                <c:if test="${(not empty sessionScope.admin) or (not empty sessionScope.client)}">
                    <li class="nav-item">
                        <button class="btn btn-outline-danger" onclick="disconnect()">Déconnexion</button>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
<script>
    function disconnect() {
        // Use Fetch API to send an AJAX request to the server for session deletion
        fetch('/BankSystem/logout', {
            method: 'POST',
            credentials: 'same-origin',  // Include cookies in the request
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then(response => {
                if (response.ok) {
                    // Redirect to the login page or perform other actions after successful logout
                    window.location.href = '/BankSystem/client';
                } else {
                    // Handle errors or show appropriate messages
                    console.error('Logout failed');
                }
            })
            .catch(error => {
                console.error('Error during logout:', error);
            });
    }
</script>
<!--<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <h3 class="navbar-brand">BankSystem</h3>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="/BankSystem/client">Clients</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/BankSystem/admin">Administrateurs</a>
                </li>
            </ul>
        </div>
    </div>
</nav>-->
