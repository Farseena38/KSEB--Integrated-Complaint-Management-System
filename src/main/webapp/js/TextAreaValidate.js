/**
 * 
 */
function validate() {

	var complaint = document.forms["complaint-form"]["complaint"].value;
	if (complaint == "" || complaint == null) {
		alert("Please Enter the Complaint");
		document.forms["complaint-form"]["complaint"].focus();
		return false;
	}
	
	document.form.submit();
}