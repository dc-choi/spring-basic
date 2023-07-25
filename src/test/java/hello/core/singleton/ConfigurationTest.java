package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberRepository;
import hello.core.member.MemberServiceImpl;
import hello.core.order.OrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {
    // @Configuration은 내부적으로 싱글톤을 보장하도록 하는 어노테이션이다.
    // @Bean만 사용할 경우 싱글톤을 보장하지 않는다.
    @Test
    @DisplayName("@Configuration 테스트")
    void testConfiguration() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);

        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        assertThat(memberRepository1).isSameAs(memberRepository2);
    }

    @Test
    @DisplayName("@Configuration 심층 분석")
    void testConfigurationDeep() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        // CGLIB라는 바이크코드 조작 라이브러리를 사용해서 AppConfig 클래스를 상속받은 임의의 다른 클래스를 만듬
        // 그 다른 클래스를 스프링 빈으로 등록한 것이다. 그 클래스가 싱글톤이 보장되도록 해준다.
        // @Configuration 어노테이션을 제거하면 AppConfig 클래스가 나옴.
        System.out.println("bean = " + bean.getClass()); // bean = class hello.core.AppConfig$$SpringCGLIB$$0
    }
}
