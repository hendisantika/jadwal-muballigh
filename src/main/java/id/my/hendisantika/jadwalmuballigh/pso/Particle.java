package id.my.hendisantika.jadwalmuballigh.pso;

import lombok.Data;

import java.util.Arrays;
import java.util.Random;

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
public class Particle {

    private final int numJumat;
    private final int numMasjid;
    private final int numKhotib;
    private final Random random;
    private int[] position;
    private double[] velocity;
    private int[] personalBest;
    private double personalBestFitness;
    private double currentFitness;

    public Particle(int numJumat, int numMasjid, int numKhotib, Random random) {
        this.numJumat = numJumat;
        this.numMasjid = numMasjid;
        this.numKhotib = numKhotib;
        this.random = random;

        int totalSlots = numJumat * numMasjid;
        this.position = new int[totalSlots];
        this.velocity = new double[totalSlots];
        this.personalBest = new int[totalSlots];
        this.personalBestFitness = Double.NEGATIVE_INFINITY;

        initializeRandom();
    }

    private void initializeRandom() {
        for (int i = 0; i < position.length; i++) {
            position[i] = random.nextInt(numKhotib);
            velocity[i] = random.nextDouble() * 2 - 1;
        }
        personalBest = Arrays.copyOf(position, position.length);
    }

    public void updatePersonalBest() {
        if (currentFitness > personalBestFitness) {
            personalBestFitness = currentFitness;
            personalBest = Arrays.copyOf(position, position.length);
        }
    }

    public void updateVelocity(int[] globalBest, double w, double c1, double c2) {
        for (int i = 0; i < velocity.length; i++) {
            double r1 = random.nextDouble();
            double r2 = random.nextDouble();

            velocity[i] = w * velocity[i]
                    + c1 * r1 * (personalBest[i] - position[i])
                    + c2 * r2 * (globalBest[i] - position[i]);

            velocity[i] = Math.max(-numKhotib, Math.min(numKhotib, velocity[i]));
        }
    }

    public void updatePosition() {
        for (int i = 0; i < position.length; i++) {
            double newPos = position[i] + velocity[i];

            if (random.nextDouble() < Math.abs(velocity[i]) / numKhotib) {
                position[i] = (int) Math.round(newPos);
            }

            position[i] = Math.max(0, Math.min(numKhotib - 1, position[i]));
        }
    }

    public int getKhotibForSlot(int jumatIndex, int masjidIndex) {
        return position[jumatIndex * numMasjid + masjidIndex];
    }

    public void setKhotibForSlot(int jumatIndex, int masjidIndex, int khotibIndex) {
        position[jumatIndex * numMasjid + masjidIndex] = khotibIndex;
    }
}
