var mapContainer = document.getElementById('map'),// 지도를 표시할div
    mapOption = {
        center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
    };
//지도를 미리 생성
var map = new daum.maps.Map(mapContainer, mapOption); //주소-좌표 변환 객체를 생성
var geocoder = new daum.maps.services.Geocoder(); //마커를 미리 생성
var marker = new daum.maps.Marker({position: new daum.maps.LatLng(37.537187, 127.005476), map: map});

function sample5_execDaumPostcode() {
    new daum.Postcode({
        oncomplete: function (data) {
            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var fullAddr = data.address; // 최종 주소 변수
            var extraAddr = ''; // 조합형 주소 변수 // 기본 주소가 도로명 타입일때 조합한다.
            if (data.addressType === 'R') { //법정동명이 있을 경우 추가한다.
                if (data.bname !== '') {
                    extraAddr += data.bname;
                } // 건물명이 있을 경우 추가한다.
                if (data.buildingName !== '') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                } // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종 주소를 만든다.
                fullAddr += (extraAddr !== '' ? ' (' + extraAddr + ')' : '');
            } // 주소 정보를 해당 필드에 넣는다.
            document.getElementById("sample5_address").value = fullAddr; // 주소로 상세 정보를 검색
            geocoder.addressSearch(data.address, function (results, status) {
                // 정상적으로 검색이 완료됐으면
                if (status === daum.maps.services.Status.OK) {
                    var result = results[0]; //첫번째 결과의 값을 활용 // 해당 주소에 대한 좌표를 받아서
                    var coords = new daum.maps.LatLng(result.y, result.x); // 지도를 보여준다.
                    mapContainer.style.display = "block";
                    map.relayout(); // 지도 중심을 변경한다.
                    map.setCenter(coords); // 마커를 결과값으로 받은 위치로 옮긴다.
                    marker.setPosition(coords)
                    alert(coords)
                }
            });
        }
    }).open();
}
