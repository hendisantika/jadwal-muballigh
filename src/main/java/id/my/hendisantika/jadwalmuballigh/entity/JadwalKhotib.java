package id.my.hendisantika.jadwalmuballigh.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@Entity
@Table(name = "jadwal_khotibs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JadwalKhotib {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Tanggal wajib diisi")
    @Column(name = "tanggal_jumat", nullable = false)
    private LocalDate tanggalJumat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "khotib_id", nullable = false)
    private Khotib khotib;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "masjid_id", nullable = false)
    private Masjid masjid;

    @Column(name = "tema_khutbah")
    private String temaKhutbah;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Builder.Default
    private StatusJadwal status = StatusJadwal.TERJADWAL;

    @Column(name = "catatan")
    private String catatan;

    @Column(name = "fitness_score")
    private Double fitnessScore;

    public enum StatusJadwal {
        TERJADWAL,
        SELESAI,
        DIBATALKAN,
        DIGANTI
    }
}
