

function showAccount(rib) {

    $.ajax({
        type: 'GET',
        url: '/BankSystem/client/showaccount',
        data: {
            rib: rib
        },
        error: function(error) {
            console.error(error);
        }
    });

}
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
function loginClient() {
    var username = $('#username').val();
    var password = $('#password').val();

    $.ajax({
        type: 'POST',
        url: '/BankSystem/client_login',
        data: {
            username: username,
            password: password
        },
        success: function(response) {
            if (response === 'success') {
                window.location.href = '/BankSystem/client';
            } else {
                $('#loginError').text('données invalides , veuillez réssayer..').addClass('alert alert-danger');
            }
        },
        error: function(error) {
            console.error(error);
        }
    });
}

