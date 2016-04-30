function validateForm() {
    var okay = true;

    var pv1 = $("#password1").val();
    var pv2 = $("#password2").val();
    console.log(pv1);
    console.log(pv2);
    
     
    
    if (pv1 == "" || pv1 == null) {
        $("#pv1_error").html(pv1Empty);
        okay = false;
        console.log(okay);
    } else
        $("#pv1_error").html("");
    
    if (pv2 == "" || pv2 == null) {
        $("#pv1_error").html(pv1Empty);
        okay = false;
        console.log(okay);
    } else
        $("#pv2_error").html("");
    
    if (pv1 != pv2 && pv1!= "" && pv2 != "") {
         $("#password_error").html(wrongPassword);
         
        okay = false;
    } else
        $("#password_error").html("");
    
    console.log(okay);
    if(okay){
         $("#password_error").html("");
         $("#passwordIdentifier").submit();
    }
}
