package hello.core.member;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class MemberServiceImplTest {
    private MemberService service = new MemberServiceImpl();

    @Test
    void join() {
        // given
        Member member = new Member(1L, "member", Grade.VIP);
        // when
        service.join(member);
        Member searchMember = service.findMember(1L);
        // then
        assertThat(member).isEqualTo(searchMember);
    }
}