var lang = location.pathname.substr(0, 3);

function changeLanguage(target) {
    location.href = location.href.replace(lang, '/' + target);
}

$(() => {
    $('#menu-icon').click(() => {
        $('#sidemodal').toggleClass('open');
        $('#sidebar').toggleClass('open');
    });

    $('#sidemodal').click(() => {
        $('#sidemodal').toggleClass('open');
        $('#sidebar').toggleClass('open');
    });

    $('#etc-btn').click(() => {
        $('#etc-popup').toggleClass('open');
    });

    $('#etc-lang').click(() => {
        $('#etc-lang-select').toggleClass('hide');
    });

    $('#sidebar').hover(() => {
        $('#sidebar').addClass('hosc');
    }, () => {
        $('#sidebar').removeClass('hosc');
    });

    if ($('#login-btn')) {
        $('#login-btn > a').attr('href', lang + '/login?redirectURL=' + location.pathname);
    }
});