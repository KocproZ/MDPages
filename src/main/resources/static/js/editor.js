function setupTags(allTags, pageTags) {

    $('#name-input').characterCounter();


    var convertedAllTags = {};
    allTags.forEach(function (tag) {
        convertedAllTags[tag] = null;
    });

    var convertedPageTags = [];

    pageTags.forEach(function (tag) {
        convertedPageTags.push({tag: tag})
    });
    console.log(convertedPageTags);
    $('#tags').material_chip({
        data: convertedPageTags,
        autocompleteOptions: {
            data: convertedAllTags,
            limit: Infinity,
            minLength: 1
        }
    });
}