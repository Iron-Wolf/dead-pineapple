    $(document).ready(function(){


console.log("test");
//definition dimentions fenetre
var largeur_fenetre = $(window).width();
var hauteur_fenetre = $(window).height();
var conteneur_acceuil = hauteur_fenetre*4;

//redimentionnement du conteneur_acceuil

$('#conteneur').height(conteneur_acceuil);
$('#ajoutez').height(conteneur_acceuil/4);
$('#whats').height(conteneur_acceuil/4);
$('#whos').height(conteneur_acceuil/4);
$('#contact').height(conteneur_acceuil/4);




//scroll vcers depuis menu
$("#creer").click(function(){
  //document.getElementById("uploadbutton").scrollIntoView(slow);
$("html, body").animate({ scrollTop: (conteneur_acceuil/4) }, 2000);
});

//affichage de description des membres
$("#pablo").click(function(){
$("#description").text("PABLO VEUX DODO ! & PABLO VEUX DODO ! PABLO VEUX DODO ! PABLO VEUX DODO ! PABLO VEUX DODO ! PABLO VEUX DODO ! PABLO VEUX DODO ! ");
});
$("#sofiane").click(function(){
$("#description").text("SOOOOAFIIAINAISNISNDJDFNJKFNDKDJFNKJFNDFKJNFDKJFNFKDJFNDFKJFNDKFJDFNDFKJDFNKJ ");
});


//$("#help").hide();

});
