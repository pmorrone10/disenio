
function getNameMethodology() {
    return getElement('InputMethodologyName');
}

function getMethodologyType(){
    return getElement('sm');
}

function getIndicators() {
    return getElement('indicators');
}

function getComparator() {
    return getElement('comparator');
}

function getCompanies() {
    return getElement('companies');
}

function getTxtValue() {
    return getElement('txtValue');
}


function setupMCompanies() {
    showElement(getComparator());
    showElement(getCompanies());
    hideElement(getTxtValue());
}

function setupMOwn() {
    showElement(getComparator());
    hideElement(getCompanies());
    hideElement(getTxtValue());
}

function setupMValue() {
    showElement(getComparator());
    hideElement(getCompanies());
    showElement(getTxtValue());
}

function hideAll() {
    hideElement(getComparator());
    hideElement(getCompanies());
    hideElement(getTxtValue());
}

function setValues() {
    deleteAllOptions(getComparator());
    getComparator().add(createOptionToSelect("3","Mayor Que"));
    getComparator().add(createOptionToSelect("4","Menor Que"));
}

function setValues2() {
    deleteAllOptions(getComparator());
    getComparator().add(createOptionToSelect("1","CRECIENTE"));
    getComparator().add(createOptionToSelect("2","DESCENDENTE"));
}

function createOptionToSelect(value,text) {
    var option = document.createElement("option");
    option.text = text;
    option.value = value;
    return option;
}


$(document).ready(function() {
    $('#sm').change(function(){
        var valueSelected = $(this).find("option:selected").attr('value');
        switch(valueSelected){
            case "1":
                setupMCompanies();
                setValues();
                break;
            case "2":
                setupMOwn();
                setValues2();
                break;
            case "3":
                setupMValue();
                setValues();
                break;
            default:
                hideAll();
                break;
        }
    });
});

window.onload = function() {
    hideAll();
}


$(document).ready(function() {
    $('#sm').change(function(){
        var valueSelected = $(this).find("option:selected").attr('value');
        switch(valueSelected){
            case "1":
                setupMCompanies();
                setValues();
                break;
            case "2":
                setupMOwn();
                setValues2();
                break;
            case "3":
                setupMValue();
                setValues();
                break;
            default:
                hideAll();
                break;
        }
    });
});

function deleteMethodology(){
    $.ajax({
        url : "/methodologies/" + getValue(getNameMethodology()),
        type: "delete",
        success    : function(){
            location.reload();
        },
        error: function (respondError) {
            if(respondError.status == 412){
                showAlertError("No se pudo eliminar la metodologia seleccionada");
            }else{
                showAlertError("Ocurrio un error. Intenta nuevamente");
            }
        }
    });
}

function addMethodology() {
    $.ajax({
       url: "/methodologies",
       type: "post",
        data: getBodyPost() ,
        success    : function(){
           location.reload();
        },
        error: function (respondError) {
            if(respondError.status == 410){
                showAlertError("Verifique que haya completado todos los campos");
            }else{
                showAlertError("Ocurrio un error. Intenta nuevamente");
            }
        }
    });
}

function getBodyPost(){
    return {name:getValue(getNameMethodology()), methodology:getValue(getMethodologyType()),
            companies:getValue(getCompanies()),comparator:getValue(getComparator()),txtValue:getValue(getTxtValue()),
            indicators:getValue(getIndicators())}
}
