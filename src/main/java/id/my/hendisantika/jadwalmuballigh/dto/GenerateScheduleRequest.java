package id.my.hendisantika.jadwalmuballigh.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.YearMonth;

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
public record GenerateScheduleRequest(
        @NotNull(message = "Bulan wajib diisi") YearMonth bulan,
        @Min(value = 10, message = "Minimal 10 partikel")
        @Max(value = 200, message = "Maksimal 200 partikel")
        Integer numParticles,
        @Min(value = 50, message = "Minimal 50 iterasi")
        @Max(value = 500, message = "Maksimal 500 iterasi")
        Integer maxIterations
) {
    public GenerateScheduleRequest {
        if (numParticles == null) numParticles = 50;
        if (maxIterations == null) maxIterations = 100;
    }
}
