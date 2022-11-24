package server.twalk.PvP.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import server.twalk.Common.entity.EntityDate;
import server.twalk.Member.entity.Member;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Table(name="pvp_match")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PvpMatch extends EntityDate {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE3")
    @SequenceGenerator(name="SEQUENCE3", sequenceName="SEQUENCE3", allocationSize=1)
    @Column(name = "pvp_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "pvp_mode")
    private PvpMode pvpMode;

    @OneToOne
    @JoinColumn(name = "status")
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "requester_id",
            nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member requester;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "acceptor_id",
            nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member acceptor;
    @Column
    private Long winnerId;

}
