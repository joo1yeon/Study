package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor // final이 있는 필드만 생성자를 만들어줌
// @AllArgsConstructor // 모든 필드의 생성자를 만들어줌
// jpa 데이터 로직 변경은 트랜잭션 안에서 반드시 실행되어야 함, 그래야 lazy loading 이런거 가능
// 클래스 레벨에 @T 를 쓰면 public 메소드에 걸려들어감
// Spring 에서 제공하는 어노테이션을 써야 사용 가능한 옵션이 많음
public class MemberService {

    private final MemberRepository memberRepository; // final -> 변경 X, 컴파일 시점에 체크
    // 필드 위에 바로 @Autowired -> 못바꿈 (테스트 등)
    // setter injection -> 파라미터로 주입 가능, but 런타임에 누군가 repository를 바꿀 수 있는 위험
    // 생성자 injection 으로 해결(주입 가능, 런타임에 변경 불가)

    // 생성자가 1개일 경우 @Autowired 생략 가능
    // public MemberService(MemberRepository memberRepository) {
    //     this.memberRepository = memberRepository;
    // }

    /**
     * 회원가입
     */
    @Transactional(readOnly = false)
    // 데이터 변경이 일어나기 때문에 별도로 설정
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
