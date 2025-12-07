package id.my.hendisantika.jadwalmuballigh.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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
@Table(name = "masjids")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Masjid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nama masjid wajib diisi")
    @Column(nullable = false)
    private String nama;

    private String alamat;

    @Column(name = "kapasitas_jamaah")
    private Integer kapasitasJamaah;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "area_id")
    private Area area;

    @OneToMany(mappedBy = "masjid", cascade = CascadeType.ALL)
    @Builder.Default
    private List<JadwalKhotib> jadwalList = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "masjid_khotib_preference",
            joinColumns = @JoinColumn(name = "masjid_id"),
            inverseJoinColumns = @JoinColumn(name = "khotib_id")
    )
    @Builder.Default
    private List<Khotib> preferredKhotibs = new ArrayList<>();
}
