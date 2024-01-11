import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class JunitStudyTest {
    @Test
    @DisplayName("Junit5 테스트")  // 테스트 이름을 지정할 수 있음
    void test() {
        JunitStudy junitStudy = new JunitStudy();
        String result = junitStudy.sayHello("Junit5!");
        Assertions.assertEquals("Hello Junit5!", result);
    }

    @Test
    void 테스트_실패() { // 테스트 이름을 지정하지 않으면 메소드 이름이 테스트 이름이 됨
        JunitStudy junitStudy = new JunitStudy();
        String result = junitStudy.sayHello("Junit5!");
        Assertions.assertNotEquals("Hello World!", result);
    }
}