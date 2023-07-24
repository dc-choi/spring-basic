package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * IoC (Inversion of Control)
 * 기존에는 클라이언트 구현체가 스스로 필요한 서버 구현 객체를 생성하고 연결하고 실행함.
 * 한마디로 구현 객체가 프로그램의 제어 흐름을 스스로 조종하였고 이는 개발자 입장에서 자연스러운 흐름이다.
 *
 * 반면에 AppConfig가 등장한 이후에 구현 객체는 자신의 로직을 실행하는 역할만 담당한다.
 * 프로그램의 제어 흐름은 이제 AppConfig가 가져간다. 모든 구현 객체는 AppConfig가 생성한다.
 *
 * 프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것이 제어의 역전이라고 한다.
 */
@Configuration
public class AppConfig {
    // 빈 이름은 무조건 다른 이름을 부여해야 한다. 이름이 같으면 충돌의 위험성이 있다.
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
