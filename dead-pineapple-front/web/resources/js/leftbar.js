$(document).ready(function() {

  var largeur_fenetre = $(window).width();
  console.log("largeur fenetre"+largeur_fenetre);
  var largeur_leftbar = largeur_fenetre/6;
  console.log("largeur leftbar"+largeur_leftbar);
  var largeur_boutons = (largeur_leftbar/6)*5;
  console.log("largeur boutons"+largeur_boutons);


  $('.connect').hide();

  $("#creer").click(function () {


    if ($(this).is("[title]")) {
      console.log("fermeture");
      $('.connect').hide("slow");
      $(this).removeAttr("title");
    } else {
      $('.connect').show("slow");
      $(this).attr("title", "ouvert");
    }
  });


  $('a[href*="anchor-"]').click(function () { // Au clic sur un élément
    var page = $(this).attr('href'); // Page cible
    var ancre = page.replace('#', '');
    var coordonnees = $('a[name="' + ancre + '"]').offset().top;
    var speed = 750; // Durée de l'animation (en ms)
    $('html, body').animate({scrollTop: coordonnees}, speed); // Go
    return false;
  });
// cacher icones a ouvertures du menu
  $("#leftbar").mouseenter(function(){
    $("#repli").hide("slow");
    $(this).stop(true, true).animate({marginLeft:'-20px'},'slow');

  });
  $("#leftbar").mouseleave(function(){
    $("#repli").show("slow");
    $(this).stop(true, true).animate({marginLeft:'-'+largeur_boutons+'px'},'slow');
  });
});