showdown.extension('audio', {
    type: 'lang',
    regex: /\?\[.+\]\((.+(?=\)))\)/g,
    replace: function (a, b, c) {
        var element = '<audio controls="true" preload="metadata">' +
            '<source src="' + b + '"/>' +
            '</audio>'
        return element;
    },
});