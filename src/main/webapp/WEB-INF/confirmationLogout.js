document.getElementById('logoutButton').addEventListener('click', function(event) {
    event.preventDefault();
    document.getElementById('confirmationLogoutDialog').style.display = 'flex';
});

document.getElementById('cancelLogoutButton').addEventListener('click', function() {
    document.getElementById('confirmationLogoutDialog').style.display = 'none';
});

document.getElementById('confirmLogoutButton').addEventListener('click', function() {
    document.getElementById('logoutForm').submit();
});