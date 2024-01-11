# 과제 04: 제어문

- [과제 04: 제어문](#과제-04-제어문)
    - [실습 00: JUnit 5 학습하세요.](#실습-00-junit-5-학습하세요)
        - [build.gradle](#buildgradle)
        - [Maven Repository](#maven-repository)
        - [JUnit 5 소개](#junit-5-소개)
        - [IntelliJ에서 JUnit 5 사용하기](#intellij에서-junit-5-사용하기)
            - [테스트 클래스 생성하기](#테스트-클래스-생성하기)
        - [JUnit 5 어노테이션](#junit-5-어노테이션)

## 실습 00: JUnit 5 학습하세요.

### build.gradle

**Gradle**이란 빌드 자동화 도구이다. Gradle은 Groovy를 이용하여 빌드 스크립트를 작성한다.
Gradle은 Maven과 Ant의 장점을 모두 취하였다.
Maven의 중앙 저장소를 그대로 사용할 수 있으며 Ant처럼 유연하고 간결한 빌드 스크립트를 작성할 수 있다.

**Ant**란 빌드 자동화 도구이다. XML을 이용하여 빌드 스크립트를 작성하며 `build.xml`로 관리된다.
Ant는 빌드 스크립트를 작성하기 위해 XML을 사용하기 때문에 빌드 스크립트가 복잡하고 길어진다.
또한 Ant는 remote repository를 사용할 수 없다.
즉, Maven이나 Gradle을 사용할 때는 [Maven Repository](https://mvnrepository.com/)에서
의존성을 받아서 사용할 수 있지만 Ant는 직접 jar 파일을 다운로드 받아서 사용해야 한다.

**Maven**은 Ant의 단점을 보완하는 빌드 자동화 도구이다.
Maven은 [Maven Repository](https://mvnrepository.com/)에서
의존성을 받아서 사용할 수 있으며, `pom.xml` 파일로 관리된다.
Ant와 마찬가지로 XML을 이용하여 빌드 스크립트를 작성하며 Ant에 비해 문법이 간결하다.
하지만 Ant에 비해 유연성이 떨어진다는 단점이 있으며,
여전히 XML을 사용하기 때문에 스크립트가 길고 복잡하다.

반면 **Gradle**은 Groovy 혹은 Kotlin을 사용하기 때문에 스크립트가 비교적 간결하다.
Gradle은 Maven의 중앙 저장소를 그대로 사용할 수 있으며
Ant처럼 유연한 빌드 스크립트를 작성할 수 있다.
또한 멀티 프로젝트 빌드 지원하며, 캐시를 사용하여 빌드 속도가 빠르다.
Gradle을 사용하기 위해서는 `build.gradle` 파일을 작성해야 한다.

### Maven Repository

Maven Repository link: https://mvnrepository.com/

위에서 설명했듯이 Maven과 Gradle은 `Maven Repository`에서 의존성을 받아서 사용할 수 있다.
Maven Repository에서는 다양한 라이브러리를 제공하고 있으며, 이를 사용하기 위해서는
`build.gradle` 파일에 의존성을 추가하기만 하면 된다.

```groovy
// ...

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter-api:5.10.1'
    testRuntimeOnly 'org.junit.jupiter:junit-jupiter-engine:5.10.1'
    testRuntimeOnly 'org.junit.vintage:junit-vintage-engine:5.10.1' // for JUnit 4
}
```

> `implementation`은 컴파일과 런타임에 사용된다.  
> `testImplementation`은 테스트를 컴파일할 때와 런타임에서 사용되고,  
> `testRuntimeOnly`는 테스트 런타임에만 사용된다.

### JUnit 5 소개

`JUnit`은 자바 애플리케이션을 테스트하는 데 널리 사용되는 **테스트 프레임워크**이다.
JUnit5는 테스트 engine을 도입하여 더욱 확장 가능한 아키텍처를 제공하고,
JUnit Jupiter를 통해 기존의 테스트 프레임워크보다 더 유연하고 강력한 기능을 제공한다.

> JUnit4 하휘 호환을 위해서는 org.junit.vintage:junit-vintage-engine 의존성을 추가해야 한다.

### IntelliJ에서 JUnit 5 사용하기

JUnit 5를 사용하기 위해서는 `build.gradle` 파일에 의존성을 추가해야 한다.
그리고 IntelliJ에서는 `build.gradle` 파일을 열고 `import gradle project`를 클릭하면
Gradle 프로젝트를 IntelliJ에서 사용할 수 있다.

#### 테스트 클래스 생성하기

IntelliJ에서 Gradle 프로젝트를 생성하면 `src/main/java`와 `src/test/java` 디렉토리가 생성된다.
`src/main/java` 디렉토리는 소스 코드를 저장하는 디렉토리이고, `src/test/java` 디렉토리는 테스트 코드를 저장하는 디렉토리이다.
`src/main/java`에서 클래스를 생성하고 `cmd + shift + t`를 누르면 간단하게 테스트 클래스를 생성할 수 있다.

JUnit 5를 사용하기 위해서는 `@Test` 어노테이션을 사용해야 한다.
JUnit 5에서는 `@Test` 어노테이션을 사용할 때 `org.junit.jupiter.api.Test`를 사용해야 한다.
또한 `@DisplayName` 어노테이션을 사용하여 테스트 메서드의 이름을 지정할 수 있다.

```java
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class JunitStudyTest {
    @Test
    @DisplayName("Junit5 테스트")
    void test() {
        JunitStudy junitStudy = new JunitStudy();
        String result = junitStudy.sayHello("Junit5!");
        Assertions.assertEquals("Hello Junit5!", result);
    }
}
```

### JUnit 5 어노테이션

| 어노테이션          | 설명                              |
|----------------|---------------------------------|
| `@Test`        | 테스트 메서드임을 나타낸다.                 |
| `@DisplayName` | 테스트 메서드의 이름을 지정한다.              |
| `@BeforeAll`   | 모든 테스트 메서드가 실행되기 전에 딱 한 번 실행된다. |
| `@AfterAll`    | 모든 테스트 메서드가 실행된 후에 딱 한 번 실행된다.  |
| `@BeforeEach`  | 각 테스트 메서드가 실행되기 전에 실행된다.        |
| `@AfterEach`   | 각 테스트 메서드가 실행된 후에 실행된다.         |
| `@Disabled`    | 테스트를 비활성화한다.                    |

> `@BeforeAll`과 `@AfterAll`은 반드시 `static` 메서드로 선언되어야 한다.

> `@BeforeEach`와 `@AfterEach`는 인스턴스 메서드로 선언한다.  

> `@Disabled`는 테스트를 비활성화되어 실행되지 않는다.
> 비활성화를 하면 테스트를 잊어버리지 않기 위해 주석처리를 하지 않아도 된다는 장점이 있다.