

var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
var mapContainer = document.getElementById('map'),// 지도를 표시할div
    mapOption = {
        center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
    };
//지도를 미리 생성
var map = new daum.maps.Map(mapContainer, mapOption); //주소-좌표 변환 객체를 생성

var geocoder = new daum.maps.services.Geocoder(); //마커를 미리 생성
var marker1 = new daum.maps.Marker({position: new daum.maps.LatLng(37.537187, 127.005476), map: map})
//현위치 마커 1생성 .

var boardid = [];
var boardx = [];
var boardy = [];
var positions = [];
var marker;

$(function () {
    $.ajax({
        type: "GET",
        url: '/board/address',
        dataType: "json",
        success: function (data) {
            console.log("접속 성공");
            console.log(Object.keys(data));
            console.log(Object.keys(data)[0]);
            console.log(Object.keys(data).length);

            for (var i = 0; i < Object.keys(data).length; i++) {

                boardid[i] = Object.keys(data)[i];
            }
            console.log(boardid);
            console.log(boardid[1]);
            console.log(data[boardid[0]]);
            console.log(data[boardid[0]].x);
            for (var i = 0; i < Object.keys(data).length; i++) {

                boardx[i] = data[boardid[i]].x
            }
            console.log(boardx)
            for (var i = 0; i < Object.keys(data).length; i++) {

                boardy[i] = data[boardid[i]].y
            }
            console.log(boardy)

            for (var i = 0; i < Object.keys(data).length; i++) {
                positions[i] =
                    {
                        title: boardid[i],
                        latlng: new kakao.maps.LatLng(boardy[i], boardx[i])
                    };

            }
            console.log(positions)

            for (var i = 0; i < Object.keys(data).length; i++) {

                // 마커 이미지의 이미지 크기 입니다
                var imageSize = new kakao.maps.Size(24, 35);

                // 마커 이미지를 생성합니다
                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

                // 마커를 생성합니다
                marker = new kakao.maps.Marker({
                    map: map, // 마커를 표시할 지도
                    position: positions[i].latlng, // 마커를 표시할 위치
                    title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                    image: markerImage, // 마커 이미지
                });

            }
        }
    });
});
;



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
                    document.getElementById("sample5_x").value = result.x;
                    document.getElementById("sample5_y").value = result.y;
                    mapContainer.style.display = "block";
                    map.relayout(); // 지도 중심을 변경한다.
                    map.setCenter(coords); // 마커를 결과값으로 받은 위치로 옮긴다.
                    console.log(coords);
                    console.log(positions[0].latlng);

                    marker1.setPosition(coords);


                }
            });
        }
    }).open();
}

