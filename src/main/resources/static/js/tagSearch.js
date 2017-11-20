var pagination = document.querySelector("#pagination");
var resultsTable = document.querySelector("#results");
var current = 1;
var max = (pagination.childNodes.length - 5) / 2;

function moveLeft() {
    if (current > 1) {
        pagination.querySelector('[name=e' + current + ']').setAttribute('class', 'waves-effect');
        pagination.querySelector('[name=e' + (current - 1) + ']').setAttribute('class', 'active');
        current--;
        download(current)
    }
}

function moveRight() {
    if (current < max) {
        pagination.querySelector('[name=e' + current + ']').setAttribute('class', 'waves-effect');
        pagination.querySelector('[name=e' + (current + 1) + ']').setAttribute('class', 'active');
        current++;
        download(current)
    }
}

function moveToPage(page) {
    pagination.querySelector('[name=e' + current + ']').setAttribute('class', 'waves-effect');
    pagination.querySelector('[name=e' + page + ']').setAttribute('class', 'active');
    current = page;
    download(current)
}

function download(page) {
    fetch('/search/tag/data?tag=Basic&p=' + page, {method: 'GET', credentials: 'same-origin'}).then(function (response) {
        return response.json();
    }).then(function (j) {
        render(j)
    })
}

function render(json) {
    resultsTable.innerHTML = "";
    json.forEach(function (entry) {
        var start = '<td><a href="/p/' + entry.stringId + '">' + entry.name + '</a>';
        entry.tags.forEach(function (tag) {
            start += '<a href="/search/tag?tag=' + tag + '"><div class="chip">' + tag + '</div><a/>'
        });
        start += '<td/>';
        resultsTable.insertAdjacentHTML("beforeend", start)
    })
}