/**
 * 
 */
function validateForm() {

	var userName = document.forms["login-form"]["uname"].value;
	var password = document.forms["login-form"]["upass"].vaue;
	if (userName == "" || userName == null) {
		alert("Please Enter the UserName");
		document.forms["login-form"]["uname"].focus();
		return false;
	}
	if (userName.length < 5) {
		alert("Username must be at least of 6 characters.");
		document.forms["login-form"]["uname"].focus();
		return false;
	}
	if (password == " ") {
		alert("Please enter your password.");
		document.forms["login-form"]["upass"].focus();
		return false;
	}
	if (password.length < 2) {
		alert("Password must contain at least of 8 digits");
		document.forms["login-form"]["upass"].focus();
		return false;
	}
	document.form.submit();
}