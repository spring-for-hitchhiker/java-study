1. [JVM](#jvm)
	1. [클래스로더](#classloading-subsystem)
	2. [런타임영역](#runtime-data-area)
	3. [실행 엔진](#execution-engine)
	4. [추가 구성요소](#추가-컴포넌트들)
		1. [GC](#gc)
		2. [JIT](#jit)
		3. [native interface](#native-interface)

## [JVM]
**Write once, Run anywhere**

- Java 바이트코드를 실행시키기 위한 가상머신

JVM 표준 규격
![표준 규격](files/image.png)

JVM은 정해진 규격이 있지 정해진 JVM 이라는 것은 없다.   
각 회사마다 정해진 규격에 맞는 서로 다른 JVM 들이 존재한다고 생각하면 된다.

그리고 JVM에는 대표적인 3개의 메모리 영역이 존재한다.   
1. ClassLoader Subsystem
2. Runtime Data Area
3. Execution Engine

### [ClassLoading Subsystem]

기본적인 클래스 파일을 `load`, `link`, `initialize` 의 세가지 처리과정을 거치고   
Runtime Data Area 의 Method Area 로 보내는 역할을 한다    

~~Class Loader에도 여러 종류가 있으나 대표적인부분만 작성했다.~~

- load   

	필요한 클래스 파일들을 중복되지 않게 Load 한다   
	(이 때 JVM에서는 그저 Class 파일명만 같은게 아닌    
	*Class Loader Name + Package Name + Class Name*    
	이 모두 일치해야 일치한 Class라고 판단한다)   
	다음과 같은 위계 구조, 계층 구조를 가지고 있다   

	1. Bootstrap    
		JVM 기동 시에 가장 먼저 생성되며 $JAVA_HOME/.../rt.jar 를 Load 하는 작업을 수행한다   
		그 후 Object Class 를 포함한 Java API 들을 Load 하게 된다    
		Bootstrap Class Loader 는 부모를 가지지 않는 가장 상위의 Class Loader 로서    
		다른 Class Loader 와 달리 Java 가 아닌 Native Code 로 구현되어 있다   

	2. Extension    
		Bootstrap Class Loader 를 부모로 하고 기본 Java API 를 제외한    
		$JAVA_HOME/.../ext 에 위치한 확장 Class 들을 Load 하는 작업을 수행한다   

	3. Application    
		$Classpath 또는 java.class.path 에 위치한 Class 들을 Load 한다    
   
   
	- 작동원리   
		작동 원리는 다음과 같다   
		Bootstrap 으로 Class 를 먼저 로딩하고   
		찾으면 종료   
		찾지못하면 다음인 Extension ClassLoader 로 넘어가 Class 를 다시 찾는다   
		Application ClassLoader 에서도 찾지못하면   
		throw ClassNotFoundException   
		![작동 순서](files/image-1.png)   

	- 3가지 원칙   
		1. Delegation Principle (위임 원칙)   
			클래스로더는 클래스를 로드할 때 계층적인 구조를 가지며, 로딩 작업을 부모 클래스 로더에게 위임한다   
			(중복 로딩을 방지, 효율적인 클래스 로딩)   

		2. Visibility Principle (가시성 원칙)   
			자식 클래스 로더가 부모 클래스 로더에서 로드한 클래스에 접근할 수 있지만, 그 역은 성립하지 않는다   
			(모듈화와 격리 유지)   
		3. Uniqueness Principle (고유성 원칙)   

			한 클래스는 특정 클래스 로더에 의해 한 번만 로딩되며, JVM 내에서 고유한 식별자를 가진다    
			(메모리 낭비를 방지하고, undefined behavior 방지)     

	- 오류 발생시 **ClassNotFoundException** 과 **NoClassDefFoundError** 을 반환   
		ClassNotFoundException 는 처음부터 클래스가 존재하지 않을 때 나오는 오류이고   
		NoClassDefFoundError 는 종속된 클래스를 찾지 못했을 때 나오는 오류이다   

- link   
	클래스나 인터페이스를 가져와 실행될 수 있도록 Java Virtual Machine의 런타임 상태로 결합하는 프로세스

	1. Verify   
		클래스 파일의 유효성을 확인하고, 올바른 형식인지 확인

	2. Prepare   
		클래스의 static variable 들을 위한 메모리 공간을 할당하고 초기화

	3. Resolve   
		심볼릭 참조를 실제 메모리 참조로 변환 (optional)   


- initialize
	클래스 또는 인터페이스의 초기화


### [Runtime Data Area]

1. PC Register   
	컴퓨터의 레지스터와는 달리 Stack-Based 로 이루어져 있다    
	현재 실행중인 JVM 명령어의 주소를 포함하며 각 Thread 별로 하나 씩 존재한다   
	특이점으로는 실행된 Thread 가 Native 로 이루어졌다면 Register의 값은 undefined 가 된다   
	위의 특징으로 플랫폼에 종속되지 않는 JVM 의 모습을 보여준다   

2.  Heap   
	모든 JVM Thread 들에게 공유되며 모든 Class Instance, array 들이 할당되는 메모리 영역이다   
	가상머신이 시작될 때 생성이되며 자동적으로 메모리가 회수되며([GC](#gc)) 명시적으로 해제하지 않아도 된다   
	크기가 정해질 수도, 늘려질 수도, 줄어들 수도 있으며 할당된 데이터는 연속적일 필요가 없다   
	- 오류 발생시 **OutOfMemoryError.**
		모든 영역을 공유하기에 동기화 문제가 발생할 수 있다.   

3. Method Area   
	모든 JVM Thread 들 간에 공유되는 Method 영역으로 텍스트를 저장하는 메모리 영역과 유사하다   
	대부분의 Method 및 생성자에 대한 코드를 저장한다   
	논리적으로 Heap 의 일부이나 [GC](#gc)나 압축을 선택적으로 구현할 수 있다   
	크기가 정해질 수도, 늘려질 수도, 줄어들 수도 있으며 할당된 데이터는 연속적일 필요가 없다     
	- 오류 발생시 **OutOfMemoryError.**   

4. JVM Stacks   
	Thread 와 동시에 생성되는 전용 Stack   
	각 스택은 [Frame](#https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html#jvms-2.6) 을 저장하며 Local Variable 과 return 값을 보유하고 있으며   
	Frame 을 Push, Pop 하는 경우를 제외하고는 직접 조작되지 않으며   
	때로는 Frame 이 Heap 에 할당 될 수도 있다    
	C언어와 같은 구조로 이루어져 있으나 그 데이터가 연속적일 필요는 없다   
	- 오류 발생시 **StackOverflowError.**   

5. Native Method Stacks   
	다른 코드들로 작성된 Native Method 들을 지원하기 위해 일반적으로 `C Stacks` 이라고하는 기존 스택을 사용할 수 있다
	JVM 내부에 영향을 주지 않기 위해 [JNI](#native-interface) 을 사용하여   
	Native Method Stacks 호출시 새로운 Stack Frame 을 생성, Native Method Stacks 에 Push 한다
	- 오류 발생시 **StackOverflowError.**, **OutOfMemoryError.**   
		계산 이상의 크기 Stack 필요시 **StackOverflowError.**   
		계속해서 확장을 하다가 메모리가 부족, 초기 Native Method Stack 생성의 메모리가 부족한 경우   
		**StackOverflowError.**     

### [Execution Engine]
	인터프리터와 [JIT](#jit) 로 구성되어 Runtime Data Area 의 내용을 실행하여 구현한다


#### 출처
[Oracle Doc](https://docs.oracle.com/javase/specs/jvms/se8/html/jvms-2.html)      
[Oracle Doc2](https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-5.html#jvms-5.3)      
[wiki](https://en.wikipedia.org/wiki/Java_virtual_machine)      
[Data on air](https://dataonair.or.kr/db-tech-reference/d-lounge/technical-data/?mod=document&uid=235933)   
[infoworld](https://www.infoworld.com/article/3700054/all-about-java-class-loaders.html)    

## 추가 컴포넌트들
Implementation details that are not part of the Java Virtual Machine's specification would unnecessarily constrain the creativity of implementors. For example, the memory layout of run-time data areas, the garbage-collection algorithm used, and any internal optimization of the Java Virtual Machine instructions (for example, translating them into machine code) are left to the discretion of the implementor.    
(~~Java Virtual Machine 사양의 일부가 아닌 구현 세부 사항은 구현자의 창의성을 불필요하게 제한합니다. 예를 들어, 런타임 데이터 영역의 메모리 레이아웃, 사용된 가비지 수집 알고리즘, Java Virtual Machine 명령어의 내부 최적화(예: 기계어 코드로 변환)는 구현자의 재량에 달려 있습니다~~)

대표적인 종류로는 다음과 같다.

1. Garbage Collector (GC)
2. Just In Time Compiler (JIT Compiler)
3. Native Interface

### [GC]
힙 메모리에서 참조, 비참조 객체를 구분해 비사용 객체를 삭제하는 프로세스

- 작동방식

1. Marking   
	참조, 비참조 객체를 식별한다   
	![Marking](files/image-2.png)   
2. Deletion   
	비참조 객체를 삭제   
	1. Normal Deletion   
		비참조 객체를 삭제하고 참조 객체를 내버려둔다   
		![Normal Deletion](files/image-3.png)   

	2. Deletion with Compacting   
		비참조 객체를 삭제하고 참조 객체들을 압축한다   
		![Deletion with Compacting](files/image-4.png)   

- 그리고 GC 는 시간이 아닌 세대별로 작동을 하는데 그 이유는 다음과 같다   

	1. 대부분의 객체는 수명이 짧다   

	2. 개체가 너무 늘어나게되면 GC 의 시간이 너무 길어지게된다   

	![객체의 수명](files/image-5.png)

- JVM 세대

	1. Young Generation   
		모든 새로운 객체가 할당되고 노화되는 곳   
		가득 차면 작은 단위의 GC(Stop the World) 가 발생   

	2. Old Generation   
		일반적으로 Young Generation 개체에 대해 임계값이 설정되고    
		해당 Age가 충족되면 개체가 Old Generation으로 이동      
		이곳에서는 major garbage collection 가 발생   

	3. Permanent Generation   
		클래스와 메소드를 설명하는 데 필요한 메타데이터가 포함   
		애플리케이션에서 사용 중인 클래스를 기반으로 런타임 시 JVM에 의해 채워진다   
		Java SE 라이브러리 클래스 및 메소드가 여기에 저장될 수 있다   

	4. Stop the World     
		모든 작은 GC 를 통칭하는 말   
		작업이 완료될 때까지 모든 애플리케이션 스레드가 중지된다   

	5. major garbage collection   
		모든 라이브 객체를 포함하기 때문에 속도가 훨씬 느린 경우가 많다   
		그렇기에 반응형 애플리케이션의 경우 최소화해야한다   


	![세대](files/image-6.png)

[data on air](https://dataonair.or.kr/db-tech-reference/d-lounge/technical-data/?mod=document&uid=237379)
[oracle](https://www.oracle.com/webfolder/technetwork/tutorials/obe/java/gc01/index.html)

### [JIT]

가상환경에서 실시간으로 코드를 컴파일하여 바이트코드를 기계어로 변환하는 컴파일러

[oracle](https://docs.oracle.com/en/database/oracle/oracle-database/21/jjdev/Oracle-JVM-JIT.html#GUID-23D5BA60-A2B3-45F9-93DF-81A3D971CA50)

### [Native Interface]

Java 코드가 네이티브 애플리케이션(하드웨어 및 운영 체제 에 특정한 프로그램) 에 의해 호출될 수 있도록 하는    
외부 함수 인터페이스 프로그래밍 프레임워크 이며, C/C++ 및 어셈블리 와 같은 다른 언어로 작성된 라이브러리

