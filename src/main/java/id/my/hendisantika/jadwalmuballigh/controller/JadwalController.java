package id.my.hendisantika.jadwalmuballigh.controller;

import id.my.hendisantika.jadwalmuballigh.dto.GenerateScheduleRequest;
import id.my.hendisantika.jadwalmuballigh.dto.JadwalKhotibDTO;
import id.my.hendisantika.jadwalmuballigh.dto.ScheduleResultDTO;
import id.my.hendisantika.jadwalmuballigh.entity.JadwalKhotib;
import id.my.hendisantika.jadwalmuballigh.service.JadwalService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;
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
@RestController
@RequestMapping("/api/jadwal")
@RequiredArgsConstructor
public class JadwalController {

    private final JadwalService jadwalService;

    @GetMapping
    public ResponseEntity<List<JadwalKhotibDTO>> findAll() {
        return ResponseEntity.ok(jadwalService.findAll());
    }

    @GetMapping("/bulan/{bulan}")
    public ResponseEntity<List<JadwalKhotibDTO>> findByMonth(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM") YearMonth bulan) {
        return ResponseEntity.ok(jadwalService.findByMonth(bulan));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JadwalKhotibDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(jadwalService.findById(id));
    }

    @PostMapping("/generate")
    public ResponseEntity<ScheduleResultDTO> generateSchedule(@Valid @RequestBody GenerateScheduleRequest request) {
        return ResponseEntity.ok(jadwalService.generateSchedule(request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<JadwalKhotibDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam JadwalKhotib.StatusJadwal status) {
        return ResponseEntity.ok(jadwalService.updateStatus(id, status));
    }

    @PatchMapping("/{id}/khotib/{khotibId}")
    public ResponseEntity<JadwalKhotibDTO> updateKhotib(
            @PathVariable Long id,
            @PathVariable Long khotibId) {
        return ResponseEntity.ok(jadwalService.updateKhotib(id, khotibId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        jadwalService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/khotib/{khotibId}")
    public ResponseEntity<List<JadwalKhotibDTO>> findByKhotibId(@PathVariable Long khotibId) {
        return ResponseEntity.ok(jadwalService.findByKhotibId(khotibId));
    }

    @GetMapping("/masjid/{masjidId}")
    public ResponseEntity<List<JadwalKhotibDTO>> findByMasjidId(@PathVariable Long masjidId) {
        return ResponseEntity.ok(jadwalService.findByMasjidId(masjidId));
    }
}
