## 과제에 대한 질문 답변

### static import와 import의 차이가 무엇인가요?

import는 클래스 또는 패키지를 가져올 때 사용
다른 패키지에 있는 클래스를 현재의 클래스에서 사용할 수 있게 함

static import는 특정 클래스의 static 멤버(메소드 or 변수)를 가져올 때 사용 이를 통해 static 멤버를 클래스 이름 없이 바로 사용할 수 있다.
ex) import static java.lang.Math.sqrt;
sqrt(16)
와 같이 바로 메소드를 사용할 수 있다.

장점으로는 코드가 간결해지지만, 어떤 클래스의 static멤버인지 알아보기 어려운 단점이 있다.