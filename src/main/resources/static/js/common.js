// Get 방식 다건 조회
function restList(url, paramap, fn){
	$.ajax({
			url : url
			, method : "GET"
			, data : paramap
			, dataType : "json"
			, statusCode: {
				417: function() {
					alert( "내부 오류가 발생하였습니다." );
				}
			}
			, success: fn
		})
}

function restOne(url, paramap, fn){
	$.ajax({
			url : url
			, method : "GET"
			, data : paramap
			, dataType : "json"
			, statusCode: {
				417: function() {
					alert( "내부 오류가 발생하였습니다." );
				}
			}
			, success: fn
		})
}

function restInsert(url, paramap, fn){
	$.ajax({
			url : url
			, method : "POST"
			, data : paramap
			, dataType : "json"
			, statusCode: {
				417: function() {
					alert( "내부 오류가 발생하였습니다." );
				}
			}
			, success: fn
		})
}

function restUpdate(url, paramap, fn){
	$.ajax({
			url : url
			, method : "PUT"
			, data : paramap
			, dataType : "json"
			, statusCode: {
				417: function() {
					alert( "내부 오류가 발생하였습니다." );
				}
			}
			, success: fn
		})
}


function restDelete(url, paramap, fn){
	$.ajax({
			url : url
			, method : "DELETE"
			, data : paramap
			, dataType : "json"
			, statusCode: {
				417: function() {
					alert( "내부 오류가 발생하였습니다." );
				}
			}
			, success: fn
		})
}


function getByteLength(s, b, i, c){
	for(b=i=0;c=s.charCodeAt(i++); b+=c>>11?3:c>>7?2:1);
	return b;
}

//byte 계산 후 자르기
function sliceByByte(str, maxByte) {
	if(getByteLength(str) > maxByte){
		alert("최대 길이 입니다");
		str = str.substring(0, maxByte);
	}
    
  	return str;
    
 }
