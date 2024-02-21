public class Main {
    public static void main(String[] args) {
        // StaticClass의 인스턴스를 생성하고
        StaticClass staticInstance = new StaticClass();

        // StaticClass 내의 static method를 통해 NormalClass의 인스턴스를 생성
        NormalClass normalInstance = staticInstance.createNormalInstance();

        // 생성된 NormalClass의 인스턴스 사용
        normalInstance.printMessage();  // 출력: Hello from NormalClass
    }
}

static class StaticClass {
    // NormalClass의 인스턴스를 생성하는 static method
    public NormalClass createNormalInstance() {
        return new NormalClass();
    }
}

class NormalClass {
    public void printMessage() {
        System.out.println("Hello from NormalClass");
    }
}
