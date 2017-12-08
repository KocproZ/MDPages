showdown.extension('audio', {
    type: 'lang',
    // regex: /\?\[.+\]\((.+(?=\)))\)/g,
    regex: /\?\[(.+)\]\((.+(?=\)))\)/g,
    replace: function (a, b, c, d) {
        var element = '<audio controls="true" preload="metadata">' +
            '<source src="' + c + '"/>' +
            b +
            '</audio>'
        return element;
    },
});