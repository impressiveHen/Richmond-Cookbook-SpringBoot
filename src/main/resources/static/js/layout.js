$(document).ready(function () {
    $(function() {
        $('a.nav-link').each(function(){
            if ($(this).prop('href') == window.location.href) {
                $(this).addClass('active');
                $(this).parents('li').addClass('active');
            }
        });
    });
});
