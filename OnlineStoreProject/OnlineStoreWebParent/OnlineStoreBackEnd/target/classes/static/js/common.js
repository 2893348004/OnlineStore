



  $(document).ready(function() {
        $("#buttonCancelAccount").on("click", function() {
            window.location = "/OnlineStoreAdmin/"
             });
             });



$(document).ready(function() {
$("#buttonCancel").on("click", function() {
            window.location = "/OnlineStoreAdmin/users/"
             });
 });

 $(document).ready(function() {
 $("#buttonCancelCategories").on("click", function() {
             window.location = "/OnlineStoreAdmin/categories/"
              });
  });


   $(document).ready(function() {
   $("#buttonCancelCustomers").on("click", function() {
               window.location = "/OnlineStoreAdmin/customers/"
                });
    });

$(document).ready(function() {
 $("#logoutlink").on("click", function(e) {
        e.preventDefault();
        document.logoutForm.submit();
            });
 });







      $(document).ready(function() {
         $("#fileImage").change(function(){
             fileSize = this.files[0].size;


             if(fileSize > 1048576){
                 this.setCustomValidity("Image must be below 1 MB");
                 this.reportValidity();
             }else{
                 this.setCustomValidity("");
                 showImageThumbNail(this);
             }


             });

         });

          $(document).ready(function() {
                  $("#extraImage").change(function(){
                      fileSize = this.files[0].size;


                      if(fileSize > 1048576){
                          this.setCustomValidity("Image must be below 1 MB");
                          this.reportValidity();
                      }else{
                          this.setCustomValidity("");
                          showImageThumbNail(this);
                      }


                      });

                  });




$( "#shiplenght" ).keydown(function(e) {
  if(!((e.keyCode > 95 && e.keyCode < 106)
     || (e.keyCode > 47 && e.keyCode < 58)
     || e.keyCode == 8)) {
     return false;
     }
});

$( "#shipwidth" ).keydown(function(e) {
   if(!((e.keyCode > 95 && e.keyCode < 106)
      || (e.keyCode > 47 && e.keyCode < 58)
      || e.keyCode == 8)) {
      return false;
      }
});

$( "#shipheightt" ).keydown(function(e) {
   if(!((e.keyCode > 95 && e.keyCode < 106)
      || (e.keyCode > 47 && e.keyCode < 58)
      || e.keyCode == 8)) {
      return false;
      }
});

$( "#shipweight" ).keydown(function(e) {
   if(!((e.keyCode > 95 && e.keyCode < 106)
      || (e.keyCode > 47 && e.keyCode < 58)
      || e.keyCode == 8)) {
      return false;
      }
});

$( "#formdiscount" ).keydown(function(e) {
  if(!((e.keyCode > 95 && e.keyCode < 106)
     || (e.keyCode > 47 && e.keyCode < 58)
     || e.keyCode == 8)) {
     return false;
     }
});

$( "#formprice" ).keydown(function(e) {
  if(!((e.keyCode > 95 && e.keyCode < 106)
     || (e.keyCode > 47 && e.keyCode < 58)
     || e.keyCode == 8)) {
     return false;
     }
});

$( "#formcost" ).keydown(function(e) {
   if(!((e.keyCode > 95 && e.keyCode < 106)
      || (e.keyCode > 47 && e.keyCode < 58)
      || e.keyCode == 8)) {
      return false;
      }
});



/*

var shiplenght = document.getElementById('shiplenght');

    // Listen for input event on numInput.
    shiplenght.onkeydown = function(e) {
    if(!((e.keyCode > 95 && e.keyCode < 106)
    || (e.keyCode > 47 && e.keyCode < 58)
    || e.keyCode == 8)) {
    return false;
    }
    }

    var shipwidth = document.getElementById('shipwidth');

    // Listen for input event on numInput.
    shipwidth.onkeydown = function(e) {
    if(!((e.keyCode > 95 && e.keyCode < 106)
    || (e.keyCode > 47 && e.keyCode < 58)
    || e.keyCode == 8)) {
    return false;
    }
    }



    var shipheightt = document.getElementById('shipheightt');

    // Listen for input event on numInput.
    shipheightt.onkeydown = function(e) {
    if(!((e.keyCode > 95 && e.keyCode < 106)
    || (e.keyCode > 47 && e.keyCode < 58)
    || e.keyCode == 8)) {
    return false;
    }
    }

    var shipweight = document.getElementById('shipweight');

    // Listen for input event on numInput.
    shipweight.onkeydown = function(e) {
    if(!((e.keyCode > 95 && e.keyCode < 106)
    || (e.keyCode > 47 && e.keyCode < 58)
    || e.keyCode == 8)) {
    return false;
    }
    }

    var formdiscount = document.getElementById('formdiscount');

        // Listen for input event on numInput.
        formdiscount.onkeydown = function(e) {
        if(!((e.keyCode > 95 && e.keyCode < 106)
        || (e.keyCode > 47 && e.keyCode < 58)
        || e.keyCode == 8)) {
        return false;
        }
        }


        var formprice = document.getElementById('formprice');

        // Listen for input event on numInput.
        formprice.onkeydown = function(e) {
        if(!((e.keyCode > 95 && e.keyCode < 106)
        || (e.keyCode > 47 && e.keyCode < 58)
        || e.keyCode == 8)) {
        return false;
        }
        }



        var formcost = document.getElementById('formcost');

        // Listen for input event on numInput.
        formcost.onkeydown = function(e) {
        if(!((e.keyCode > 95 && e.keyCode < 106)
        || (e.keyCode > 47 && e.keyCode < 58)
        || e.keyCode == 8)) {
        return false;
        }
        }
*/


         function showImageThumbNail(fileInput){
                 var file = fileInput.files[0];
                 var reader = new FileReader();
                 reader.onload = function(e) {
                     $("#thumbnail").attr("src", e.target.result);
                 };
                 reader.readAsDataURL(file);
         }




         function checkUniquenessEmail(form) {
             var returnToCheckEmail = "/OnlineStoreAdmin/users/check_email";

             url = returnToCheckEmail;
             userEmail = $("#email").val();
             userId = $("#id").val();
             csrfValue = $("input[name='_csrf']").val();
             params = {id:userId ,email: userEmail, _csrf: csrfValue};

             $.post(url, params, function(response){
                 if(response == "OK"){
                  form.submit();
                 }else if(response == "Duplicated"){
                 showModalDialog("Warning", "This email is already taken by another user: " + userEmail );
                 }else{
                 showModalDialog("Error", "Unknown response from server");
                 }
             });
             return false;
         }




          function checkUniquenessCategory(form) {
                      var returnToCheckCategory = "/OnlineStoreAdmin/categories/check_category";

                      url = returnToCheckCategory;
                      name = $("#name").val();
                      Id = $("#id").val();
                      csrfValue = $("input[name='_csrf']").val();
                      params = {id:Id ,name: name, _csrf: csrfValue};

                      $.post(url, params, function(response){
                          if(response == "OK"){
                           form.submit();
                          }else if(response == "Duplicated"){
                          showModalDialog("Warning", "This category is already in use : " + name );
                          }else{
                          showModalDialog("Error", "Unknown response from server");
                          }
                      });
                      return false;
                  }










         function showModalDialog(title,message){
         $("#modalTitle").text(title);
         $("#modalBody").text(message);
         $("#modalDialog").modal();
         }

         function showErrorModal(message){
         showModalDialog("Error", message)
         }

         function showWarningModal(message){
         showModalDialog("Warning", message)
         }

