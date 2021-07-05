

$(document).ready(function() {
    $(".linkMinus").on("click", function(evt) {
        evt.preventDefault();

        productId = $(this).attr("pid");
        quantityInput = $("#quantity" + productId);
        newQuantity =  parseInt(quantityInput.val()) - 1;

        if(newQuantity > 0){
            quantityInput.val(newQuantity);
        }else{
            showModalDialog("Minimum quantity is 1")
        }


    })


 $(".linkPlus").on("click", function(evt) {
        evt.preventDefault();

       productId = $(this).attr("pid");
       quantityInput = $("#quantity" + productId);
       newQuantity =  parseInt(quantityInput.val()) + 1 ;

         if(newQuantity <= 10){
                    quantityInput.val(newQuantity);
                }else{
                    showModalDialog("Warning","Max quantity is 10")
                }



    })


})


         function showModalDialog(title,message){
         $("#modalTitle").text(title);
         $("#modalBody").text(message);
         $("#modalDialog").modal();
         }








