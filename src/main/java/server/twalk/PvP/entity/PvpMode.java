package server.twalk.PvP.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name="status")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PvpMode {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE1")
    @SequenceGenerator(name="SEQUENCE1", sequenceName="SEQUENCE1", allocationSize=1)
    @Column(name = "pvpType_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PvpModeType pvpModeType;

    public PvpMode(PvpModeType pvpModeType) {
        this.pvpModeType = pvpModeType;
    }
}
