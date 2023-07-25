package hello.core.beanfind;

import hello.core.AppConfig;
import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * BeanFactory와 ApplicationContext
 *
 * BeanFactory
 * 스프링 컨테이너의 최상위 인터페이스이다.
 * 스프링 빈을 관리하고 조회하는 역할을 담당한다.
 * getBean()을 제공한다.
 * 지금까지 우리가 사용했던 대부분의 기능은 BeanFactory가 제공하는 기능이다.
 *
 * ApplicationContext
 * BeanFactory의 기능을 모두 상속받아서 제공한다.
 * 빈을 관리하고 검색하는 BeanFactory가 있지만 부가기능을 위해서 만들어짐
 *
 * 1. 메시지소스를 활용한 국제화 기능
 * 지역에 따른 언어로 처리를 할 수 있다.
 *
 * 2. 환경변수
 * 로컬, 개발, 운영등을 구분해서 처리
 *
 * 3. 어플리케이션 이벤트
 * 이벤트를 발행하고 구독하는 모델을 편리하게 지원
 *
 * 4. 편리한 리소스 조회
 * 파일, 클래스패스, 외부등에서 리소스를 편리하게 조회
 */
class ApplicationContextInfoTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    @Test
    @DisplayName("모든 빈 출력하기")
    // 스프링 내부적으로 확장하기 위한 빈까지 전부 가져온다.
    void findAllBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            Object bean = ac.getBean(name);
            System.out.println("name = " + name);
            System.out.println("Object = " + bean);
        }
    }

    @Test
    @DisplayName("애플리케이션 빈 출력하기")
    void findApplicationBean() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(name);

            // ROLE_APPLICATION: 유저가 직접 등록한 어플리케이션 빈
            // ROLE_INFRASTRUCTURE: 스프링이 내부에서 사용하는 빈
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                Object bean = ac.getBean(name);
                System.out.println("name = " + name);
                System.out.println("Object = " + bean);
            }
        }
    }

    @Test
    @DisplayName("빈 이름으로 조회하기")
    void findByName() {
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        System.out.println("memberService = " + memberService);
        System.out.println("memberService.getClass = " + memberService.getClass());
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름없이 타입으로만 조회하기")
    void findByType() {
        MemberService memberService = ac.getBean(MemberService.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("구체 타입으로 조회")
    void findByDetailType() {
        // 스프링의 인스턴스 타입을 보고 결정하기 때문에 상관이 없음
        MemberService memberService = ac.getBean(MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("빈 이름으로 찾기 못한 경우")
    void findByNameFail() {
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> ac.getBean("xxxx", MemberService.class));
    }

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 둘 이상 있으면, 중복 오류가 발생한다.")
    void findBeanByTypeDuplicate() {
        AnnotationConfigApplicationContext ac2 = new AnnotationConfigApplicationContext(SameBean.class);
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac2.getBean(MemberRepository.class));
    }

    @Test
    @DisplayName("타입으로 조회시 같은 타입이 두개 이상 있으면 빈 이름을 지정하면 됨.")
    void findByBeanName() {
        AnnotationConfigApplicationContext ac2 = new AnnotationConfigApplicationContext(SameBean.class);
        MemberRepository memberRepository = ac2.getBean("memberRepository2", MemberRepository.class);
        assertThat(memberRepository).isInstanceOf(MemberRepository.class);
    }

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면 중복 오류가 발생한다.")
    void findBeanByParentTypeDuplicate() {
        AnnotationConfigApplicationContext ac3 = new AnnotationConfigApplicationContext(TestConfig.class);
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac3.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모 타입을 Object로 하고 전부 조회하기")
    void findBeanAll() {
        AnnotationConfigApplicationContext ac3 = new AnnotationConfigApplicationContext(TestConfig.class);
        Map<String, Object> beansOfType = ac3.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key);
            System.out.println("value = " + beansOfType.get(key));
        }
    }

    /**
     * BeanDefinition 정보
     *
     * BeanClassName: 생성할 빈의 클래스 명(자바 설정 처럼 팩토리 역할의 빈을 사용하면 없음)
     * factoryBeanName: 팩토리 역할의 빈을 사용할 경우 이름, 예) appConfig
     * factoryMethodName: 빈을 생성할 팩토리 메서드 지정, 예) memberService
     * Scope: 싱글톤(기본값)
     * lazyInit: 스프링 컨테이너를 생성할 때 빈을 생성하는 것이 아니라, 실제 빈을 사용할 때 까지 최대한 생성을 지연처리 하는지 여부
     * InitMethodName: 빈을 생성하고, 의존관계를 적용한 뒤에 호출되는 초기화 메서드 명
     * DestroyMethodName: 빈의 생명주기가 끝나서 제거하기 직전에 호출되는 메서드 명
     * Constructor arguments, Properties: 의존관계 주입에서 사용한다. (자바 설정 처럼 팩토리 역할의 빈을 사용하면 없음)
     */
    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationMetaInfo() {
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        for (String name : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(name);

            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("name = " + name);
                System.out.println("beanDefinition = " + beanDefinition);
            }
        }
    }

    @Configuration
    static class SameBean {
        @Bean
        public MemberRepository memberRepository1() {
            return new MemoryMemberRepository();
        }

        @Bean
        public MemberRepository memberRepository2() {
            return new MemoryMemberRepository();
        }
    }

    @Configuration
    static class TestConfig {
        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}
