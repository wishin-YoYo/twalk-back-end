package server.twalk.PvP.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;
import server.twalk.Common.entity.EntityDate;
import server.twalk.Member.entity.Member;
import server.twalk.Walking.entity.LatLonPair;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "requester_id",
            nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "receiver_id",
            nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member receiver;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "winner_id",
            nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member winner;

    @Lob
    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "status_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Status status;

    // 만나기로 한 위치
    @OneToOne(cascade = CascadeType.ALL)
    private LatLonPair targetLocation;

    public void setStatus(Status status){
        this.status = status;
    }

    // run 모드라면 도착할 위치
    public void setTargetLocation(LatLonPair targetLocation) {
        this.targetLocation = targetLocation;
    }

    public void setWinner(@Nullable Member winner) {
        this.winner = winner;
    }
}
