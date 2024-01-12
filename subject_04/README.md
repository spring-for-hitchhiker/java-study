# 과제 04: 제어문

- [과제 04: 제어문](#과제-04-제어문)
  - [선택문](#선택문)
    - [if](#if)
    - [switch](#switch)
      - [java 12 이후](#java-12-이후)
  - [반복문](#반복문)
    - [while](#while)
    - [do while](#do-while)
    - [for](#for)
    - [for-each (향상된 for문)](#for-each-향상된-for문)
      - [Iterator](#iterator)
    - [Stream.forEach()](#streamforeach)

## 선택문

### if

- `C/C++`과 동일하다. (`if`, `else if`, `else`)
- 쇼트 서킷(`Short-circuit`)이 적용된다.
- `else if`, `else`는 가독성을 위해 가급적 사용하지 않는 것이 좋다. (아닌 경우도 있다)
- 조건이 많을 때는 `if-else`를 사용하는 것보다 `switch` 구문을 사용하는 것이 성능 및 가독성 측면에서 좋다.

### switch

```java
public class SwitchExample {
    public static void main(String[] args) {
        int day = 3;
        String dayString;

        switch (day) {
            case 1:
                dayString = "Monday";
                break;
            case 2:
                dayString = "Tuesday";
                break;
            // ... 다른 케이스들
            default:
                dayString = "Invalid day";
                break;
        }

        System.out.println(dayString);
    }
}
```

`switch` 구문은 위와 같이 사용한다. 각 케이스는 숫자 뿐만 아니라 `Enum`, `문자열`, `상수` 등 다양한 값을 사용할 수 있다.

주의해야할 점은 각 케이스마다 `break;`를 사용하지 않으면 다음 케이스도 실행되기 때문에 반드시 `break;`를 사용해야한다.

또한 `default`가 누락되어도 컴파일 오류가 발생하지 않아서 의도하지 않은 `null`이 반환되는 등의 문제가 있을 수 있다.

#### java 12 이후

```java
public class SwitchExample {
    public static void main(String[] args) {
        int day = 3;
        String dayString = switch (day) {
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            // ... 다른 케이스들
            default -> "Invalid day";
        };

        System.out.println(dayString);
    }
}
```

java 12 이후부터는 `->` 연산자를 사용한다.

`break;`를 사용하지 않아도 되고, `default`가 누락되면 컴파일러가 경고를 보여준다.

```java
public class SwitchExample {
    public String dayToString(int day) {
        return switch (day) { // swtich에 대한 결과값을 return 한다.
            case 1 -> "Monday";
            case 2 -> "Tuesday";
            // ... 다른 케이스들
            default -> "Invalid day";
        };
    }
}
```

연산자 처럼 `return` 값을 사용할 수 있다.

```java
public class SwitchExpressionWithYield {
    public static void main(String[] args) {
        int day = 3;
        String result = switch (day) {
            case 1, 2 -> {
                yield "Monday or Tuesday";
            }
            case 3, 4 -> {
                yield "Wednesday or Thursday";
            }
            default -> {
                yield "Invalid day";
            }
        };

        System.out.println(result);
    }
}
```

또한 값을 명확하게 반환하기 위해서 `yield` 예약어가 추가되었다.

## 반복문

### while

- C/C++과 동일

### do while

- C/C++과 동일

### for

- C/C++과 동일
- 조건식 안에서 함수를 호출하면 상황에 따라 성능 저하 발생 가능

```java
/* 리펙토링 전 */
for (int i = 0; i < getSize(); i++) {
    // ...
}
```

```java
/* 리펙토링 후 */
int size = getSize();
for (int i = 0; i < size; i++) {
    // ...
}
```

### for-each (향상된 for문)

```java
int[] numbers = {1, 2, 3, 4, 5};

for (int number : numbers) {
    System.out.println(number);
}
```

Java 5 부터 `향상된 for-each` 구문이 추가되었다.

`for-each` 구문을 사용하기 위해서는 `Iterable` 인터페이스를 구현해야한다.
`Collection` 인터페이스는 `Iterable`을 구현하고 있기 때문에 대부분의 `컬렉션 API`에 대해 `for-each` 구문을 사용할 수 있다.

#### Iterator

`Iterable` 인터페이스는 `Iterator` 인터페이스를 추상메서드로 선언한다.
`Iterator`는 `hasNext()` 메서드를 통해 남아있는 원소가 있는지 확인하고, 남아있는 원소가 있으면 `next()` 메서드를 통해 원소를 가져온다.

즉, `for-each`문은 내부적으로 `래퍼런스`를 사용하기 때문에 순회하는 원소에 대한 변경이 일어나면 `ConcurrentModificationException`이 발생한다. 따라서 `for-each` 구문에서 원소에 대한 변경을 위해서는 `Iterator`에 정의된 함수(`remove()` 등)을 사용해야한다. (`C++`의 `STL`과 유사하다.)

> `HashMap` 같은 경우에는 `ConcurrentHashMap`을 사용하여 해결할 수도 있다.

> 자세한 내용은 공식문서 참고  
> [JavaSE8 - The For-Each Loop](https://docs.oracle.com/javase/8/docs/technotes/guides/language/foreach.html)  
> [JavaSE8 - Iterable](https://docs.oracle.com/javase/8/docs/api/java/lang/Iterable.html)  
> [JavaTutorial - Interface Collection](https://docs.oracle.com/javase/tutorial/collections/interfaces/collection.html)  


### Stream.forEach()

Java 8에서 도입된 람다 표현식을 활용한 함수형 프로그래밍에서 `forEach()` 메서드를 지원한다.

하지만 이는 반복문 보다는 스트림에서 중간 및 최종 연산을 제공하는 메서드로 상황과 목적에 맞게 사용해야한다.

- `for-each`:
  - 코드가 간결하고 명확한 경우
  - 병렬 처리가 필요하지 않고, 외부에서 반복을 명시적으로 제어해야 하는 경우
 
- `Stream.forEach()`:
  - 병렬 처리를 수행해야 하는 경우
  - 필터링, 매핑, 정렬 등 다양한 스트림 연산을 활용하는 경우