function validateForm() {
    var okay = true;
     var _validFileExtensions = [".jpg", ".jpeg", ".png"];

    var title = $("#title").val();
    var writer = $("#writer").val();
    var category = $("#category").val();
    var releaseDate = $("#releaseDate").val();
    var price = $("#price").val();
    var image = $("#file").val();
  
    if (image.length>0) {
        var blnValid = false;
        for (var j = 0; j < _validFileExtensions.length; j++) {
            var sCurExtension = _validFileExtensions[j];
            if (image.substr(image.length - sCurExtension.length, sCurExtension.length).toLowerCase() == sCurExtension.toLowerCase()) {
                blnValid = true;
                break;
            }
        }
        if (!blnValid) {
            $("#file_error").html(wrongFile);
            okay=false;
        } else {
            $("#file_error").html("");
        }
    }
    
    if (title == "" || title == null) {
        $("#title_error").html(titleEmpty);
        okay = false;
    } else
        $("#title_error").html("");
    
    if (writer == "" || writer == null) {
        $("#writer_error").html(writerEmpty);
        okay = false;
    } else
        $("#writer_error").html("");
    
    if (category == "" || category == null) {
        $("#category_error").html(categoryEmpty);
        okay = false;
    } else
        $("#category_error").html("");
    
    if (releaseDate == "" || releaseDate == null) {
        $("#release_date_error").html(release_date_empty);
        okay = false;
    } else
        $("#release_date_error").html("");
    
     if (price == "" || price == null) {
        $("#price_error").html(priceEmpty);
        okay = false;
    } else
        $("#price_error").html("");

    if(okay){
       $("#newBookIdentifier").submit();
    }
}
