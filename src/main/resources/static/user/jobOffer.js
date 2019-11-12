$(document).ready(function() {
    let id = $(location).attr("search");
    console.log($(location)+ " "+ id);
    fetch("http://localhost:8080/jobs"+id)
        .then(response => {
            return response.json()
        })
        .then(data => {
               console.log(data[0].title);
               $("#title").append(data[0].title);
               $("#company").append(data[0].company);
               $("#description").append( data[0].description);
               $("#salary").append(data[0].salary);
        })
});