package id.my.hendisantika.jadwalmuballigh.controller;

import id.my.hendisantika.jadwalmuballigh.dto.KhotibDTO;
import id.my.hendisantika.jadwalmuballigh.service.KhotibService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/api/khotibs")
@RequiredArgsConstructor
public class KhotibController {

    private final KhotibService khotibService;

    @GetMapping
    public ResponseEntity<List<KhotibDTO>> findAll() {
        return ResponseEntity.ok(khotibService.findAll());
    }

    @GetMapping("/active")
    public ResponseEntity<List<KhotibDTO>> findAllActive() {
        return ResponseEntity.ok(khotibService.findAllActive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<KhotibDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(khotibService.findById(id));
    }

    @PostMapping
    public ResponseEntity<KhotibDTO> create(@Valid @RequestBody KhotibDTO dto) {
        KhotibDTO created = khotibService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<KhotibDTO> update(@PathVariable Long id, @Valid @RequestBody KhotibDTO dto) {
        return ResponseEntity.ok(khotibService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        khotibService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/toggle-active")
    public ResponseEntity<KhotibDTO> toggleActive(@PathVariable Long id) {
        return ResponseEntity.ok(khotibService.toggleActive(id));
    }

    @GetMapping("/area/{areaId}")
    public ResponseEntity<List<KhotibDTO>> findByPreferredAreaId(@PathVariable Long areaId) {
        return ResponseEntity.ok(khotibService.findByPreferredAreaId(areaId));
    }
}
