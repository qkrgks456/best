
//소켓생성, 소켓요청경로 설정과 stomp 먹여주기
var sockJsForAlarm = new SockJS("/stomp/alarm");
var stompForAlarm = Stomp.over(sockJsForAlarm);

console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+loginIdForAlarm);

stompForAlarm.connect({},function(sock){
 						
		console.log("알람용 STOMP 연결!");
		console.log("sock :: "+ sock);
		 
		//채팅알람
		stompForAlarm.subscribe("/sub/alarm/chatAlarm", function(data){
		 							
		var person = data.body
		console.log("상대방의 아이디 :: "+ person);
		
			 $("#alarmArea").empty();
			
			var alarmText = person + " 님으로부터 새로운 메세지 도착";
			 
			var strForAlarm = '<div class="toast align-items-center" role="alert" aria-live="assertive" aria-atomic="true">';
				  strForAlarm += '<div class="d-flex">';
				  strForAlarm += '<div class="toast-body">';
				  strForAlarm += alarmText;
				  strForAlarm += '</div>';
				  strForAlarm += '<button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>';
				  strForAlarm += '</div>';
				  strForAlarm += '</div>'; 
			 
			 $("#alarmArea").append(strForAlarm);
			 
			 $(".toast").show();
		
		})
		
		
		//팔로우 알람
		stompForAlarm.subscribe("/sub/alarm/followAlarm", function(data){
		 							
		var person = data.body
		console.log("상대방의 아이디 :: "+ person);
		
		 $("#alarmArea").empty();
		 						
		var alarmText = person + " 님이 회원님을 팔로우 했습니다.";
		 
		var strForAlarm = '<div class="toast align-items-center" role="alert" aria-live="assertive" aria-atomic="true">';
			  strForAlarm += '<div class="d-flex">';
			  strForAlarm += '<div class="toast-body">';
			  strForAlarm += alarmText;
			  strForAlarm += '</div>';
			  strForAlarm += '<button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast" aria-label="Close"></button>';
			  strForAlarm += '</div>';
			  strForAlarm += '</div>'; 
		 
		 $("#alarmArea").append(strForAlarm);
		 
		 $(".toast").show();

		})
		
		
	
	
	})



















	$(document).on('click', '.btn-close', function () {
        $(this).closest('div.toast').hide(100);
        $(this).remove();
    })
