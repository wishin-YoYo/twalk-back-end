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
@Table(name="walking")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Walking extends EntityDate {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE3")
    @SequenceGenerator(name="SEQUENCE3", sequenceName="SEQUENCE3", allocationSize=1)
    @Column(name = "walking_id")
    private Long id;

    @Column
    private Integer walkingCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    @OneToMany(mappedBy = "walking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LatLonPair> latLonPair = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "status_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Status status;

    private double distance;

    public void addLatLon(LatLonPair latLonPair){
        this.latLonPair.add(latLonPair);
    }

    public void updateStatus(Status status){
        this.status = status;
    }

    public void addDistance(double distance){
        this.distance+=distance;
    }
}
