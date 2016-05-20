$(document).ready(function() {

  var largeur_fenetre = $(window).width();
  var largeur_leftbar = 270;
  var largeur_boutons = 217;
  var largeur_icons = 53;

  $("#leftbar").css({left:'-'+largeur_boutons+'px'});
  $('.connect').hide();

  $("#creer").click(function () {


    if ($(this).is("[title]")) {
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
    $("#repli").stop(true, true).animate({opacity:'0'},'fast');
    $(this).stop(true, true).animate({left:'0px'},'fast');

  });
  $("#leftbar").mouseleave(function(){
    $("#repli").stop(true, true).show().animate({opacity:'1'},'fast');
    $(this).stop(true, true).animate({left:'-'+largeur_boutons+'px'},'fast');
  });
});