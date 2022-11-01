package server.twalk.PvP.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Getter
@Table(name="status")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE1")
    @SequenceGenerator(name="SEQUENCE1", sequenceName="SEQUENCE1", allocationSize=1)
    @Column(name = "status_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private StatusType statusType;

    public Status(StatusType statusType) {
        this.statusType = statusType;
    }

}
