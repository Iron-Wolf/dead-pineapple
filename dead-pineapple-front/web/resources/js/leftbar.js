$('.connect').hide();

$("#creer").click(function(){


  if($(this).is("[title]")) {
    console.log("fermeture");
    $('.connect').hide("slow");
    $(this).removeAttr("title");
  }else {
    $('.connect').show("slow");
    $(this).attr("title","ouvert");
  }
});
