$(document).ready(function(){
	builderIndex();
	calculateMoney();
    setRemove();
	$("#addItemButton").click(
		function(){
			var newNode = $("#items").children("div").first().clone();
			newNode.find("input").val("");
			$("#items").children("div").last().after(newNode);
            setRemove();
			$("#items").find("button").attr("disabled",false);
			builderIndex();
			$(".money").change(
				function(){
					calculateMoney();
				}
			);
			calculateMoney();
		}
	);
	$(".money").change(
		function(){
			calculateMoney();
		}
	);
});// JavaScript Document

function builderIndex(){
	$.each($("#items").children(),function(i,val){
		$("#items").children("div").eq(i).children().eq(0).children().eq(0).find("select").attr("name","items["+i+"].item");
		$("#items").children("div").eq(i).children().eq(0).children().eq(1).find("input").attr("name","items["+i+"].amount");
		$("#items").children("div").eq(i).children().eq(0).children().eq(2).find("input").attr("name","items["+i+"].comment");
		$("#items").children("div").eq(i).children().eq(1).children().eq(0).find("select").attr("name","items["+i+"].guahaoType");
		$("#items").children("div").eq(i).children().eq(1).children().eq(1).find("input").attr("name","items["+i+"].hospital");
		$("#items").children("div").eq(i).children().eq(1).children().eq(2).find("input").attr("name","items["+i+"].keshi");
		// $("#items").children("div").eq(i).children().eq(2).children().eq(0).find("input").attr("name","items["+i+"].guahaoType");
		$("#items").children("div").eq(i).children().eq(2).children().eq(0).find("input").attr("name","pictures["+i+"]");
	});	
}
function calculateMoney(){
	var totalMoney=0;
	$.each($(".money"),function(i,val){
		totalMoney+=parseFloat($(".money").eq(i).val());
	});
	if (isNaN(totalMoney)||totalMoney==0){
		$("#totalMoney").attr("value","");
	}else {
		$("#totalMoney").attr("value",totalMoney);
	}
}

function setRemove(){
    $("#items").children("div").find("button").click(
        function(){
            $(this).parent().parent().parent().remove();
            if($("#items").children("div").size()==1){
                $("#items").find("button").attr("disabled",true);
            }
            builderIndex();
            calculateMoney();
        }
    );
}