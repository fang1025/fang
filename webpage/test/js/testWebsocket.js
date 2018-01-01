

$(function() {
	// 建立websocket连接
	var sysdate = new Date(), datestr;
	var _interval = null;
	var socket = new SockJS( '/realdata');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		// 定时接收服务器的时间
		stompClient.subscribe('/topic/messagepush', function(data) {
			if ($.trim(data.body) != "") {
				datestr = $.trim(data.body);
				datestr = datestr.replace(/-/g, "/");
				// sysdate = new Date(datestr);
				console.log("aaaaaaaaa--" + datestr);
			}
		});
		
		stompClient.subscribe('/topic/custompush/' + "test", function(data) {
			if ($.trim(data.body) != "") {
				console.log("bbbbbbbbbb--" + $.trim(data.body));
				$("#text").html(data.body);
			}
		});
		
		if (_interval != null) {
			clearInterval(_interval);
		}
		_interval = setInterval(function() {
			stompClient.send("/app/custompush/" + "test",{"name":"test","brower":navigator.userAgent.toLowerCase()},"hhhh");
		}, 6000);
	});
});