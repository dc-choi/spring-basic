package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class OrderServiceImplTest {
    private MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;
    private MemberService memberService;
    private OrderService orderService;

    @BeforeEach
    void beforeEach() {
        this.memberRepository = new MemoryMemberRepository();
        this.discountPolicy = new RateDiscountPolicy();
        this.memberService = new MemberServiceImpl(this.memberRepository);
        this.orderService = new OrderServiceImpl(this.memberRepository, this.discountPolicy);
    }

    @Test
    void create() {
        Long memberId = 1L;
        Member member = new Member(memberId, "member", Grade.VIP);
        memberService.join(member);

        Order order = orderService.create(memberId, "item", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}