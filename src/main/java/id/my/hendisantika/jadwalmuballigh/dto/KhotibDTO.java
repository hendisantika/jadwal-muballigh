package id.my.hendisantika.jadwalmuballigh.dto;

import id.my.hendisantika.jadwalmuballigh.entity.Khotib;
import jakarta.validation.constraints.NotBlank;

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
public record KhotibDTO(
        Long id,
        @NotBlank(message = "Nama khotib wajib diisi") String nama,
        String noTelepon,
        String alamat,
        Double latitude,
        Double longitude,
        String spesialisasiTema,
        Integer pengalamanTahun,
        Double rating,
        Boolean aktif,
        Integer maxJadwalPerBulan,
        List<Long> preferredAreaIds
) {
    public static KhotibDTO fromEntity(Khotib khotib) {
        List<Long> areaIds = khotib.getPreferredAreas() != null
                ? khotib.getPreferredAreas().stream().map(a -> a.getId()).toList()
                : List.of();

        return new KhotibDTO(
                khotib.getId(),
                khotib.getNama(),
                khotib.getNoTelepon(),
                khotib.getAlamat(),
                khotib.getLatitude(),
                khotib.getLongitude(),
                khotib.getSpesialisasiTema(),
                khotib.getPengalamanTahun(),
                khotib.getRating(),
                khotib.getAktif(),
                khotib.getMaxJadwalPerBulan(),
                areaIds
        );
    }

    public Khotib toEntity() {
        return Khotib.builder()
                .id(id)
                .nama(nama)
                .noTelepon(noTelepon)
                .alamat(alamat)
                .latitude(latitude)
                .longitude(longitude)
                .spesialisasiTema(spesialisasiTema)
                .pengalamanTahun(pengalamanTahun)
                .rating(rating)
                .aktif(aktif != null ? aktif : true)
                .maxJadwalPerBulan(maxJadwalPerBulan != null ? maxJadwalPerBulan : 4)
                .build();
    }
}
