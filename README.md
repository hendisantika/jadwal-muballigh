# Jadwal Muballigh

Aplikasi web untuk manajemen jadwal khotib Jum'at dengan optimasi menggunakan algoritma **Particle Swarm Optimization (
PSO)**.

## Teknologi

- **Spring Boot 4.0.0** - Framework Java
- **JDK 25** - Java Development Kit
- **PostgreSQL 18** - Database
- **Thymeleaf** - Template Engine
- **Hibernate/JPA** - ORM
- **Docker Compose** - Container untuk PostgreSQL

## Fitur

### Manajemen Data

- **Area** - Kelola area/wilayah (kecamatan, kota)
- **Masjid** - Kelola data masjid dengan koordinat lokasi dan kapasitas jamaah
- **Khotib** - Kelola data khotib dengan pengalaman, rating, dan preferensi area
- **Jadwal** - Lihat dan kelola jadwal khotib per bulan

### Optimasi PSO

Algoritma Particle Swarm Optimization digunakan untuk mengoptimalkan penempatan khotib dengan mempertimbangkan:

- **Jarak** - Preferensi khotib yang dekat dengan masjid (menggunakan formula Haversine)
- **Area Match** - Kesesuaian area preferensi khotib dengan lokasi masjid
- **Pengalaman** - Khotib dengan pengalaman lebih tinggi mendapat skor lebih baik
- **Rating** - Khotib dengan rating lebih tinggi diprioritaskan
- **Distribusi Adil** - Jadwal didistribusikan merata ke semua khotib
- **Pencegahan Konflik** - Tidak ada khotib yang dijadwalkan di 2 masjid pada hari yang sama

### Konfigurasi PSO

```java
weightDistancePreference =10.0   // Bobot jarak
weightAreaMatch =20.0            // Bobot kesesuaian area
weightKhotibExperience =5.0      // Bobot pengalaman
weightKhotibRating =8.0          // Bobot rating
weightMaxJadwalConstraint =50.0  // Bobot batas maksimal jadwal
weightNoConflict =100.0          // Bobot pencegahan konflik
```

## Cara Menjalankan

### Prasyarat

- JDK 25
- Docker dan Docker Compose

### Langkah-langkah

1. Clone repository

```bash
git clone <repository-url>
cd jadwal-muballigh
```

2. Jalankan PostgreSQL dengan Docker Compose

```bash
docker compose up -d
```

3. Jalankan aplikasi

```bash
./mvnw spring-boot:run
```

4. Buka browser dan akses `http://localhost:8080`

## Struktur Project

```
src/main/java/id/my/hendisantika/jadwalmuballigh/
├── config/          # Konfigurasi aplikasi
├── controller/      # REST dan Web controllers
├── dto/             # Data Transfer Objects
├── entity/          # JPA Entities
├── pso/             # Algoritma PSO
│   ├── Particle.java
│   ├── PSOConfig.java
│   └── ScheduleOptimizer.java
├── repository/      # JPA Repositories
└── service/         # Business Logic Services
```

## API Endpoints

### Area

- `GET /api/areas` - List semua area
- `POST /api/areas` - Tambah area baru
- `PUT /api/areas/{id}` - Update area
- `DELETE /api/areas/{id}` - Hapus area

### Masjid

- `GET /api/masjids` - List semua masjid
- `POST /api/masjids` - Tambah masjid baru
- `PUT /api/masjids/{id}` - Update masjid
- `DELETE /api/masjids/{id}` - Hapus masjid

### Khotib

- `GET /api/khotibs` - List semua khotib
- `GET /api/khotibs/active` - List khotib aktif
- `POST /api/khotibs` - Tambah khotib baru
- `PUT /api/khotibs/{id}` - Update khotib
- `PUT /api/khotibs/{id}/toggle-active` - Toggle status aktif
- `DELETE /api/khotibs/{id}` - Hapus khotib

### Jadwal

- `GET /api/jadwal?bulan=YYYY-MM` - List jadwal per bulan
- `POST /api/jadwal/generate` - Generate jadwal dengan PSO
- `PUT /api/jadwal/{id}/change-khotib?khotibId={id}` - Ganti khotib
- `DELETE /api/jadwal/{id}` - Hapus jadwal

## Web Pages

- `/` - Dashboard dengan statistik dan ringkasan jadwal
- `/jadwal` - Halaman jadwal dengan navigasi bulan dan generate jadwal
- `/khotib` - Manajemen data khotib
- `/masjid` - Manajemen data masjid
- `/area` - Manajemen data area

## Database

Aplikasi menggunakan PostgreSQL 18. Konfigurasi default:

- Host: `localhost`
- Port: `5435`
- Database: `jadwal_muballigh`
- Username: `postgres`
- Password: `postgres`

## Data Seeder

Pada profile `dev`, aplikasi akan otomatis mengisi data contoh:

- 5 Area (Pusat Kota, Utara, Selatan, Timur, Barat)
- 8 Masjid dengan koordinat di Bandung
- 10 Khotib dengan berbagai pengalaman dan rating

## Lisensi

MIT License
