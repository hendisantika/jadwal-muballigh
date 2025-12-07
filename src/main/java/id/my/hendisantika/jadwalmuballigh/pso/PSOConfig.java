package id.my.hendisantika.jadwalmuballigh.pso;

import lombok.Builder;
import lombok.Data;

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
@Data
@Builder
public class PSOConfig {

    @Builder.Default
    private int numParticles = 50;

    @Builder.Default
    private int maxIterations = 100;

    @Builder.Default
    private double inertiaWeight = 0.7;

    @Builder.Default
    private double cognitiveCoefficient = 1.5;

    @Builder.Default
    private double socialCoefficient = 1.5;

    @Builder.Default
    private double inertiaWeightMin = 0.4;

    @Builder.Default
    private double inertiaWeightMax = 0.9;

    @Builder.Default
    private double weightDistancePreference = 10.0;

    @Builder.Default
    private double weightAreaMatch = 20.0;

    @Builder.Default
    private double weightKhotibExperience = 5.0;

    @Builder.Default
    private double weightKhotibRating = 8.0;

    @Builder.Default
    private double weightMaxJadwalConstraint = 50.0;

    @Builder.Default
    private double weightNoConflict = 100.0;

    @Builder.Default
    private double weightFairDistribution = 15.0;

    @Builder.Default
    private double weightMasjidPreference = 12.0;
}
