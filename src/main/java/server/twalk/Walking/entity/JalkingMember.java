package server.twalk.Walking.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import server.twalk.Common.entity.EntityDate;
import server.twalk.Member.entity.Member;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
public class JalkingMember extends EntityDate {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "member")
        private Member member;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "jalking")
        private Jalking jalking;

        public JalkingMember(Member member, Jalking jalking) {
            this.member = member;
            this.jalking = jalking;
        }
}
