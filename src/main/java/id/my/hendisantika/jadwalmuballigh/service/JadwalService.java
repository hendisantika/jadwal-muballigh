package id.my.hendisantika.jadwalmuballigh.service;

import id.my.hendisantika.jadwalmuballigh.dto.GenerateScheduleRequest;
import id.my.hendisantika.jadwalmuballigh.dto.JadwalKhotibDTO;
import id.my.hendisantika.jadwalmuballigh.dto.ScheduleResultDTO;
import id.my.hendisantika.jadwalmuballigh.entity.JadwalKhotib;
import id.my.hendisantika.jadwalmuballigh.entity.Khotib;
import id.my.hendisantika.jadwalmuballigh.entity.Masjid;
import id.my.hendisantika.jadwalmuballigh.pso.PSOConfig;
import id.my.hendisantika.jadwalmuballigh.pso.ScheduleOptimizer;
import id.my.hendisantika.jadwalmuballigh.repository.JadwalKhotibRepository;
import id.my.hendisantika.jadwalmuballigh.repository.KhotibRepository;
import id.my.hendisantika.jadwalmuballigh.repository.MasjidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
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
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JadwalService {

    private final JadwalKhotibRepository jadwalRepository;
    private final KhotibRepository khotibRepository;
    private final MasjidRepository masjidRepository;

    public List<JadwalKhotibDTO> findAll() {
        return jadwalRepository.findAll().stream()
                .map(JadwalKhotibDTO::fromEntity)
                .toList();
    }

    public List<JadwalKhotibDTO> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return jadwalRepository.findByDateRangeWithDetails(startDate, endDate).stream()
                .map(JadwalKhotibDTO::fromEntity)
                .toList();
    }

    public List<JadwalKhotibDTO> findByMonth(YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return findByDateRange(startDate, endDate);
    }

    public JadwalKhotibDTO findById(Long id) {
        return jadwalRepository.findById(id)
                .map(JadwalKhotibDTO::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Jadwal tidak ditemukan dengan ID: " + id));
    }

    @Transactional
    public ScheduleResultDTO generateSchedule(GenerateScheduleRequest request) {
        YearMonth bulan = request.bulan();
        log.info("Generating schedule for month: {}", bulan);

        List<Khotib> activeKhotibs = khotibRepository.findAllActiveWithPreferredAreas();
        List<Masjid> allMasjids = masjidRepository.findAllWithDetails();

        if (activeKhotibs.isEmpty()) {
            return new ScheduleResultDTO(List.of(), 0, 0, "Tidak ada khotib aktif yang tersedia");
        }

        if (allMasjids.isEmpty()) {
            return new ScheduleResultDTO(List.of(), 0, 0, "Tidak ada masjid yang terdaftar");
        }

        List<LocalDate> jumatDates = getJumatDatesInMonth(bulan);

        if (jumatDates.isEmpty()) {
            return new ScheduleResultDTO(List.of(), 0, 0, "Tidak ada hari Jumat dalam bulan " + bulan);
        }

        log.info("Found {} Jumat dates, {} active khotibs, {} masjids",
                jumatDates.size(), activeKhotibs.size(), allMasjids.size());

        PSOConfig config = PSOConfig.builder()
                .numParticles(request.numParticles())
                .maxIterations(request.maxIterations())
                .build();

        ScheduleOptimizer optimizer = new ScheduleOptimizer(activeKhotibs, allMasjids, jumatDates, config);
        ScheduleOptimizer.ScheduleResult result = optimizer.optimize();

        List<JadwalKhotib> jadwalList = new ArrayList<>();

        result.schedule().forEach((slot, assignment) -> {
            Khotib khotib = khotibRepository.findById(assignment.khotibId()).orElseThrow();
            Masjid masjid = masjidRepository.findById(assignment.masjidId()).orElseThrow();

            JadwalKhotib jadwal = JadwalKhotib.builder()
                    .tanggalJumat(slot.date())
                    .khotib(khotib)
                    .masjid(masjid)
                    .fitnessScore(assignment.fitnessScore())
                    .status(JadwalKhotib.StatusJadwal.TERJADWAL)
                    .build();

            jadwalList.add(jadwal);
        });

        LocalDate startDate = bulan.atDay(1);
        LocalDate endDate = bulan.atEndOfMonth();
        jadwalRepository.deleteByTanggalJumatBetween(startDate, endDate);

        List<JadwalKhotib> savedJadwal = jadwalRepository.saveAll(jadwalList);

        List<JadwalKhotibDTO> dtoList = savedJadwal.stream()
                .map(JadwalKhotibDTO::fromEntity)
                .toList();

        String message = String.format("Berhasil membuat %d jadwal untuk bulan %s dengan fitness score %.2f",
                dtoList.size(), bulan, result.totalFitness());

        return new ScheduleResultDTO(dtoList, result.totalFitness(), result.iterations(), message);
    }

    private List<LocalDate> getJumatDatesInMonth(YearMonth yearMonth) {
        List<LocalDate> jumatDates = new ArrayList<>();
        LocalDate firstDayOfMonth = yearMonth.atDay(1);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();

        LocalDate firstJumat = firstDayOfMonth.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY));

        LocalDate currentJumat = firstJumat;
        while (!currentJumat.isAfter(lastDayOfMonth)) {
            jumatDates.add(currentJumat);
            currentJumat = currentJumat.plusWeeks(1);
        }

        return jumatDates;
    }

    @Transactional
    public JadwalKhotibDTO updateStatus(Long id, JadwalKhotib.StatusJadwal status) {
        JadwalKhotib jadwal = jadwalRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Jadwal tidak ditemukan dengan ID: " + id));

        jadwal.setStatus(status);
        JadwalKhotib saved = jadwalRepository.save(jadwal);
        return JadwalKhotibDTO.fromEntity(saved);
    }

    @Transactional
    public JadwalKhotibDTO updateKhotib(Long jadwalId, Long newKhotibId) {
        JadwalKhotib jadwal = jadwalRepository.findById(jadwalId)
                .orElseThrow(() -> new IllegalArgumentException("Jadwal tidak ditemukan dengan ID: " + jadwalId));

        Khotib newKhotib = khotibRepository.findById(newKhotibId)
                .orElseThrow(() -> new IllegalArgumentException("Khotib tidak ditemukan dengan ID: " + newKhotibId));

        List<JadwalKhotib> conflicts = jadwalRepository.findConflicts(newKhotibId, jadwal.getTanggalJumat());
        if (!conflicts.isEmpty()) {
            throw new IllegalStateException("Khotib sudah memiliki jadwal pada tanggal tersebut");
        }

        jadwal.setKhotib(newKhotib);
        jadwal.setStatus(JadwalKhotib.StatusJadwal.DIGANTI);
        JadwalKhotib saved = jadwalRepository.save(jadwal);
        return JadwalKhotibDTO.fromEntity(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!jadwalRepository.existsById(id)) {
            throw new IllegalArgumentException("Jadwal tidak ditemukan dengan ID: " + id);
        }
        jadwalRepository.deleteById(id);
    }

    public List<JadwalKhotibDTO> findByKhotibId(Long khotibId) {
        return jadwalRepository.findByKhotibId(khotibId).stream()
                .map(JadwalKhotibDTO::fromEntity)
                .toList();
    }

    public List<JadwalKhotibDTO> findByMasjidId(Long masjidId) {
        return jadwalRepository.findByMasjidId(masjidId).stream()
                .map(JadwalKhotibDTO::fromEntity)
                .toList();
    }
}
