const searchInput = document.querySelector('#userSearchInput');
let current = 1;
let max;
const usersPagination = new UsersPagination();

updateUserListFilters();

//TODO: optimize after this gets browser support https://developer.mozilla.org/en-US/docs/Web/API/AbortController/abort
function updateUserListFilters() {
    current = 1;
    getPageCount()
        .then(usersPagination.updatePagination)
        .then(function () {
            usersPagination.moveToBeginning();
        });

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
            inside.setAttribute('onclick', 'usersPagination.moveToPage(this.parentNode)');
            inside.innerText = i;

            let number = document.createElement('li');
            number.classList.add(i === 1 ? 'active' : 'waves-effect');
            number.appendChild(inside);
            number.setAttribute('page', i);
            paginationNumbers.push(number);
            paginationNode.insertBefore(number, rightArrow)
        }
    };

    this.moveLeft = function () {
        if (current > 1) {
            let last = paginationNode.querySelector('[page="' + current + '"]');
            current--;
            let currentNumberNode = paginationNode.querySelector('[page="' + current + '"]');
            setEffects(last, currentNumberNode);
            download(current)
        }
    };

    this.moveRight = function () {
        if (current < max) {
            let last = paginationNode.querySelector('[page="' + current + '"]');
            current++;
            let currentNumberNode = paginationNode.querySelector('[page="' + current + '"]');
            setEffects(last, currentNumberNode);
            download(current)
        }
    };


    this.moveToPage = function (node) {
        let page = node.getAttribute('page');
        let last = paginationNode.querySelector('[page="' + current + '"]');
        setEffects(last, node);
        current = page;
        download(current)
    };

    function setEffects(last, current) {
        last.classList.remove('active');
        last.classList.add('waves-effect');
        current.classList.add('active');
        current.classList.remove('waves-effect');
    }

    this.moveToBeginning = function () {
        this.moveToPage(paginationNode.querySelector('[page="1"]'));
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
        usernameTd.classList.add('m12');
        usernameTd.appendChild(userSpan);

        line.appendChild(usernameTd);

        let permissionsTd = document.createElement('td');
        let permissionsRow = document.createElement('div');
        permissionsRow.classList.add('row');
        permissionsTd.appendChild(permissionsRow);

        let user = new User(entry.username);
        entry.permissions.forEach(function (permission) {
            user.setPermission(permission, true);
        });
        user.buildPermissionMap();
        let permissions = [
            'CREATE',
            'UPLOAD',
            'MODERATE',
            'REGISTER',
            'ADMIN'
        ];
        permissions.forEach(function (permission) {
            createPermissionButton(permission, user.getPermission(permission).getValue(), permissionsRow, entry.username);
        });
        line.appendChild(permissionsTd);

        resultsTable.appendChild(line);

    }

    function createPermissionButton(permission, value, row, username) {
        let div = document.createElement('div');
        div.classList.add('col');
        div.classList.add('m2');
        let p = document.createElement('p');
        p.setAttribute('permission', permission);
        p.setAttribute('user', username);
        p.innerText = permission.charAt(0);
        p.setAttribute('onClick', "usersPagination.togglePermission(this)");
        p.classList.add(value ? 'granted' : 'notGranted');
        div.appendChild(p);
        row.appendChild(div);
    }
}


function User(name, permissionsDiv) {
    let username = name;
    let permissionNode = permissionsDiv;
    let primitivePermissionMap = new Map(
        [
            ["CREATE", false],
            ["UPLOAD", false],
            ["REGISTER", false],
            ["MODERATE", false],
            ["ADMIN", false]
        ]
    );

    let permissionMap = new Map();

    this.setPermission = function (permission, value) {
        primitivePermissionMap.set(permission, value);
    };

    this.getPermission = function (permissionName) {
        return permissionMap.get(permissionName);
    };

    this.buildPermissionMap = function () {
        primitivePermissionMap.forEach(function (value, key) {
            permissionMap.set(key, new Permission(key, value, username))
        })
    }
}

function Permission(permissionType, originalValue, user) {
    this.permissionType = permissionType;
    this.originalValue = originalValue;
    this.currentValue = originalValue;
    this.user = user;

    function toggle() {
        this.currentValue = !this.currentValue;
    }

    function isDirty() {
        return this.originalValue !== this.value
    }

    this.getValue = function () {
        return this.currentValue;
    }
}
