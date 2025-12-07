package id.my.hendisantika.jadwalmuballigh.service;

import id.my.hendisantika.jadwalmuballigh.dto.AreaDTO;
import id.my.hendisantika.jadwalmuballigh.entity.Area;
import id.my.hendisantika.jadwalmuballigh.repository.AreaRepository;
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
public class AreaService {

    private final AreaRepository areaRepository;

    public List<AreaDTO> findAll() {
        return areaRepository.findAll().stream()
                .map(AreaDTO::fromEntity)
                .toList();
    }

    public AreaDTO findById(Long id) {
        return areaRepository.findById(id)
                .map(AreaDTO::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Area tidak ditemukan dengan ID: " + id));
    }

    @Transactional
    public AreaDTO create(AreaDTO dto) {
        Area area = dto.toEntity();
        Area saved = areaRepository.save(area);
        return AreaDTO.fromEntity(saved);
    }

    @Transactional
    public AreaDTO update(Long id, AreaDTO dto) {
        Area existing = areaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Area tidak ditemukan dengan ID: " + id));

        existing.setNama(dto.nama());
        existing.setKecamatan(dto.kecamatan());
        existing.setKota(dto.kota());

        Area saved = areaRepository.save(existing);
        return AreaDTO.fromEntity(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!areaRepository.existsById(id)) {
            throw new IllegalArgumentException("Area tidak ditemukan dengan ID: " + id);
        }
        areaRepository.deleteById(id);
    }

    public List<AreaDTO> findByKota(String kota) {
        return areaRepository.findByKota(kota).stream()
                .map(AreaDTO::fromEntity)
                .toList();
    }
}
