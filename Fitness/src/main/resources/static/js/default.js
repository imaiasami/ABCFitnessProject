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
});