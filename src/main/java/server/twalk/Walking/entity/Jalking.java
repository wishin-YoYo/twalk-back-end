package server.twalk.Walking.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import server.twalk.Common.entity.EntityDate;
import server.twalk.Member.entity.Member;
import server.twalk.PvP.entity.Status;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Table(name="jalking")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Jalking extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE3")
    @SequenceGenerator(name="SEQUENCE3", sequenceName="SEQUENCE3", allocationSize=1)
    @Column(name = "jalking_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "request_id",
            nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member requester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "receiver_id",
            nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member receiver;

    @Lob
    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "status_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Status status;

    // 함께 걸은 거리
    @OneToMany(mappedBy = "jalking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LatLonPair> latLonPair = new ArrayList<>();

    public void setStatus(Status status){
        this.status = status;
    }

    public void addLatLon(LatLonPair latLonPair){
        this.latLonPair.add(latLonPair);
    }


}
