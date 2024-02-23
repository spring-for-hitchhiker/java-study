# QnA

+ Object 클래스의 스레드 관련 메서드(wait, notify 등)에 대해 설명해주세요.
	+ wait은 현재 실행 중인 스레드를 일시적으로 대기상태로 만들어 다른 스레드가 해당 객체를 사용할 수 있도록 함
    + notify는 대기 상태인 스레드를 하나 깨워서 실행 대기 상태로 만들어줌

+ sychronized method과 sychronized block의 차이가 무엇인가요? 
    + synchronized method는 메서드 전체에 동기화 영역으로 지정함 
    + synchronized block은 해당 블럭에만 동기화를 해줌

+ sychronized와 static synchonized의 차이가 무엇인가요?
    + synchronized는 일반적인 메서드나 블럭에 사용되어 인스턴스에 대해 동기화됨
    + static synchronized는 스태틱 메서드와 블럭에 사용되고 그 클래스의 모든 인스턴스를 동기화 해줌