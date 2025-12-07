package id.my.hendisantika.jadwalmuballigh.repository;

import id.my.hendisantika.jadwalmuballigh.entity.Masjid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : jadwal-muballigh
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 07/11/25
 * Time: 21.53
 * To change this template use File | Settings | File Templates.
 */
@Repository
public interface MasjidRepository extends JpaRepository<Masjid, Long> {

    List<Masjid> findByAreaId(Long areaId);

    @Query("SELECT m FROM Masjid m WHERE m.area.kota = :kota")
    List<Masjid> findByKota(@Param("kota") String kota);

    @Query("SELECT m FROM Masjid m LEFT JOIN FETCH m.area LEFT JOIN FETCH m.preferredKhotibs")
    List<Masjid> findAllWithDetails();
}
