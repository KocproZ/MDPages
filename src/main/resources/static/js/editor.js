var multiple;

function setupForm(pageTags) {
    multiple = $('#tagsInput').materialize_autocomplete({
        multiple: {
            enable: true,
            maxSize: Infinity
        },
        appender: {
            el: '.ac-tags',
            tagTemplate: '<div class="chip" data-id="<%= item.id %>" data-text="<%= item.text %>"><%= item.text %><i class="material-icons close">close</i></div>'

        },
        dropdown: {
            el: '#multipleDropdown'
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
                    var convertedTags = convertTags(data);
                    callback(value, convertedTags);
                }
            });
        }
    });
    if (pageTags !== null)
        pageTags.forEach(function (tag) {
            multiple.append({id: tag, text: tag})
        });
    var tagsInput = document.querySelector('#tagsInput');
    tagsInput.onkeyup = function (e) {
        if (e.code === 'Enter' && tagsInput.value.length > 0) multiple.append({
            id: tagsInput.value,
            text: tagsInput.value
        })
    }
}

function convertTags(strings) {

    var convertedAllTags = [];
    strings.forEach(function (tag) {
        convertedAllTags.push({id: tag, text: tag});
    });
    return convertedAllTags;
}