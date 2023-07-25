package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService bean = ac.getBean(StatefulService.class);
        StatefulService bean2 = ac.getBean(StatefulService.class);

        // 한 쓰레드에서 a가 10000원 주문
        bean.order("a", 10000);
        // 다른 쓰레드에서 b가 20000원 주문
        bean2.order("b", 20000);

        // 한 쓰레드에서 a의 주문 금액 조회
        int price = bean.getPrice();
        // assertThat(price).isEqualTo(10000); // 오류 발생
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}