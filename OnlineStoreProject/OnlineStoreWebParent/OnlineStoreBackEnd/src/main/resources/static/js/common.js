



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

