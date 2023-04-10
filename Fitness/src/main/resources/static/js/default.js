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

    $('#sidebar').hover(() => {
        $('#sidebar').addClass('hosc')
    }, () => {
        $('#sidebar').removeClass('hosc')
    });

    if ($('#login-btn')) {
        $('#login-btn > a').attr('href', '/login?redirectURL=' + location.pathname.slice(1));
    }
});