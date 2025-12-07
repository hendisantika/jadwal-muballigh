package id.my.hendisantika.jadwalmuballigh.dto;

import id.my.hendisantika.jadwalmuballigh.entity.Area;
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
public record AreaDTO(
        Long id,
        @NotBlank(message = "Nama area wajib diisi") String nama,
        String kecamatan,
        String kota
) {
    public static AreaDTO fromEntity(Area area) {
        return new AreaDTO(
                area.getId(),
                area.getNama(),
                area.getKecamatan(),
                area.getKota()
        );
    }

    public Area toEntity() {
        return Area.builder()
                .id(id)
                .nama(nama)
                .kecamatan(kecamatan)
                .kota(kota)
                .build();
    }
}
