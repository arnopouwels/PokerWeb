$(document).ready(function(){
    $("#passwordReg").keyup(function(){
        $("#result").html(strength($(this).val()));
        //$("#countLen").text($(this).val().length);

    });
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

