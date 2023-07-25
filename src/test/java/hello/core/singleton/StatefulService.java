package hello.core.singleton;

/**
 * 싱글톤 방식을 사용하기 위해서는 무상태로 설계해야한다.
 * 왜냐하면 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문이다.
 *
 * 그래서..
 * 1. 특정 클라이언트에 의존적인 필드가 있으면 안된다.
 * 2. 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안된다.
 * 3. 가급적 읽기만 가능해야 한다.
 * 4. 필드 대신에 자바에서 공유되지 않는 지역변수, 파라미터, ThreadLocal 등을 사용해야 한다.
 */
public class StatefulService {
    private int price; // 상태를 유지하는 필드

    public void order(String name, int price) {
        System.out.println("name = " + name + ", price = " + price);
        this.price = price; // 여기가 문제!!
    }

    public int getPrice() {
        return price;
    }
}
