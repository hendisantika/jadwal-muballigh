package id.my.hendisantika.jadwalmuballigh.controller;

import id.my.hendisantika.jadwalmuballigh.dto.AreaDTO;
import id.my.hendisantika.jadwalmuballigh.service.AreaService;
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
@RequestMapping("/api/areas")
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;

    @GetMapping
    public ResponseEntity<List<AreaDTO>> findAll() {
        return ResponseEntity.ok(areaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(areaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AreaDTO> create(@Valid @RequestBody AreaDTO dto) {
        AreaDTO created = areaService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaDTO> update(@PathVariable Long id, @Valid @RequestBody AreaDTO dto) {
        return ResponseEntity.ok(areaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        areaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/kota/{kota}")
    public ResponseEntity<List<AreaDTO>> findByKota(@PathVariable String kota) {
        return ResponseEntity.ok(areaService.findByKota(kota));
    }
}
