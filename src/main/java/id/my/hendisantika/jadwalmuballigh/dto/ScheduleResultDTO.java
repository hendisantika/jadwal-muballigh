package id.my.hendisantika.jadwalmuballigh.dto;

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
public record ScheduleResultDTO(
        List<JadwalKhotibDTO> jadwalList,
        double totalFitness,
        int iterations,
        String message
) {
}
