$(document).ready(function() {
    $.ajax({
        url: "http://localhost:8080/jobs"
    }).then(function(data) {
            $.each(data, function () {
                let offerUrl = "http://localhost:8080/user/offer?id="+this['id'];
                $('#offers > tbody').append("<tr>" +
                    "<td> "+ this['title'] +"</td>" +
                    "<td>"+ "date and date to go"+"</td>"+
                    "</tr>"+"<tr class='table-info'>" +
                    "<td> "+ this['company']+"</td>" +
                    "<td class='bg-success'>"+ this['salary'] +"</td>"+ "</tr>" +
                    "<tr><td colspan='2'><a class='btn btn-info'>Szczegóły oferty</a></td></tr>"+
                    "<tr class='table-dark'><td colspan='2'></td></tr>"
                );
                $("a").last().attr("href", offerUrl);
            });
    });
});