package id.my.hendisantika.jadwalmuballigh.config;

import id.my.hendisantika.jadwalmuballigh.entity.Area;
import id.my.hendisantika.jadwalmuballigh.entity.Khotib;
import id.my.hendisantika.jadwalmuballigh.entity.Masjid;
import id.my.hendisantika.jadwalmuballigh.repository.AreaRepository;
import id.my.hendisantika.jadwalmuballigh.repository.KhotibRepository;
import id.my.hendisantika.jadwalmuballigh.repository.MasjidRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

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
@Component
@Profile("dev")
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final AreaRepository areaRepository;
    private final MasjidRepository masjidRepository;
    private final KhotibRepository khotibRepository;

    @Override
    public void run(String... args) {
        if (areaRepository.count() > 0) {
            log.info("Data sudah ada, skip seeding");
            return;
        }

        log.info("Seeding initial data...");

        // Create Areas
        Area areaPusat = areaRepository.save(Area.builder()
                .nama("Area Pusat Kota")
                .kecamatan("Kecamatan Pusat")
                .kota("Bandung")
                .build());

        Area areaUtara = areaRepository.save(Area.builder()
                .nama("Area Utara")
                .kecamatan("Kecamatan Utara")
                .kota("Bandung")
                .build());

        Area areaSelatan = areaRepository.save(Area.builder()
                .nama("Area Selatan")
                .kecamatan("Kecamatan Selatan")
                .kota("Bandung")
                .build());

        Area areaTimur = areaRepository.save(Area.builder()
                .nama("Area Timur")
                .kecamatan("Kecamatan Timur")
                .kota("Bandung")
                .build());

        Area areaBarat = areaRepository.save(Area.builder()
                .nama("Area Barat")
                .kecamatan("Kecamatan Barat")
                .kota("Bandung")
                .build());

        log.info("Created {} areas", 5);

        // Create Masjids
        masjidRepository.save(Masjid.builder()
                .nama("Masjid Agung Al-Ukhuwah")
                .alamat("Jl. Asia Afrika No. 1")
                .area(areaPusat)
                .kapasitasJamaah(1000)
                .latitude(-6.9175)
                .longitude(107.6191)
                .build());

        masjidRepository.save(Masjid.builder()
                .nama("Masjid Raya Bandung")
                .alamat("Jl. Dalem Kaum No. 14")
                .area(areaPusat)
                .kapasitasJamaah(800)
                .latitude(-6.9211)
                .longitude(107.6069)
                .build());

        masjidRepository.save(Masjid.builder()
                .nama("Masjid Al-Ikhlas")
                .alamat("Jl. Setiabudi No. 50")
                .area(areaUtara)
                .kapasitasJamaah(500)
                .latitude(-6.8731)
                .longitude(107.6088)
                .build());

        masjidRepository.save(Masjid.builder()
                .nama("Masjid Istiqomah")
                .alamat("Jl. Cihampelas No. 100")
                .area(areaUtara)
                .kapasitasJamaah(400)
                .latitude(-6.8953)
                .longitude(107.6025)
                .build());

        masjidRepository.save(Masjid.builder()
                .nama("Masjid Salman ITB")
                .alamat("Jl. Ganesa No. 7")
                .area(areaUtara)
                .kapasitasJamaah(1500)
                .latitude(-6.8915)
                .longitude(107.6107)
                .build());

        masjidRepository.save(Masjid.builder()
                .nama("Masjid Al-Falah")
                .alamat("Jl. Buah Batu No. 200")
                .area(areaSelatan)
                .kapasitasJamaah(350)
                .latitude(-6.9419)
                .longitude(107.6365)
                .build());

        masjidRepository.save(Masjid.builder()
                .nama("Masjid An-Nur")
                .alamat("Jl. Soekarno Hatta No. 500")
                .area(areaTimur)
                .kapasitasJamaah(600)
                .latitude(-6.9256)
                .longitude(107.6689)
                .build());

        masjidRepository.save(Masjid.builder()
                .nama("Masjid Al-Muhajirin")
                .alamat("Jl. Pasteur No. 100")
                .area(areaBarat)
                .kapasitasJamaah(450)
                .latitude(-6.8960)
                .longitude(107.5883)
                .build());

        log.info("Created {} masjids", 8);

        // Create Khotibs
        khotibRepository.save(Khotib.builder()
                .nama("Ustadz Ahmad Fauzi")
                .noTelepon("081234567890")
                .alamat("Jl. Merdeka No. 10")
                .latitude(-6.9100)
                .longitude(107.6100)
                .spesialisasiTema("Akidah dan Tauhid")
                .pengalamanTahun(15)
                .rating(4.8)
                .aktif(true)
                .maxJadwalPerBulan(4)
                .preferredAreas(List.of(areaPusat, areaUtara))
                .build());

        khotibRepository.save(Khotib.builder()
                .nama("Ustadz Muhammad Rizki")
                .noTelepon("081234567891")
                .alamat("Jl. Braga No. 20")
                .latitude(-6.9200)
                .longitude(107.6050)
                .spesialisasiTema("Fiqih dan Muamalah")
                .pengalamanTahun(10)
                .rating(4.5)
                .aktif(true)
                .maxJadwalPerBulan(4)
                .preferredAreas(List.of(areaPusat, areaSelatan))
                .build());

        khotibRepository.save(Khotib.builder()
                .nama("Ustadz Abdullah Hakim")
                .noTelepon("081234567892")
                .alamat("Jl. Dago No. 30")
                .latitude(-6.8850)
                .longitude(107.6150)
                .spesialisasiTema("Sirah Nabawiyah")
                .pengalamanTahun(20)
                .rating(4.9)
                .aktif(true)
                .maxJadwalPerBulan(3)
                .preferredAreas(List.of(areaUtara))
                .build());

        khotibRepository.save(Khotib.builder()
                .nama("Ustadz Hasan Basri")
                .noTelepon("081234567893")
                .alamat("Jl. Dipatiukur No. 40")
                .latitude(-6.8920)
                .longitude(107.6180)
                .spesialisasiTema("Akhlak dan Tasawuf")
                .pengalamanTahun(12)
                .rating(4.6)
                .aktif(true)
                .maxJadwalPerBulan(4)
                .preferredAreas(List.of(areaUtara, areaTimur))
                .build());

        khotibRepository.save(Khotib.builder()
                .nama("Ustadz Umar Farooq")
                .noTelepon("081234567894")
                .alamat("Jl. Sukajadi No. 50")
                .latitude(-6.8800)
                .longitude(107.5900)
                .spesialisasiTema("Tafsir Al-Quran")
                .pengalamanTahun(18)
                .rating(4.7)
                .aktif(true)
                .maxJadwalPerBulan(4)
                .preferredAreas(List.of(areaBarat, areaPusat))
                .build());

        khotibRepository.save(Khotib.builder()
                .nama("Ustadz Yusuf Mansur")
                .noTelepon("081234567895")
                .alamat("Jl. Buah Batu No. 60")
                .latitude(-6.9350)
                .longitude(107.6300)
                .spesialisasiTema("Motivasi Islami")
                .pengalamanTahun(8)
                .rating(4.4)
                .aktif(true)
                .maxJadwalPerBulan(5)
                .preferredAreas(List.of(areaSelatan, areaTimur))
                .build());

        khotibRepository.save(Khotib.builder()
                .nama("Ustadz Ibrahim Ali")
                .noTelepon("081234567896")
                .alamat("Jl. Soekarno Hatta No. 70")
                .latitude(-6.9300)
                .longitude(107.6600)
                .spesialisasiTema("Hadits dan Ulumul Hadits")
                .pengalamanTahun(14)
                .rating(4.6)
                .aktif(true)
                .maxJadwalPerBulan(4)
                .preferredAreas(List.of(areaTimur))
                .build());

        khotibRepository.save(Khotib.builder()
                .nama("Ustadz Zainal Abidin")
                .noTelepon("081234567897")
                .alamat("Jl. Pasteur No. 80")
                .latitude(-6.8900)
                .longitude(107.5850)
                .spesialisasiTema("Ekonomi Syariah")
                .pengalamanTahun(7)
                .rating(4.3)
                .aktif(true)
                .maxJadwalPerBulan(4)
                .preferredAreas(List.of(areaBarat))
                .build());

        khotibRepository.save(Khotib.builder()
                .nama("Ustadz Ridwan Kamil")
                .noTelepon("081234567898")
                .alamat("Jl. Ir. H. Juanda No. 90")
                .latitude(-6.9050)
                .longitude(107.6130)
                .spesialisasiTema("Islam dan Kepemimpinan")
                .pengalamanTahun(11)
                .rating(4.5)
                .aktif(true)
                .maxJadwalPerBulan(3)
                .preferredAreas(List.of(areaPusat, areaUtara, areaSelatan))
                .build());

        khotibRepository.save(Khotib.builder()
                .nama("Ustadz Salim Bahanan")
                .noTelepon("081234567899")
                .alamat("Jl. Ciumbuleuit No. 100")
                .latitude(-6.8700)
                .longitude(107.6050)
                .spesialisasiTema("Qiraat dan Tahsin")
                .pengalamanTahun(16)
                .rating(4.8)
                .aktif(true)
                .maxJadwalPerBulan(4)
                .preferredAreas(List.of(areaUtara, areaBarat))
                .build());

        log.info("Created {} khotibs", 10);
        log.info("Data seeding completed!");
    }
}
