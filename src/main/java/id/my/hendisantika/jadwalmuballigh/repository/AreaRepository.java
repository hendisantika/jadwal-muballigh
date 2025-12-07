package id.my.hendisantika.jadwalmuballigh.repository;

import id.my.hendisantika.jadwalmuballigh.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {

    Optional<Area> findByNama(String nama);

    List<Area> findByKota(String kota);

    List<Area> findByKecamatan(String kecamatan);
}
