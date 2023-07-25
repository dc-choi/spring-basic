package hello.core.scan;

import hello.core.AutoAppConfig;
import hello.core.member.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @ComponentScan
 * @Component이 붙은 모든 클래스를 스프링 빈으로 등록한다.
 * 이때 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
 * 빈 이름을 직접 지정하고 싶으면 @Component("memberService") 이런식으로 부여하면 된다.
 *
 * 컴포넌트 스캔 기본 대상
 * @Component : 컴포넌트 스캔에서 사용
 * @Controller : 스프링 MVC 컨트롤러에서 사용
 * @Service : 스프링 비즈니스 로직에서 사용
 * @Repository : 스프링 데이터 접근 계층에서 사용
 * @Configuration : 스프링 설정 정보에서 사용
 *
 * 참고: 사실 애노테이션에는 상속관계라는 것이 없다.
 * 그래서 이렇게 애노테이션이 특정 애노테이션을 들고 있는 것을 인식할 수 있는 것은 자바 언어가 지원하는 기능은 아니고, 스프링이 지원하는 기능이다.
 *
 * @Autowired
 * 생성자에 @Autowired를 지정하면 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아서 주입힌다.
 * 기본 조회 전략은 타입이 같은 빈을 찾아서 주입힌다.
 * getBean(MemberRepository.class)와 동일하다고 이해하면 된다.
 *
 * 중복된 빈이 여러개가 있을 경우 중복을 해결하는 여러 방법이 있다.
 *
 * 1. @Autowired 필드명 매칭
 * @Autowired는 타입 매칭을 시도하고 이때 여러 빈이 있으면 필드 이름, 파라미터 이름으로 빈 이름을 추가 매칭한다.
 *
 * 2. @Qualifier("~")
 * 추가 구분자를 붙여주는 방법이다. 주입시 추가적인 방법을 제공하는 것뿐이고 빈 이름을 변경하는건 아니다.
 *
 * 3. @Primary
 * @Autowired에 우선순위를 추가한다. 여러 빈이 매칭되는 경우 해당 에노테이션을 추가해서 사용한다.
 *
 * @Qualifier와 @Primary의 우선순위
 * @Primary는 기본값처럼 동작하고 @Qualifier는 매우 상세하게 동작한다.
 * 이런 경우 스프링은 수동이고 좁은 선택권을 가진 에노테이션이 우선권을 가진다.
 * 그래서 @Qualifier이 우선순위이다.
 */
class AutoAppConfigTest {
    @Test
    @DisplayName("컴포넌트 스캔 방식 이해")
    void AutoAppTest() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);

        MemberService memberService = ac.getBean(MemberService.class);

        assertThat(memberService).isInstanceOf(MemberService.class);
    }
}
