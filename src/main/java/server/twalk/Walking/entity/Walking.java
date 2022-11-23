package server.twalk.Walking.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import server.twalk.Common.entity.EntityDate;
import server.twalk.Member.entity.Member;
import server.twalk.PvP.entity.Status;

import javax.persistence.*;

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
    private Member user;

    @Column // polygon 에 찍을 마커 - 위도, 경도
    private LatLonPair latLonPair = new LatLonPair();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true  )
    private Status status;

    private double distance;

    public void addLatLon(double lat_ele, double lon_ele){
        latLonPair.addLatLon(lat_ele, lon_ele);
    }

    public void updateStatus(Status status){
        this.status = status;
    }

    public void addDistance(double distance){
        this.distance+=distance;
    }
}
