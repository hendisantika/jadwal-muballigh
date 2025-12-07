package id.my.hendisantika.jadwalmuballigh.dto;

import id.my.hendisantika.jadwalmuballigh.entity.Masjid;
import jakarta.validation.constraints.NotBlank;

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
public record MasjidDTO(
        Long id,
        @NotBlank(message = "Nama masjid wajib diisi") String nama,
        String alamat,
        Integer kapasitasJamaah,
        Double latitude,
        Double longitude,
        Long areaId,
        String areaNama
) {
    public static MasjidDTO fromEntity(Masjid masjid) {
        return new MasjidDTO(
                masjid.getId(),
                masjid.getNama(),
                masjid.getAlamat(),
                masjid.getKapasitasJamaah(),
                masjid.getLatitude(),
                masjid.getLongitude(),
                masjid.getArea() != null ? masjid.getArea().getId() : null,
                masjid.getArea() != null ? masjid.getArea().getNama() : null
        );
    }

    public Masjid toEntity() {
        return Masjid.builder()
                .id(id)
                .nama(nama)
                .alamat(alamat)
                .kapasitasJamaah(kapasitasJamaah)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
