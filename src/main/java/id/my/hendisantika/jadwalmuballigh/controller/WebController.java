package id.my.hendisantika.jadwalmuballigh.controller;

import id.my.hendisantika.jadwalmuballigh.service.AreaService;
import id.my.hendisantika.jadwalmuballigh.service.JadwalService;
import id.my.hendisantika.jadwalmuballigh.service.KhotibService;
import id.my.hendisantika.jadwalmuballigh.service.MasjidService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.YearMonth;

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
@Controller
@RequiredArgsConstructor
public class WebController {

    private final AreaService areaService;
    private final MasjidService masjidService;
    private final KhotibService khotibService;
    private final JadwalService jadwalService;

    @GetMapping("/")
    public String index(Model model) {
        YearMonth currentMonth = YearMonth.now();
        model.addAttribute("jadwalList", jadwalService.findByMonth(currentMonth));
        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("areas", areaService.findAll());
        model.addAttribute("masjids", masjidService.findAll());
        model.addAttribute("khotibs", khotibService.findAllActive());
        return "index";
    }

    @GetMapping("/jadwal")
    public String jadwal(Model model) {
        YearMonth currentMonth = YearMonth.now();
        model.addAttribute("jadwalList", jadwalService.findByMonth(currentMonth));
        model.addAttribute("currentMonth", currentMonth);
        model.addAttribute("khotibs", khotibService.findAllActive());
        return "jadwal";
    }

    @GetMapping("/jadwal/{bulan}")
    public String jadwalByMonth(@PathVariable String bulan, Model model) {
        YearMonth yearMonth = YearMonth.parse(bulan);
        model.addAttribute("jadwalList", jadwalService.findByMonth(yearMonth));
        model.addAttribute("currentMonth", yearMonth);
        model.addAttribute("khotibs", khotibService.findAllActive());
        return "jadwal";
    }

    @GetMapping("/khotib")
    public String khotib(Model model) {
        model.addAttribute("khotibs", khotibService.findAll());
        model.addAttribute("areas", areaService.findAll());
        return "khotib";
    }

    @GetMapping("/masjid")
    public String masjid(Model model) {
        model.addAttribute("masjids", masjidService.findAll());
        model.addAttribute("areas", areaService.findAll());
        return "masjid";
    }

    @GetMapping("/area")
    public String area(Model model) {
        model.addAttribute("areas", areaService.findAll());
        return "area";
    }
}
