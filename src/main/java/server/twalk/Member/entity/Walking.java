package server.twalk.Member.entity;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import server.twalk.Common.entity.EntityDate;

import javax.persistence.*;
import java.time.LocalDate;
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
    private LocalDate nowDate;

    @Column
    private Integer walkingCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member user;

    @Column // polygon 에 찍을 마커 - 위도
    private List<Float> lat;

    @Column // polygon 에 찍을 마커 - 경도
    private List<Float> lon;

}
