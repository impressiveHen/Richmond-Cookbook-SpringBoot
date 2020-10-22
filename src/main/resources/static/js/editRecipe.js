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

    $('a[href="#show-recipe-detail"]').on('click', function(event) {
        let recipeCardIdx = $(this).closest('div.recipecard').attr('data');

        let recipe = $('.recipecard[data='+recipeCardIdx+']');
        $('#recipeDetailModal').find('.modal-title').text(recipe.find('.card-title').text());
        $('#recipeDetailModal').find('img').attr('src', recipe.find('.card-img-top').attr('src'));

        $('#recipeDetailModal').find('.steps-timeline').empty();
        let steps = recipe.find('#steps').data('steps');

        if (typeof steps === 'string') {
            steps = steps.substring(1, steps.length-1).split(',');

            for (let i=0; i<steps.length; i++) {
                let step = steps[i].replaceAll('@', ',');
                if (i%2===0) {
                    $('#recipeDetailModal').find('.steps-timeline').append(
                        '<div class="timeline"><div id="step'+i+'"class="box right"><div class="content"><h5>Step '+i+'</h5><p>'+step+'</p></div></div>'
                    );
                } else {
                    $('#recipeDetailModal').find('.steps-timeline').append(
                        '<div class="timeline"><div id="step'+i+'"class="box left"><div class="content"><h5>Step '+i+'</h5><p>'+step+'</p></div></div>'
                    );
                }
            }
        }

        $('#recipeDetailModal').modal('show');
    });
});