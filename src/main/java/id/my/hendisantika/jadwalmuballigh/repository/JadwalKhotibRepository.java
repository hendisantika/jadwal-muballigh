package id.my.hendisantika.jadwalmuballigh.repository;

import id.my.hendisantika.jadwalmuballigh.entity.JadwalKhotib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
public interface JadwalKhotibRepository extends JpaRepository<JadwalKhotib, Long> {

    List<JadwalKhotib> findByTanggalJumatBetween(LocalDate startDate, LocalDate endDate);

    List<JadwalKhotib> findByKhotibId(Long khotibId);

    List<JadwalKhotib> findByMasjidId(Long masjidId);

    @Query("SELECT j FROM JadwalKhotib j WHERE j.khotib.id = :khotibId AND j.tanggalJumat BETWEEN :startDate AND :endDate")
    List<JadwalKhotib> findByKhotibIdAndDateRange(
            @Param("khotibId") Long khotibId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT j FROM JadwalKhotib j LEFT JOIN FETCH j.khotib LEFT JOIN FETCH j.masjid WHERE j.tanggalJumat BETWEEN :startDate AND :endDate ORDER BY j.tanggalJumat, j.masjid.nama")
    List<JadwalKhotib> findByDateRangeWithDetails(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("SELECT j FROM JadwalKhotib j WHERE j.khotib.id = :khotibId AND j.tanggalJumat = :tanggal")
    List<JadwalKhotib> findConflicts(
            @Param("khotibId") Long khotibId,
            @Param("tanggal") LocalDate tanggal
    );

    @Query("SELECT COUNT(j) FROM JadwalKhotib j WHERE j.khotib.id = :khotibId AND j.tanggalJumat BETWEEN :startDate AND :endDate")
    long countByKhotibIdAndDateRange(
            @Param("khotibId") Long khotibId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    void deleteByTanggalJumatBetween(LocalDate startDate, LocalDate endDate);
}
