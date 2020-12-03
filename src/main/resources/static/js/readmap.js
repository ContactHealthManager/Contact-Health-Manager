
var mapContainer = document.getElementById('map'),// 지도를 표시할div
    mapOption = {
        center: new daum.maps.LatLng(37.4875235978289, 126.826163535057), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
    };
//지도를 미리 생성
var map = new daum.maps.Map(mapContainer, mapOption); //주소-좌표 변환 객체를 생성
var geocoder = new daum.maps.services.Geocoder(); //마커를 미리 생성
// 마커를 표시할 위치와 title 객체 배열입니다
var marker2 =new daum.maps.Marker({position: new daum.maps.LatLng(37.537187, 127.005476), map: map});

var boardid = [];
var boardx = [];
var boardy = [];
var positions = [];
var boardaddress = [];
var boardtitle = [];
var boardwriter = [];
var marker;


$.ajax({
    url : "http://localhost:8080/board/address", // test.jsp 에서 받아옴
    dataType :"json", // 데이터타입을 json 으로 받아옴

    success : function(data) {
        for (var key in data) {
            var x = data[key];
            var y = data[key];
            var address = data[key];
            console.log("attribute:" + key + ',value:' + x.x + "," + y.y);

        }

        for (var i = 0; i < Object.keys(data).length; i++) {

            boardid[i] = Object.keys(data)[i];
        }
        for (var i = 0; i < Object.keys(data).length; i++) {

            boardaddress[i] = data[boardid[i]].address
        }
        for (var i = 0; i < Object.keys(data).length; i++) {

            boardx[i] = data[boardid[i]].x
        }
        for (var i = 0; i < Object.keys(data).length; i++) {

            boardy[i] = data[boardid[i]].y
        }
        for (var i = 0; i < Object.keys(data).length; i++) {

            boardtitle[i] = data[boardid[i]].title
        }
        for (var i = 0; i < Object.keys(data).length; i++) {

            boardwriter[i] = data[boardid[i]].writer
        }
        for (var i = 0; i < Object.keys(data).length; i++) {
            positions[i] =
                {
                    title: boardid[i],
                    latlng: new kakao.maps.LatLng(boardy[i], boardx[i])
                };

        }
        for (var i = 0; i < Object.keys(data).length; i++) {

// 마커 이미지의 이미지 주소입니다
            var imageSrc1 = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_red.png";

            // 마커 이미지의 이미지 크기 입니다
            var imageSize = new kakao.maps.Size(28, 40);

            // 마커 이미지를 생성합니다
            var markerImage1 = new kakao.maps.MarkerImage(imageSrc1, imageSize);

            // 마커를 생성합니다
            marker = new kakao.maps.Marker({
                map: map, // 마커를 표시할 지도
                position: positions[i].latlng, // 마커를 표시할 위치
                title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                image: markerImage1, // 마커 이미지
                clickable: true // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
            });
            var url = "post/"+boardid[i];
            var a = document.getElementById('b')
            var iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다
            // 마커에 표시할 인포윈도우를 생성합니다
            var infowindow = new kakao.maps.InfoWindow({

                content:   '    <div class="info">' +
                    '        <div class="title" style="color: black">' +
                    boardtitle[i] +
                    '        </div>' +
                    '        <div class="body" style="font-size: 15px">' +
                    '            <div class="desc">' +
                    '                <div class="ellipsis">'+boardaddress[i]+'</div>'+
                    '                <div><a href="post/'+boardid[i]+'" starget="_self" class="link">홈페이지</a></div>'+
                    '            </div>' +
                    '        </div>' +
                    '    </div>'
                ,
                removable : iwRemoveable

            });

        }
        kakao.maps.event.addListener(marker, 'click', function() {
            // 마커 위에 인포윈도우를 표시합니다
            infowindow.open(map, marker);
        });

        console.log("성공");
        console.log(data)

    },

    error : function(e) {
        console.log(e.responseText);
    }
});


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
            // 마커 이미지를 생성합니다
            // 마커 이미지의 이미지 크기 입니다
            var imageSize = new kakao.maps.Size(28, 40);
            var imageSrc2 = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
            var markerImage2 = new kakao.maps.MarkerImage(imageSrc2, imageSize);
            geocoder.addressSearch(data.address, function (results, status) {
                // 정상적으로 검색이 완료됐으면
                if (status === daum.maps.services.Status.OK) {
                    var result = results[0]; //첫번째 결과의 값을 활용 // 해당 주소에 대한 좌표를 받아서
                    var coords = new daum.maps.LatLng(result.y, result.x); // 지도를 보여준다.
                    mapContainer.style.display = "block";
                    map.relayout(); // 지도 중심을 변경한다.
                    map.setCenter(coords); // 마커를 결과값으로 받은 위치로 옮긴다.
                    marker2.setImage(markerImage2)
                    marker2.setPosition(coords)


                }
            });
        }
    }).open();
}


