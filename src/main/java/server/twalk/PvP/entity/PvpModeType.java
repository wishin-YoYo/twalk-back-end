package server.twalk.PvP.entity;


public enum PvpModeType {
    SOGANG( 37.550897,126.940976),
    IFC( 37.526001, 126.925215),
    FLAG(0,0);

    public final double lat;
    public final double lon;

    PvpModeType(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }
}
