package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * 애플리케이션은 크게 업무 로직과 기술 지원 로직으로 나눌 수 있다.
 * 업무 로직 빈: 웹을 지원하는 컨트롤러, 핵심 비즈니스 로직이 있는 서비스, 데이터 계층의 로직을 처리하는 리포지토리등이 모두 업무 로직이다. 보통 비즈니스 요구사항을 개발할 때 추가되거나 변경된다.
 * 기술 지원 빈: 기술적인 문제나 공통 관심사(AOP)를 처리할 때 주로 사용된다. 데이터베이스 연결이나, 공 통 로그 처리 처럼 업무 로직을 지원하기 위한 하부 기술이나 공통 기술들이다.
 *
 * 업무 로직은 숫자도 매우 많고, 한번 개발해야 하면 컨트롤러, 서비스, 리포지토리 처럼 어느정도 유사한 패 턴이 있다.
 * 이런 경우 자동 기능을 적극 사용하는 것이 좋다. 보통 문제가 발생해도 어떤 곳에서 문제가 발생 했는지 명확하게 파악하기 쉽다.
 *
 * 기술 지원 로직은 업무 로직과 비교해서 그 수가 매우 적고, 보통 애플리케이션 전반에 걸쳐서 광범위하게 영향을 미친다.
 * 그리고 업무 로직은 문제가 발생했을 때 어디가 문제인지 명확하게 잘 드러나지만, 기술 지원 로직은 적용이 잘 되고 있는지 아닌지 조차 파악하기 어려운 경우가 많다.
 * 그래서 이런 기술 지원 로직들은 가급적 수동 빈 등록을 사용해서 명확하게 드러내는 것이 좋다.
 *
 * 다형성을 적극 활용하는 비즈니스 로직은 수동 등록을 고민해보자.
 * 내가 직접 기술 지원 객체를 스프링 빈으로 등록한다면 수동으로 등록해서 명확하게 드러내는 것이 좋다.
 * 만약 다른 개발자가 로직을 개발해서 나에게 준 것이라면 어떨까? 자동 등록을 사용하고 있기 때문에 파악하려면 여러 코드를 찾아봐야 한다.
 * 이런 경우 수동 빈으로 등록하거나 또는 자동으로하면 특정 패키지에 같이 묶어두는게 좋다! 핵심은 딱 보고 이해가 되어야 한다!
 */
@Configuration
// @Component이 붙은 것을 찾는데 @Configuration 어노테이션이 붙은 건 필터링한다.
@ComponentScan(
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
        // basePackages = "hello.core" // 탐색할 패키지의 위치를 직접 지정한다. 단, 이 방법은 권장하지 않는다.
        // 프로젝트 루트에 설정 파일을 두는것을 권고한다.
)
public class AutoAppConfig {
    // 기존과 다르게 @Bean을 하나도 붙이지 않는다.
    // 만약 수동으로 @Bean을 직접 등록하는 경우 컴포넌트 스캔보다 우선순위를 가진다.
    // 하지만 이렇게 되면 여러 설정이 꼬여서 잡기 어려운 버그가 만들어진다.
    // 잡기 어려운 버그는 애매한 버그다. 그래서 최근 스프링 부트에서는 수동 빈 등록과 자동 빈 등록이 충돌나면 오류가 발생하도록 수정함.
}