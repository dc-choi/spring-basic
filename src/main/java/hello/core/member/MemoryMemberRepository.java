package hello.core.member;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class MemoryMemberRepository implements MemberRepository {
    // HashMap을 사용하는 것은 동시성 문제가 있어서 실무에서는 사용하지 않음.
    // https://www.inflearn.com/questions/347336/threadlocal-%EB%8F%99%EC%8B%9C%EC%84%B1-%EC%9D%B4%EC%8A%88-arraylist-hashmap-hashset
    // https://applepick.tistory.com/124
    private static Map<Long, Member> store = new HashMap<>();

    @Override
    public void save(Member member) {
        store.put(member.getId(), member);
    }

    @Override
    public Member findById(Long memberId) {
        return store.get(memberId);
    }
}
