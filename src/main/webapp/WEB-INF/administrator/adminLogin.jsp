<html>
<head>
    <meta charset="UTF-8">
    <title>Connexion Administrateur</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome CSS for icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <style>
        body {
            background-color: #f8f9fa;
        }

        .login-container {
            max-width: 400px;
            margin: auto;
            margin-top: 100px;
        }

        .card {
            border: none;
        }

        .password-group {
            position: relative;
        }

        .password-icon {
            position: absolute;
            top: 50%;
            right: 10px;
            transform: translateY(-50%);
            cursor: pointer;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="login-container">
        <div class="card">
            <div class="card-body">
                <h3 class="card-title text-center mb-4">Connexion d'administrateur</h3>
                <form action="/BankSystem/admin_login" method="post">
                    <div class="mb-3">
                        <label for="username" class="form-label">Nom</label>
                        <input type="text" class="form-control" id="username" placeholder="Votre nom" name="username" required>
                    </div>
                    <div class="mb-3 password-group">
                        <label for="password" class="form-label">Mot de passe</label>
                        <input type="password" class="form-control" id="password" placeholder="Votre mot de passe" name="password" required>
                        <i class="password-icon fas fa-eye" onclick="togglePassword()"></i>
                    </div>
                    <button type="submit" class="btn btn-primary w-100">Connexion</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS (optional) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Font Awesome JS for icons -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/js/all.min.js"></script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

<script>
    function togglePassword() {
        var passwordInput = document.getElementById('password');
        var passwordIcon = document.querySelector('.password-icon');

        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            passwordIcon.classList.remove('fa-eye');
            passwordIcon.classList.add('fa-eye-slash');
        } else {
            passwordInput.type = 'password';
            passwordIcon.classList.remove('fa-eye-slash');
            passwordIcon.classList.add('fa-eye');
        }
    }
</script>
</body>
</html>
