package id.my.hendisantika.jadwalmuballigh.service;

import id.my.hendisantika.jadwalmuballigh.dto.MasjidDTO;
import id.my.hendisantika.jadwalmuballigh.entity.Area;
import id.my.hendisantika.jadwalmuballigh.entity.Masjid;
import id.my.hendisantika.jadwalmuballigh.repository.AreaRepository;
import id.my.hendisantika.jadwalmuballigh.repository.MasjidRepository;
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
public class MasjidService {

    private final MasjidRepository masjidRepository;
    private final AreaRepository areaRepository;

    public List<MasjidDTO> findAll() {
        return masjidRepository.findAll().stream()
                .map(MasjidDTO::fromEntity)
                .toList();
    }

    public MasjidDTO findById(Long id) {
        return masjidRepository.findById(id)
                .map(MasjidDTO::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Masjid tidak ditemukan dengan ID: " + id));
    }

    @Transactional
    public MasjidDTO create(MasjidDTO dto) {
        Masjid masjid = dto.toEntity();

        if (dto.areaId() != null) {
            Area area = areaRepository.findById(dto.areaId())
                    .orElseThrow(() -> new IllegalArgumentException("Area tidak ditemukan dengan ID: " + dto.areaId()));
            masjid.setArea(area);
        }

        Masjid saved = masjidRepository.save(masjid);
        return MasjidDTO.fromEntity(saved);
    }

    @Transactional
    public MasjidDTO update(Long id, MasjidDTO dto) {
        Masjid existing = masjidRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Masjid tidak ditemukan dengan ID: " + id));

        existing.setNama(dto.nama());
        existing.setAlamat(dto.alamat());
        existing.setKapasitasJamaah(dto.kapasitasJamaah());
        existing.setLatitude(dto.latitude());
        existing.setLongitude(dto.longitude());

        if (dto.areaId() != null) {
            Area area = areaRepository.findById(dto.areaId())
                    .orElseThrow(() -> new IllegalArgumentException("Area tidak ditemukan dengan ID: " + dto.areaId()));
            existing.setArea(area);
        }

        Masjid saved = masjidRepository.save(existing);
        return MasjidDTO.fromEntity(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!masjidRepository.existsById(id)) {
            throw new IllegalArgumentException("Masjid tidak ditemukan dengan ID: " + id);
        }
        masjidRepository.deleteById(id);
    }

    public List<MasjidDTO> findByAreaId(Long areaId) {
        return masjidRepository.findByAreaId(areaId).stream()
                .map(MasjidDTO::fromEntity)
                .toList();
    }

    public List<Masjid> findAllEntities() {
        return masjidRepository.findAllWithDetails();
    }
}
