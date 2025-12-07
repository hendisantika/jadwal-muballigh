package id.my.hendisantika.jadwalmuballigh.repository;

import id.my.hendisantika.jadwalmuballigh.entity.Khotib;
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
public interface KhotibRepository extends JpaRepository<Khotib, Long> {

    List<Khotib> findByAktifTrue();

    @Query("SELECT k FROM Khotib k WHERE k.aktif = true AND k.pengalamanTahun >= :minPengalaman")
    List<Khotib> findByMinPengalaman(@Param("minPengalaman") Integer minPengalaman);

    @Query("SELECT k FROM Khotib k WHERE k.aktif = true AND k.rating >= :minRating")
    List<Khotib> findByMinRating(@Param("minRating") Double minRating);

    @Query("SELECT k FROM Khotib k LEFT JOIN FETCH k.preferredAreas WHERE k.aktif = true")
    List<Khotib> findAllActiveWithPreferredAreas();

    @Query("SELECT k FROM Khotib k JOIN k.preferredAreas a WHERE a.id = :areaId AND k.aktif = true")
    List<Khotib> findByPreferredAreaId(@Param("areaId") Long areaId);
}
