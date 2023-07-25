package hello.core.singleton;

/**
 * 싱글톤 패턴의 문제점
 * 1. 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
 * 2. 의존관계상 클라이언트가 구체 클래스에 의존한다. (DIP 위반)
 * 3. 클라이언트가 구체 클래스에 의존해서 OCP 원칙을 위반할 가능성이 높다.
 * 4. 테스트하기 어렵다.
 * 5. 내부 속성을 변경하거나 초기화 하기 어렵다.
 * 6. private 생성자로 자식 클래스를 만들기 어렵다.
 * 7. 결론적으로 유연성이 떨어지며 안티패턴으로 불린다.
 */
public class SingletonService {
    private static final SingletonService instance = new SingletonService();

    private SingletonService() {
    }

    public static SingletonService getInstance() {
        return instance;
    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출!");
    }
}
