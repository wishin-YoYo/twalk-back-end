package server.twalk.Walking.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name="latLonPair")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LatLonPair {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE3")
    @SequenceGenerator(name="SEQUENCE3", sequenceName="SEQUENCE3", allocationSize=1)
    @Column(name = "latlon_id")
    private Long id;

    private Double lat;

    private Double lon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "walking")
    private Walking walking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jalking")
    private Jalking jalking;

    public LatLonPair(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}