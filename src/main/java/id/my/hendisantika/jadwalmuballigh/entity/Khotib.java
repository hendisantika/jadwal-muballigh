package id.my.hendisantika.jadwalmuballigh.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
@Entity
@Table(name = "khotibs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Khotib {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nama khotib wajib diisi")
    @Column(nullable = false)
    private String nama;

    @Column(name = "no_telepon")
    private String noTelepon;

    private String alamat;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "spesialisasi_tema")
    private String spesialisasiTema;

    @Column(name = "pengalaman_tahun")
    private Integer pengalamanTahun;

    @Column(name = "rating")
    private Double rating;

    @Column(name = "aktif")
    @Builder.Default
    private Boolean aktif = true;

    @Column(name = "max_jadwal_per_bulan")
    @Builder.Default
    private Integer maxJadwalPerBulan = 4;

    @ManyToMany
    @JoinTable(
            name = "khotib_area_preference",
            joinColumns = @JoinColumn(name = "khotib_id"),
            inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    @Builder.Default
    private List<Area> preferredAreas = new ArrayList<>();

    @OneToMany(mappedBy = "khotib", cascade = CascadeType.ALL)
    @Builder.Default
    private List<JadwalKhotib> jadwalList = new ArrayList<>();
}
