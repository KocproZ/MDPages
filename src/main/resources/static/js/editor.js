function setupForm(allTags, pageTags) {

    $('#name-input').characterCounter();


    var convertedAllTags = {};
    allTags.forEach(function (tag) {
        convertedAllTags[tag] = null;
    });

    var convertedPageTags = [];
    if (pageTags)
        pageTags.forEach(function (tag) {
            convertedPageTags.push({tag: tag})
        });
    //console.log(convertedPageTags);
    $('#tags').material_chip({
        data: convertedPageTags,
        autocompleteOptions: {
            data: convertedAllTags,
            limit: Infinity,
            minLength: 1
        }
    });

    $('#pageForm').submit(function () {
        $('#pageContent').val($('#contentValue').val());
        var tagsInput = $('#tags-input');
        var chips = $('#tags').material_chip('data');
        chips.forEach(function (element, index) {
            if (index == 0)
                tagsInput.val(element.tag);
            else
                tagsInput.val(tagsInput.val() + "," + element.tag);
        })
    })
}