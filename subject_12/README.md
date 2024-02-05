# 과제 12: 어노테이션

- [과제 12: 어노테이션](#과제-12-어노테이션)
  - [어노테이션이란?](#어노테이션이란)
  - [어노테이션 적용](#어노테이션-적용)
  - [애노테이션 정의](#애노테이션-정의)
  - [어노테이션의 종류](#어노테이션의-종류)
  - [커스텀 어노테이션](#커스텀-어노테이션)
    - [@Retention](#retention)
    - [@Target](#target)
    - [@Documented](#documented)
    - [애노테이션 프로세서](#애노테이션-프로세서)

## 어노테이션이란?

기존에는 주석이나 XML 파일에 설정 정보를 저장하여 사용하였다.
하지만, 이러한 설정 정보들은 프로그램 코드와 분리되어 있어서 유지보수가 어려웠다.
이러한 문제를 해결하기 위해 자바 5부터 어노테이션이라는 기능이 추가되었다.

어노테이션은 자바 소스 코드에 추가 정보를 제공하는 메타데이터이다.  
어노테이션의 용도는 다음과 같다.

* 컴파일러에게 정보를 제공한다. (예: `@Override`, `@Deprecated`)
* 컴파일된 클래스에 추가적인 메타데이터를 제공한다. (예: `@Entity`, `@Table`)
* 런타임에 리플렉션을 이용하여 정보를 읽어온다. (예: `@Rentention`, `@Target`)

```java
@MyClassAnnotation(value=123, pivot=356)
public class 클래스이름 {
	@MyMethodAnnotation(value=45, pivot=73)
	public void 메서드이름(@MyVarAnnotation(value=14, pivot=54) int 매개변수) {
		...
	}
}
```

## 어노테이션 적용

어노테이션은 클래스, 메서드, 변수, 매개변수 등에 적용할 수 있다.
어노테이션을 적용할 때는 `@` 기호를 사용하여 어노테이션의 이름을 적어주면 된다.
어노테이션의 이름 뒤에는 괄호를 사용하여 속성을 지정할 수 있다.

## 애노테이션 정의

```java
public @interface MyAnnotation {
	String value();
	int pivot() default 20;	// 기본값 지정 가능
}
```

어노테이션을 정의할 때는 `@interface` 키워드를 사용한다.
어노테이션의 속성을 지정할 때는 메서드와 같이 속성의 이름과 타입을 지정하면 된다.
어노테이션의 속성을 지정할 때는 `default` 키워드를 사용하여 기본값을 지정할 수 있다.
어노테이션의 속성 타입은 `기본형`, `String`, `enum`, `어노테이션`, `Class`만 사용할 수 있다.

## 어노테이션의 종류

`JVM`에서 제공하는 어노테이션은 크게 두 가지로 분류된다.

* `Built-in Annotation` : 자바에서 기본적으로 제공하는 어노테이션
    * `@Override` : 메서드가 오버라이딩 되었는지 검사한다.
    * `@Deprecated` : 사용하지 말아야 할 메서드를 알려준다.
    * `@SuppressWarnings` : 컴파일러 경고를 무시하도록 한다.
    * `@SafeVarargs` : 제네릭 가변인자 메서드나 생성자에 사용한다.
    * `@FunctionalInterface` : 함수형 인터페이스를 나타낸다.
    * `@Native` : 네이티브 메서드에 사용한다.

* `Meta Annotation` : 커스텀 어노테이션을 만들수 있게 제공된 어노테이션.
    * `@Retention` : 어노테이션을 언제까지 유지할 것인지를 지정한다.
    * `@Target` : 어노테이션을 적용할 대상을 지정한다.
    * `@Documented` : 어노테이션 정보가 javadoc으로 작성된 문서에 포함되도록 한다.
    * `@Inherited` : 어노테이션이 상속되도록 한다.
    * `@Repeatable` : 어노테이션을 반복해서 사용할 수 있도록 한다.

## 커스텀 어노테이션

### @Retention

`@Retention` 어노테이션은 어노테이션을 언제까지 유지할 것인지를 지정한다.
`@Retention` 어노테이션은 `java.lang.annotation` 패키지에 정의되어 있다.

```java
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String value();
    int pivot() default 20;
}
```

`@Retention` 어노테이션은 `RetentionPolicy` 열거형 상수를 사용하여 어노테이션의 유지 정책을 지정한다.
`RetentionPolicy` 열거형 상수는 다음과 같다.

* `SOURCE` : 소스 파일에만 존재하고 클래스 파일에는 존재하지 않는다. (예: `@Getter`, `@Setter`)
* `CLASS` : 클래스 파일에 존재하고 런타임에는 존재하지 않는다. (예: `@Notnull`, `@Nullable`)
* `RUNTIME` : 클래스 파일에 존재하고 런타임에도 존재한다. (예: `@Component`, `@Autowired`)

### @Target

`@Target`은 어노테이션을 적용할 대상을 지정한다.
`@Target` 어노테이션은 `java.lang.annotation` 패키지에 정의되어 있다.

```java
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface MyAnnotation {
    String value();
    int pivot() default 20;
}
```

`@Target` 어노테이션은 `ElementType` 열거형 상수를 사용하여 어노테이션의 적용 대상을 지정한다.
`ElementType` 열거형 상수는 다음과 같다.

* `ANNOTATION_TYPE` : 어노테이션
* `CONSTRUCTOR` : 생성자
* `FIELD` : 필드
* `LOCAL_VARIABLE` : 지역변수
* `METHOD` : 메서드
* `PARAMETER` : 매개변수
* `TYPE` : 클래스, 인터페이스, 열거형

### @Documented

`@Documented` 어노테이션은 어노테이션 정보가 `javadoc`으로 작성된 문서에 포함되도록 한다.
`@Documented` 어노테이션은 `java.lang.annotation` 패키지에 정의되어 있다.

```java
@Documented
public @interface MyAnnotation {
    String value();
    int pivot() default 20;
}
```

### 애노테이션 프로세서

애노테이션 프로세서는 컴파일 시점에 애노테이션을 처리하는 프로그램이다.
애노테이션 프로세서는 `javax.annotation.processing` 패키지에 정의되어 있다.

애노테이션 프로세서를 만들기 위해서는 `AbstractProcessor` 클래스를 상속받아야 한다.
애노테이션 프로세서를 만들기 위해서는 다음과 같은 과정을 거쳐야 한다.

1. `AbstractProcessor` 클래스를 상속받는다.
2. `@SupportedAnnotationTypes` 어노테이션을 사용하여 처리할 애노테이션을 지정한다.
3. `@SupportedSourceVersion` 어노테이션을 사용하여 처리할 소스 버전을 지정한다.
4. `process` 메서드를 오버라이딩하여 애노테이션을 처리한다.

```java
@SupportedAnnotationTypes("com.example.MyAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MyAnnotationProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (TypeElement typeElement : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
                ...
            }
        }
        return true;
    }
}
```

애노테이션 프로세서를 사용하기 위해서는 `META-INF/services/javax.annotation.processing.Processor` 파일을 만들어야 한다.
이 파일에는 애노테이션 프로세서의 클래스 이름을 적어주면 된다.

```java
com.example.MyAnnotationProcessor
```