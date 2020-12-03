# Springboot_JPA_chattingExample  
### Websocket  
+ Websocket은 기존의 단방향 HTTP 프로토콜과 호환되어 양방향 통신을 제공하기위해 개발된 프로토콜  
일반 socket통신과 달리 HTTP 80 Port를 이용하므로 방화벽에 제약이 없으며 통상 Websocket으로 불림  
접속까지는 HTTP 프로토콜을 이용하고 그 이후의 통신은 자체적인 Websocket 프로토콜로 통신하게 됨.  
##### 11월 5일 Springboot Websocket 서버 구축



### Stomp  
메시징 전송을 효율적으로 하기 위해 나온 프로토콜이며 기본적으로  
pub/sub 구조로 되어있음 메시지를 발송하고, 메시지를 받아 처리하는 부분이 확실히  
정해져 있기 때문에 개발하는 입장에서 명확하게 인지하고 개발할 수 있는 이점이 있습니다.  
또한 Stomp를 이용하면 통신 메시지의 헤더에 값을 세팅 할 수 있어 헤더 값을 기반으로 통신 시 인증처리를 구현하는 것도 가능
  
 pub/sub 메시징 방법 이것만 기억하기 우체통(topic) 집배원 (publisher)이 신문을 우체통에 배달 이걸 기다렸다가 보는 구독자(subcriber)의 액션

* 채팅방 생성 - pub/sub 구현을 위한 Topic이 하나 생성된다.  
* 채팅방 입장 - Topic을 구독  
* 채팅방에서 메시지 주고 받기 - 해당 Topic으로 메시지를 발송(pub) 받는다(sub)  
##### dependancies에 stomp관련 라이브러리 추가 
##### webjar : 채팅 웹 화면을 구현하기 위해 필요한 js를 로드
##### sockjs : websocket을 지원하지 않는 낮은 버전의 브라우저에서도 websocket을 사용할수 있도록 해주는 라이브러리  
  
#### 11월 6일 Springboot Websocket Stomp 서버  


#### 11월 7일 redis pub/sub 구현
