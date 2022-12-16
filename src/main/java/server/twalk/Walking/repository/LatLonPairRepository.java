package server.twalk.Walking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import server.twalk.Walking.entity.LatLonPair;

public interface LatLonPairRepository extends JpaRepository<LatLonPair, Long> {

    @Transactional
    @Modifying
    @Query(value = "insert into LatLong ([컬럼명]) " +
            "select [컬럼명] from table_nm2 where table_nm2.idx = :idx", nativeQuery = true)
    void insertIntoSelect(@Param("idx") Long idx);

}
