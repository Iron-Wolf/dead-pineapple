$(document).ready(function () {

//definition dimentions fenetre
    var largeur_fenetre = $(window).width();
    var hauteur_fenetre = $(window).height();
    var conteneur_acceuil = hauteur_fenetre * 4;

//redimentionnement du conteneur_acceuil

    $('#conteneur').height(conteneur_acceuil);
    $('#ajoutez').height(hauteur_fenetre);
    $('#whats').height(hauteur_fenetre);
    $('#whos').height(hauteur_fenetre);
    $('#contact').height(hauteur_fenetre);

    //si on est sur du paralax
    var actualPosition = -1;
    if($('#conteneur').length > 0){

        //grossissement des icones sur le scroll
        var grossiment = function () {
            var position = parseInt($(window).scrollTop() / hauteur_fenetre);
            if (position != actualPosition) {
                actualPosition = position;
                var icons = $('#repli .icon');
                for (var i = 0; i < icons.length; i++) {
                    if (i == actualPosition) {
                        $(icons[i]).addClass('bigger');
                    } else {
                        $(icons[i]).removeClass('bigger');
                    }
                }
            }
        };

        //sur le scroll et au start
        $(window).scroll(grossiment);
        grossiment();
    }




//affichage de description des membres
    $("#pablo").click(function () {
        $("#description").text("PABLO VEUX DODO ! & PABLO VEUX DODO ! PABLO VEUX DODO ! PABLO VEUX DODO ! PABLO VEUX DODO ! PABLO VEUX DODO ! PABLO VEUX DODO ! ");
    });
    $("#sofiane").click(function () {
        $("#description").text("SOOOOAFIIAINAISNISNDJDFNJKFNDKDJFNKJFNDFKJNFDKJFNFKDJFNDFKJFNDKFJDFNDFKJDFNKJ ");
    });


//navi
//$("#help").hide();

//déclenches les infos steps
    $("#1upload").mouseover(function () {
        $("#explicatif h1").text("Uploadez jusqu'à 10 gigas de videos convertibles, n'importe ou, n'importe quand");
    });
    $("#2convert").mouseover(function () {
        $("#explicatif h1").text("Convertissez vers tout type de format");
    });
    $("#3paye").mouseover(function () {
        $("#explicatif h1").text("Payez un maximum de 3 euros");
    });
    $("#4download").mouseover(function () {
        $("#explicatif h1").text("Récupérez vos conversions depuis votre cloud perso");
    });


//placement des lunettes
//todo: faire les lunnettes

});
