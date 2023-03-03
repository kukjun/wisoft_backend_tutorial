package io.wisoft.tutorial_backend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(unique = true)
    private String email;
    private String password;

    @Column(unique = true)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    /**
     * 정적 생성자
     */
    public static Member newInstance(
            String email,
            String password,
            String nickname,
            MemberRole role
    ) {
        Member member = new Member();
        member.email = email;
        member.password = password;
        member.nickname = nickname;
        member.role = role;
        return member;
    }


}
