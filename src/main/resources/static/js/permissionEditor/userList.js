const searchInput = document.querySelector('#userSearchInput');
let current = 1;
let max;
const usersPagination = new UsersPagination();

updateUserListFilters();

//TODO: optimize after this gets browser support https://developer.mozilla.org/en-US/docs/Web/API/AbortController/abort
function updateUserListFilters() {
    current = 1;
    getPageCount()
        .then(usersPagination.updatePagination);

}


function getPageCount() {
    return new Promise(function (resolve) {
        resolve(
            fetch('/admin/rest/countUsers?search=' + searchInput.value, {
                method: 'GET',
                credentials: 'same-origin'
            }).then(function (response) {
                return response.text();
            }).then(function (responseText) {
                let responseNumber = parseInt(responseText);
                max = responseNumber;
                return responseText;
            }))
    })
}

function UsersPagination() {
    const paginationNode = document.querySelector("#usersPagination");
    const rightArrow = document.querySelector('#rightUsersArrow');
    const resultsTable = document.querySelector("#userTable");
    let paginationNumbers = [];

    this.updatePagination = function (pages) {
        paginationNumbers.forEach(function (node) {
            node.remove()
        });
        for (let i = 1; i <= pages; i++) {
            let inside = document.createElement('a');
            inside.setAttribute('onclick', 'usersPagination.moveToPage(this)');
            inside.setAttribute('page', i);
            inside.innerText = i;

            let number = document.createElement('li');
            number.classList.add(i === 1 ? 'active' : 'waves-effect');
            number.appendChild(inside);
            paginationNumbers.push(number);
            paginationNode.insertBefore(number, rightArrow)
        }
    };

    this.moveLeft = function () {
        if (current > 1) {
            paginationNode.querySelector('[page="' + current + '"]').setAttribute('class', 'waves-effect');
            paginationNode.querySelector('[page="' + (current - 1) + '"]').setAttribute('class', 'active');
            current--;
            download(current)
        }
    };

    this.moveRight = function () {
        if (current < max) {
            paginationNode.querySelector('[page="' + current + '"]').setAttribute('class', 'waves-effect');
            paginationNode.querySelector('[page="' + (current + 1) + '"]').setAttribute('class', 'active');
            current++;
            download(current)
        }
    };

    this.moveToPage = function (node) {
        let page = node.getAttribute('page');
        paginationNode.querySelector('[page="' + current + '"]').setAttribute('class', 'waves-effect');
        node.setAttribute('class', 'active');
        current = page;
        download(current)
    };

    function download(page) {
        fetch('/admin/rest/userData?page=' + page + '&search=' + searchInput.value, {
            method: 'GET',
            credentials: 'same-origin'
        }).then(function (response) {
            return response.json();
        }).then(function (j) {
            render(j)
        })
    }

    function render(json) {
        resultsTable.innerHTML = "";
        json.forEach(writeLine)
    }

    function writeLine(entry) {
        let line = document.createElement('tr');
        let userSpan = document.createElement('span');
        userSpan.innerText = entry.username;
        let usernameTd = document.createElement('td');
        usernameTd.classList.add('col');
        usernameTd.classList.add('m7');
        usernameTd.appendChild(userSpan);

        line.appendChild(usernameTd);
        resultsTable.appendChild(line);

        // let start = '<td><a href="/p/' + entry.stringId + '">' + entry.name + '</a><div class="tags">';
        // entry.tags.forEach(function (tag) {
        //     start += '<a href="/search/tag?tag=' + tag + '"><div class="chip">' + tag + '</div><a/>'
        // });
        // start += '<div/><td/>';
        // resultsTable.insertAdjacentHTML("beforeend", start)
    }
}