package id.my.hendisantika.jadwalmuballigh.controller;

import id.my.hendisantika.jadwalmuballigh.dto.MasjidDTO;
import id.my.hendisantika.jadwalmuballigh.service.MasjidService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/api/masjids")
@RequiredArgsConstructor
public class MasjidController {

    private final MasjidService masjidService;

    @GetMapping
    public ResponseEntity<List<MasjidDTO>> findAll() {
        return ResponseEntity.ok(masjidService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MasjidDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(masjidService.findById(id));
    }

    @PostMapping
    public ResponseEntity<MasjidDTO> create(@Valid @RequestBody MasjidDTO dto) {
        MasjidDTO created = masjidService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MasjidDTO> update(@PathVariable Long id, @Valid @RequestBody MasjidDTO dto) {
        return ResponseEntity.ok(masjidService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        masjidService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/area/{areaId}")
    public ResponseEntity<List<MasjidDTO>> findByAreaId(@PathVariable Long areaId) {
        return ResponseEntity.ok(masjidService.findByAreaId(areaId));
    }
}
