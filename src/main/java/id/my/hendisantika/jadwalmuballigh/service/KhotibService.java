package id.my.hendisantika.jadwalmuballigh.service;

import id.my.hendisantika.jadwalmuballigh.dto.KhotibDTO;
import id.my.hendisantika.jadwalmuballigh.entity.Area;
import id.my.hendisantika.jadwalmuballigh.entity.Khotib;
import id.my.hendisantika.jadwalmuballigh.repository.AreaRepository;
import id.my.hendisantika.jadwalmuballigh.repository.KhotibRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KhotibService {

    private final KhotibRepository khotibRepository;
    private final AreaRepository areaRepository;

    public List<KhotibDTO> findAll() {
        return khotibRepository.findAll().stream()
                .map(KhotibDTO::fromEntity)
                .toList();
    }

    public List<KhotibDTO> findAllActive() {
        return khotibRepository.findByAktifTrue().stream()
                .map(KhotibDTO::fromEntity)
                .toList();
    }

    public KhotibDTO findById(Long id) {
        return khotibRepository.findById(id)
                .map(KhotibDTO::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Khotib tidak ditemukan dengan ID: " + id));
    }

    @Transactional
    public KhotibDTO create(KhotibDTO dto) {
        Khotib khotib = dto.toEntity();

        if (dto.preferredAreaIds() != null && !dto.preferredAreaIds().isEmpty()) {
            List<Area> areas = areaRepository.findAllById(dto.preferredAreaIds());
            khotib.setPreferredAreas(areas);
        }

        Khotib saved = khotibRepository.save(khotib);
        return KhotibDTO.fromEntity(saved);
    }

    @Transactional
    public KhotibDTO update(Long id, KhotibDTO dto) {
        Khotib existing = khotibRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Khotib tidak ditemukan dengan ID: " + id));

        existing.setNama(dto.nama());
        existing.setNoTelepon(dto.noTelepon());
        existing.setAlamat(dto.alamat());
        existing.setLatitude(dto.latitude());
        existing.setLongitude(dto.longitude());
        existing.setSpesialisasiTema(dto.spesialisasiTema());
        existing.setPengalamanTahun(dto.pengalamanTahun());
        existing.setRating(dto.rating());
        existing.setAktif(dto.aktif() != null ? dto.aktif() : true);
        existing.setMaxJadwalPerBulan(dto.maxJadwalPerBulan() != null ? dto.maxJadwalPerBulan() : 4);

        if (dto.preferredAreaIds() != null) {
            List<Area> areas = areaRepository.findAllById(dto.preferredAreaIds());
            existing.setPreferredAreas(areas);
        }

        Khotib saved = khotibRepository.save(existing);
        return KhotibDTO.fromEntity(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!khotibRepository.existsById(id)) {
            throw new IllegalArgumentException("Khotib tidak ditemukan dengan ID: " + id);
        }
        khotibRepository.deleteById(id);
    }

    public List<KhotibDTO> findByPreferredAreaId(Long areaId) {
        return khotibRepository.findByPreferredAreaId(areaId).stream()
                .map(KhotibDTO::fromEntity)
                .toList();
    }

    public List<Khotib> findAllActiveEntities() {
        return khotibRepository.findAllActiveWithPreferredAreas();
    }

    @Transactional
    public KhotibDTO toggleActive(Long id) {
        Khotib khotib = khotibRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Khotib tidak ditemukan dengan ID: " + id));

        khotib.setAktif(!khotib.getAktif());
        Khotib saved = khotibRepository.save(khotib);
        return KhotibDTO.fromEntity(saved);
    }
}
