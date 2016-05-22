$(document).ready(function () {

//definition dimentions fenetre
    var largeur_fenetre, hauteur_fenetre, conteneur_acceuil;

//redimentionnement du conteneur_acceuil
    function misAjourDesTaillesConteneur() {
        largeur_fenetre = $(window).width();
        hauteur_fenetre = $(window).height();
        if(hauteur_fenetre <480){
            hauteur_fenetre = 480;
        }
        conteneur_acceuil = hauteur_fenetre * 4;

        $('#conteneur').height(conteneur_acceuil);
        $('#ajoutez').height(hauteur_fenetre);
        $('#whats').height(hauteur_fenetre);
        $('#whos').height(hauteur_fenetre);
        $('#contact').height(hauteur_fenetre);
    }

    //si on est sur du paralax
    var actualPosition = -1;
    if ($('#conteneur').length > 0) {

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

        $(window).resize(misAjourDesTaillesConteneur);
        misAjourDesTaillesConteneur();

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
        $("#explicatif h1").html('Uploadez jusqu\'à 10 gigas de videos à convertir. <br/> <span class="small"> N\'importe ou, n\'importe quand!</span>');
    });
    $("#2convert").mouseover(function () {
        $("#explicatif h1").html('Convertissez vers tout type de formats et encodages!<br/> <span class="small">14 formats et 5 encodages disponibles.</span>');
    });
    $("#3paye").mouseover(function () {
        $("#explicatif h1").html('Payez un maximum de <strong>3,5€</strong>! <br/> <span class="small">Pour un film 1heure 30.</span>');
    });
    $("#4download").mouseover(function () {
        $("#explicatif h1").html('Télécharger vos conversions depuis toute les plateforme! <br/> <span class="small">Depuis votre boite mail ou votre espace dédié en ligne.</span>');
    });


//placement des lunettes
//todo: faire les lunnettes

});
