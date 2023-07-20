package hello.core.order;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class OrderServiceImplTest {
    private MemberService memberService = new MemberServiceImpl();
    private OrderService orderService = new OrderServiceImpl();

    @Test
    void create() {
        Long memberId = 1L;
        Member member = new Member(memberId, "member", Grade.VIP);
        memberService.join(member);

        Order order = orderService.create(memberId, "item", 10000);
        assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}