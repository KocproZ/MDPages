var listEntries = [];

function fetchPermissions(page) {
    fetch('/admin/permissions?p=' + page, {
        method: 'GET', credentials: 'same-origin'
    }).then(function (response) {
        return response.json()
    }).then()
}

function parsePermissions(permissionsJSON) {
    listEntries = [];
    permissionsJSON.forEach(function (entry) {

    })
}

function savePermissions() {

}

function User() {
    var permissionsMap = {};

    function addPermission(type, value, user) {
        permissionsMap[type] = new Permission(type, value, user)
    }
}

function Permission(permissionType, originalValue, user) {
    this.permissionType = permissionType;
    this.originalValue = originalValue;
    this.currentValue = originalValue;
    this.user = user;

    function toggle() {

    }

    function isDirty() {
        return this.originalValue !== this.value
    }
}
