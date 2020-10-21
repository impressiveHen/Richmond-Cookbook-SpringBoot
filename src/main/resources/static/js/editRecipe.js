$(document).ready(function () {
    $('#add-recipe-step').on('click', function(event) {
        let index = $('#recipe-steps-view').children().length + $('#recipe-steps').children().length;

        $('#recipe-steps').append(
            '<input class="form-control mb-2" id="step'+index+'" name="steps['+index+'].step" type="text" placeholder="Step '+index+'"/>'
        );
    });

    $(".close-recipe-step").on('click', function(event) {
        event.stopPropagation();
        $(this).parent().remove();
    });
});
