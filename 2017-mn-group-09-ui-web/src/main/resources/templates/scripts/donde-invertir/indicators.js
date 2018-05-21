
function getNameIndicator() {
    return getElement('InputIndicatorName');
}

function getExpression(){
    return getElement('InputIndicatorForm');
}

function cleanFields() {
    getNameIndicator().value = "";
    getExpression().value = "";
}

function addIndicator() {
    $.ajax({
        url: "/indicators",
        type: "post",
        data: getBodyPost() ,
        success    : function(){
            location.reload();
        },
        error: function (respondError) {
            if(respondError.status == 410){
                showAlertError("Verifique que haya completado todos los campos");
            }else if(respondError.status == 411){
                showAlertError("Ya existe este indicador");
            }else{
                showAlertError("Ocurrio un error. Intenta nuevamente");
            }
        }
    });
}

function deleteIndicator(){

    $.ajax({
        url : "/indicators/" + getValue(getNameIndicator()),
        type: "delete",
        success    : function(){
            location.reload();
        },
        error: function (respondError) {
            if(respondError.status == 412){
                showAlertError("No se pudo eliminar el indicador seleccionado");
            }else if(respondError.status == 413){
                showAlertError("No se puede borrar el indicador ya que esta en uso en alguna metodologia");
            }else{
                showAlertError("Ocurrio un error. Intenta nuevamente");
            }
        }
    });
}

function getBodyPost() {
    return {name:getValue(getNameIndicator()), expression:getValue(getExpression())}
}