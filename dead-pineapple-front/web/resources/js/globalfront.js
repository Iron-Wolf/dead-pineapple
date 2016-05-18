    $(document).ready(function(){


console.log("test1");
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


       // console.log("largeur fenetre"+largeur_fenetre/6);


//scroll vcers depuis menu
$("#creer").click(function(){
  //document.getElementById("uploadbutton").scrollIntoView(slow);
$("html, body").animate({ scrollTop: (conteneur_acceuil/4) }, 2000);
});
        console.log("test3");

//affichage de description des membres
$("#pablo").click(function(){
$("#description").text("PABLO VEUX DODO ! & PABLO VEUX DODO ! PABLO VEUX DODO ! PABLO VEUX DODO ! PABLO VEUX DODO ! PABLO VEUX DODO ! PABLO VEUX DODO ! ");
});
$("#sofiane").click(function(){
$("#description").text("SOOOOAFIIAINAISNISNDJDFNJKFNDKDJFNKJFNDFKJNFDKJFNFKDJFNDFKJFNDKFJDFNDFKJDFNKJ ");
});
        console.log("test4");

//navi
//$("#help").hide();

//déclenches les infos steps
$("#1upload").mouseover(function(){
    console.log("upload ok");
   $("#explicatif h1").text("Uploadez jusqu'à 10 gigas de videos convertibles, n'importe ou, n'importe quand");
});
$("#2convert").mouseover(function(){
    $("#explicatif h1").text("Convertissez vers tout type de format");
});
$("#3paye").mouseover(function(){
    $("#explicatif h1").text("Payez un maximum de 3 euros");
});
$("#4download").mouseover(function(){
    $("#explicatif h1").text("Récupérez vos conversions depuis votre cloud perso");
});

        console.log("test5");
        
        

//placement des lunettes
        //var p = $( ".lun" );
       // var position = p.position();


    });
