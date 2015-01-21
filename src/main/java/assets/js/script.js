$(document).ready(function(){
    $("#passwordReg").keyup(function(){
        $("#result").html(strength($(this).val()));
        //$("#countLen").text($(this).val().length);

    });

   /* $("#card1").fadeIn('slow',function(){
      $(this).animate({'bottom': '-=50px'},'slow');
    });
    $("#card2").fadeIn('slow',function(){
          $(this).animate({'bottom': '-=50px'},'slow');
        });
    $("#card3").fadeIn('slow',function(){
          $(this).animate({'bottom': '-=50px'},'slow');
        });
    $("#card4").fadeIn('slow',function(){
          $(this).animate({'bottom': '-=50px'},'slow');
        });
    $("#card5").fadeIn('slow',function(){
          $(this).animate({'bottom': '-=50px'},'slow');
        });*/
});

function strength(pass)
{
    var passStrength = 0;
    var len = 0;
    len = pass.length;
    if (len < 5) passStrength += 0;
    else if (len < 8) passStrength += 1;
    else if (len >= 8) passStrength += 2;
    if (pass.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/)) passStrength += 2;
    if (pass.match(/([a-zA-Z])/) && pass.match(/([0-9])/))  passStrength += 2;
    if (pass.match(/([!,%,&,@,#,$,^,*,?,_,~])/))  passStrength += 2;
    if (pass.match(/(.*[!,%,&,@,#,$,^,*,?,_,~].*[!,%,&,@,#,$,^,*,?,_,~])/)) passStrength += 2;


    if (passStrength <= 3) return "Weak";
    if (passStrength <= 7) return "Good";
    if (passStrength > 7) return "Strong";
};

