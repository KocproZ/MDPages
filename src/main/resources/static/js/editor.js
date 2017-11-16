var tagsMultiple;
var usersMultiple;

function setupForm(pageTags) {
    tagsMultiple = $('#tagsInput').materialize_autocomplete({
        multiple: {
            enable: true,
            maxSize: Infinity
        },
        appender: {
            el: '.ac-tags',
            tagTemplate: '<div class="chip" data-id="<%= item.id %>" data-text="<%= item.text %>"><%= item.text %><i class="material-icons close">close</i></div>'

        },
        dropdown: {
            el: '#tagsMultipleDropdown'
        },
        hidden: {
            el: '#hiddenTags'
        },
        getData: function (value, callback) {
            request = $.ajax({
                type: 'GET',
                url: '/api/tagsAutocomplete',
                data: {
                    fragment: value
                },
                success: function (data) {
                    var convertedTags = convertForAutocomplete(data);
                    callback(value, convertedTags);
                }
            });
        }
    });
    /*usersMultiple = $('#usersInput').materialize_autocomplete({//TODO add in html
        multiple: {
            enable: true,
            maxSize: Infinity
        },
        appender: {
            el: '.ac-users',
            tagTemplate: '<div class="chip" data-id="<%= item.id %>" data-text="<%= item.text %>"><%= item.text %><i class="material-icons close">close</i></div>'

        },
        dropdown: {
            el: '#usersMultipleDropdown'
        },
        hidden: {
            el: '#hiddenUsers'
        },
        getData: function (value, callback) {
            request = $.ajax({
                type: 'GET',
                url: '/api/usersAutocomplete',
                data: {
                    fragment: value
                },
                success: function (data) {
                    var convertedUsers = convertForAutocomplete(data);
                    callback(value, convertedUsers);
                }
            });
        }
    });*/
    if (pageTags !== null)
        pageTags.forEach(function (tag) {
            tagsMultiple.append({id: tag, text: tag})
        });
    var tagsInput = document.querySelector('#tagsInput');
    tagsInput.onkeyup = function (e) {
        if (e.code === 'Enter' && tagsInput.value.length > 0) tagsMultiple.append({
            id: tagsInput.value,
            text: tagsInput.value
        })
    }

    $('#pageForm').submit(function () {
        if ($('#name-input').val().length <= 3)
            return false;
    });
}

function convertForAutocomplete(strings) {

    var convertedAll = [];
    strings.forEach(function (string) {
        convertedAll.push({id: string, text: string});
    });
    return convertedAll;
}