package id.my.hendisantika.jadwalmuballigh.pso;

import id.my.hendisantika.jadwalmuballigh.entity.Khotib;
import id.my.hendisantika.jadwalmuballigh.entity.Masjid;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

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
public class ScheduleOptimizer {

    private final List<Khotib> khotibs;
    private final List<Masjid> masjids;
    private final List<LocalDate> jumatDates;
    private final PSOConfig config;
    private final Random random;

    private Particle[] particles;
    private int[] globalBest;
    private double globalBestFitness;

    public ScheduleOptimizer(List<Khotib> khotibs, List<Masjid> masjids,
                             List<LocalDate> jumatDates, PSOConfig config) {
        this.khotibs = khotibs;
        this.masjids = masjids;
        this.jumatDates = jumatDates;
        this.config = config;
        this.random = new Random();
        this.globalBestFitness = Double.NEGATIVE_INFINITY;
    }

    public ScheduleResult optimize() {
        if (khotibs.isEmpty() || masjids.isEmpty() || jumatDates.isEmpty()) {
            log.warn("Tidak dapat mengoptimasi: data kosong");
            return new ScheduleResult(new HashMap<>(), 0, 0);
        }

        initializeParticles();

        double w = config.getInertiaWeightMax();
        double wDecay = (config.getInertiaWeightMax() - config.getInertiaWeightMin())
                / config.getMaxIterations();

        for (int iter = 0; iter < config.getMaxIterations(); iter++) {
            for (Particle particle : particles) {
                double fitness = calculateFitness(particle);
                particle.setCurrentFitness(fitness);
                particle.updatePersonalBest();

                if (fitness > globalBestFitness) {
                    globalBestFitness = fitness;
                    globalBest = Arrays.copyOf(particle.getPosition(), particle.getPosition().length);
                    log.debug("Iterasi {}: Fitness baru terbaik = {}", iter, globalBestFitness);
                }
            }

            for (Particle particle : particles) {
                particle.updateVelocity(globalBest, w,
                        config.getCognitiveCoefficient(),
                        config.getSocialCoefficient());
                particle.updatePosition();
            }

            w -= wDecay;

            if (iter % 10 == 0) {
                log.info("Iterasi {}/{}: Best Fitness = {}", iter, config.getMaxIterations(), globalBestFitness);
            }
        }

        return buildResult();
    }

    private void initializeParticles() {
        particles = new Particle[config.getNumParticles()];
        globalBest = new int[jumatDates.size() * masjids.size()];

        for (int i = 0; i < config.getNumParticles(); i++) {
            particles[i] = new Particle(jumatDates.size(), masjids.size(), khotibs.size(), random);

            double fitness = calculateFitness(particles[i]);
            particles[i].setCurrentFitness(fitness);
            particles[i].updatePersonalBest();

            if (fitness > globalBestFitness) {
                globalBestFitness = fitness;
                globalBest = Arrays.copyOf(particles[i].getPosition(), particles[i].getPosition().length);
            }
        }
    }

    private double calculateFitness(Particle particle) {
        double fitness = 0;
        Map<Integer, Integer> khotibScheduleCount = new HashMap<>();
        Map<Integer, Set<Integer>> khotibJumatAssignments = new HashMap<>();

        for (int i = 0; i < khotibs.size(); i++) {
            khotibScheduleCount.put(i, 0);
            khotibJumatAssignments.put(i, new HashSet<>());
        }

        for (int j = 0; j < jumatDates.size(); j++) {
            for (int m = 0; m < masjids.size(); m++) {
                int khotibIdx = particle.getKhotibForSlot(j, m);
                Khotib khotib = khotibs.get(khotibIdx);
                Masjid masjid = masjids.get(m);

                fitness += calculateDistanceScore(khotib, masjid);

                fitness += calculateAreaMatchScore(khotib, masjid);

                fitness += calculateExperienceScore(khotib, masjid);

                fitness += calculateRatingScore(khotib);

                fitness += calculateMasjidPreferenceScore(khotib, masjid);

                khotibScheduleCount.merge(khotibIdx, 1, Integer::sum);

                if (khotibJumatAssignments.get(khotibIdx).contains(j)) {
                    fitness -= config.getWeightNoConflict();
                } else {
                    khotibJumatAssignments.get(khotibIdx).add(j);
                }
            }
        }

        for (int i = 0; i < khotibs.size(); i++) {
            int count = khotibScheduleCount.get(i);
            int maxAllowed = khotibs.get(i).getMaxJadwalPerBulan();

            if (count > maxAllowed) {
                fitness -= config.getWeightMaxJadwalConstraint() * (count - maxAllowed);
            }
        }

        fitness += calculateDistributionFairness(khotibScheduleCount);

        return fitness;
    }

    private double calculateDistanceScore(Khotib khotib, Masjid masjid) {
        if (khotib.getLatitude() == null || khotib.getLongitude() == null
                || masjid.getLatitude() == null || masjid.getLongitude() == null) {
            return 0;
        }

        double distance = calculateHaversineDistance(
                khotib.getLatitude(), khotib.getLongitude(),
                masjid.getLatitude(), masjid.getLongitude()
        );

        double maxDistance = 50.0;
        double normalizedDistance = Math.min(distance / maxDistance, 1.0);

        return config.getWeightDistancePreference() * (1 - normalizedDistance);
    }

    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    private double calculateAreaMatchScore(Khotib khotib, Masjid masjid) {
        if (masjid.getArea() == null || khotib.getPreferredAreas() == null) {
            return 0;
        }

        boolean isPreferred = khotib.getPreferredAreas().stream()
                .anyMatch(area -> area.getId().equals(masjid.getArea().getId()));

        return isPreferred ? config.getWeightAreaMatch() : 0;
    }

    private double calculateExperienceScore(Khotib khotib, Masjid masjid) {
        if (khotib.getPengalamanTahun() == null) {
            return 0;
        }

        double experienceScore = Math.min(khotib.getPengalamanTahun() / 10.0, 1.0);

        if (masjid.getKapasitasJamaah() != null && masjid.getKapasitasJamaah() > 500) {
            experienceScore *= 1.5;
        }

        return config.getWeightKhotibExperience() * experienceScore;
    }

    private double calculateRatingScore(Khotib khotib) {
        if (khotib.getRating() == null) {
            return 0;
        }

        return config.getWeightKhotibRating() * (khotib.getRating() / 5.0);
    }

    private double calculateMasjidPreferenceScore(Khotib khotib, Masjid masjid) {
        if (masjid.getPreferredKhotibs() == null) {
            return 0;
        }

        boolean isPreferred = masjid.getPreferredKhotibs().stream()
                .anyMatch(k -> k.getId().equals(khotib.getId()));

        return isPreferred ? config.getWeightMasjidPreference() : 0;
    }

    private double calculateDistributionFairness(Map<Integer, Integer> khotibScheduleCount) {
        if (khotibScheduleCount.isEmpty()) {
            return 0;
        }

        double mean = khotibScheduleCount.values().stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0);

        double variance = khotibScheduleCount.values().stream()
                .mapToDouble(count -> Math.pow(count - mean, 2))
                .average()
                .orElse(0);

        double stdDev = Math.sqrt(variance);

        return config.getWeightFairDistribution() * (1 / (1 + stdDev));
    }

    private ScheduleResult buildResult() {
        Map<ScheduleSlot, KhotibAssignment> schedule = new HashMap<>();

        for (int j = 0; j < jumatDates.size(); j++) {
            for (int m = 0; m < masjids.size(); m++) {
                int khotibIdx = globalBest[j * masjids.size() + m];
                Khotib khotib = khotibs.get(khotibIdx);
                Masjid masjid = masjids.get(m);
                LocalDate date = jumatDates.get(j);

                double slotFitness = calculateSlotFitness(khotib, masjid);

                ScheduleSlot slot = new ScheduleSlot(date, masjid.getId());
                KhotibAssignment assignment = new KhotibAssignment(
                        khotib.getId(),
                        khotib.getNama(),
                        masjid.getId(),
                        masjid.getNama(),
                        slotFitness
                );

                schedule.put(slot, assignment);
            }
        }

        return new ScheduleResult(schedule, globalBestFitness, config.getMaxIterations());
    }

    private double calculateSlotFitness(Khotib khotib, Masjid masjid) {
        double fitness = 0;
        fitness += calculateDistanceScore(khotib, masjid);
        fitness += calculateAreaMatchScore(khotib, masjid);
        fitness += calculateExperienceScore(khotib, masjid);
        fitness += calculateRatingScore(khotib);
        fitness += calculateMasjidPreferenceScore(khotib, masjid);
        return fitness;
    }

    public record ScheduleSlot(LocalDate date, Long masjidId) {
    }

    public record KhotibAssignment(
            Long khotibId,
            String khotibNama,
            Long masjidId,
            String masjidNama,
            double fitnessScore
    ) {
    }

    public record ScheduleResult(
            Map<ScheduleSlot, KhotibAssignment> schedule,
            double totalFitness,
            int iterations
    ) {
    }
}
