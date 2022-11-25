package server.twalk.Member.entity;

import lombok.*;
import org.hibernate.annotations.Cascade;
import server.twalk.Common.entity.EntityDate;
import server.twalk.Walking.entity.LatLonPair;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Table(name="member")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends EntityDate {

    // USER INFO
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE3")
    @SequenceGenerator(name = "SEQUENCE3", sequenceName = "SEQUENCE3", allocationSize = 1)
    @Column(name = "member_id")
    private Long id;

    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private boolean activated;

    @Column(nullable = false)
    private boolean showLocation;

    // < MY PROFILE >
    @Column
    private String comment;

    @Column
    private double totalDistance = 0;

    @Column
    private double totalCalories = 0;

    // < PVP PROFILE >
    @Column
    private Integer wins;

    @Column
    private Integer loses;

    @OneToOne(cascade = CascadeType.ALL)
    private LatLonPair latLonPair;

    // 현재 내 위치 보여주기 설정이 t -> f , f -> t
    public void showMyLocation() {
        if (this.showLocation) {
            this.showLocation = false;
        } else {
            this.showLocation = true;
        }
    }

    // 현재 내 위치 업데이트
    public void updateMyLocation(LatLonPair latLonPair) {
        this.latLonPair = latLonPair;
    }

    // 현재 내 activated (걷는 중 여부) t -> f , f -> t
    public void activateMyStatus() {
        if (this.activated) {
            this.activated = false;
        } else {
            this.activated = true;
        }
    }

    public void addTotalDistanceAndCalories(double distance){
        this.totalDistance+=distance;
        this.totalCalories+=distance*0.8;
    }

}