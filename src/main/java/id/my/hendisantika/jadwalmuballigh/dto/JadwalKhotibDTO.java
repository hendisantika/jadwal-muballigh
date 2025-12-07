package id.my.hendisantika.jadwalmuballigh.dto;

import id.my.hendisantika.jadwalmuballigh.entity.JadwalKhotib;

import java.time.LocalDate;

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
public record JadwalKhotibDTO(
        Long id,
        LocalDate tanggalJumat,
        Long khotibId,
        String khotibNama,
        Long masjidId,
        String masjidNama,
        String temaKhutbah,
        String status,
        String catatan,
        Double fitnessScore
) {
    public static JadwalKhotibDTO fromEntity(JadwalKhotib jadwal) {
        return new JadwalKhotibDTO(
                jadwal.getId(),
                jadwal.getTanggalJumat(),
                jadwal.getKhotib() != null ? jadwal.getKhotib().getId() : null,
                jadwal.getKhotib() != null ? jadwal.getKhotib().getNama() : null,
                jadwal.getMasjid() != null ? jadwal.getMasjid().getId() : null,
                jadwal.getMasjid() != null ? jadwal.getMasjid().getNama() : null,
                jadwal.getTemaKhutbah(),
                jadwal.getStatus() != null ? jadwal.getStatus().name() : null,
                jadwal.getCatatan(),
                jadwal.getFitnessScore()
        );
    }
}
