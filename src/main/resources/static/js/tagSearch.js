function moveLeft() {

}

function moveRight() {

}

function moveToPage(page) {
    fetch('/api/search/tag/data?tag=Basic&p=' + page, {method: 'GET'}).then(function (response) {
        return response.json();
    }).then(function (j) {
        render(j)
    })
}

function render(json) {
    var resultsTable = document.querySelector("#results");
    resultsTable.innerHTML = "";
    json.forEach(function (entry) {
        var start = '<td><a href="/p/"' + entry["stringId"] + '>' + entry["name"] + '</a></td>';
        resultsTable.insertAdjacentHTML("beforeend", start)
    })
}