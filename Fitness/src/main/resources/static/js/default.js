// window.addEventListener('DOMContentLoaded', function() {
//     document.getElementById('menu-icon').addEventListener('click', () => {
//         document.getElementById('sidebar').classList.toggle('open');
//     });
// });
$(() => {
    $('#menu-icon').click(() => {
        $('#sidebar').toggleClass('open');
    });
});