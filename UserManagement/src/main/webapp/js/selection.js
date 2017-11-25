window.onload = function () {
    var tableRows = document.querySelectorAll("#userTable tbody tr");
    for (var i = 0; i < tableRows.length; i++) {
        var element = tableRows[i];
        element["onclick"] = createClassTrigger(element, "select-active",
            mapHrefs);
    }
};

function mapHrefs() {
    var element = document.querySelector(".select-active td:first-child");
    if (element != null) {
        var id = element.innerText;
        document.getElementById("editBtn").setAttribute("href", "edit?id=" + id);
        document.getElementById("detailsBtn").setAttribute("href", "details?id=" + id);
        document.getElementById("deleteBtn").setAttribute("href", "delete?id=" + id);
    }
}

function createClassTrigger(element, cssClass, fxExec) {
    cssClass = " " + cssClass.trim() + " ";
    if (fxExec != undefined) {
        var fx = fxExec;
    } else {
        fx = function () {
        };
    }
    return function () {
        if (element.className.indexOf(cssClass) !== -1) {
            element.className = element.className.replace(cssClass, " ");
        } else {

            var selectedRows = document.querySelectorAll(".select-active");
            for (var index = 0; index < selectedRows.length; index++) {
                selectedRows[index].className = selectedRows[index].className.replace(cssClass, " ");
            }
            element.className = element.className.concat(cssClass);
        }
        fx();
    }
}