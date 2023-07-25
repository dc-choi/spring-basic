package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class SingletonTest {
    /**
     * 지금 만든 순수한 DI 컨테이너는 요청할 때마다 객체를 새로 생성한다.
     * 고객 트래픽이 초당 100개씩 나오면 초당 100개의 객체가 생성되고 소멸한다 -> 메모리 낭비가 심함.
     * 해결방안은 해당 객체가 딱 1개만 생성되고 공유하도록 설계하면 된다. -> 싱글톤 패턴
     */
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        // 1. 조회: 호출할 때 마다 객체를 생성
        MemberService memberService1 = appConfig.memberService();
        MemberService memberService2 = appConfig.memberService();

        // 2. 참조값이 다른 것을 확인
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("싱글톤 패턴을 적용한 객체 사용")
    void singletonServiceTest() {
        SingletonService instance = SingletonService.getInstance();
        SingletonService instance2 = SingletonService.getInstance();

        // 참조값이 같은 것을 확인
        assertThat(instance).isSameAs(instance2);
    }

    /**
     * 스프링 컨테이너는 싱글톤의 문제점을 해결하면서 장점만을 사용함.
     * 스프링 컨테이너는 싱글톤 컨테이너 역할을 한다. 이렇게 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤 레지스트리라고 한다.
     * 스프링 컨테이너의 이런 기능 덕분에 싱글톤 패턴의 모든 단점을 해결하면서 객체를 싱글톤으로 유지할 수 있다.
     *
     * 고객의 요청이 올 때 마다 이미 만들어진 객체를 공유해서 효율적으로 재사용할 수 있다.
     *
     * 참고: 스프링의 기본 빈 등록 방식은 싱글톤이지만, 싱글톤 방식만 지원하는 것은 아니다.
     * 요청할 때마다 새로운 객체를 생성해서 반환하는 기능도 제공한다. 자세한 내용은 뒤에 빈 스코프에서 설명...
     */
    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        // 스프링 컨테이너 생성
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 조회: 호출할 때마다 같은 객체를 반환
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        // 참조값이 같은 것을 확인.
        assertThat(memberService).isSameAs(memberService2);
    }
}
