console.log("leftbar is in action");
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
$("#leftbar") .mouseover(function(){
  $(".connect").show();
  console.log("enter");
});
$("#leftbar").mouseout(function(){
  $(".connect").hide();
  console.log("leave");
});