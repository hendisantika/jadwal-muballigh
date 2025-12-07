// API Base URL
const API_BASE = '/api';

// Utility Functions
function showAlert(message, type = 'success') {
    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type}`;
    alertDiv.textContent = message;

    const container = document.querySelector('.container');
    container.insertBefore(alertDiv, container.firstChild);

    setTimeout(() => alertDiv.remove(), 5000);
}

function formatDate(dateString) {
    const options = {weekday: 'long', year: 'numeric', month: 'long', day: 'numeric'};
    return new Date(dateString).toLocaleDateString('id-ID', options);
}

function formatMonth(yearMonth) {
    const [year, month] = yearMonth.split('-');
    const date = new Date(year, month - 1);
    return date.toLocaleDateString('id-ID', {year: 'numeric', month: 'long'});
}

// Modal Functions
function openModal(modalId) {
    document.getElementById(modalId).classList.add('active');
}

function closeModal(modalId) {
    document.getElementById(modalId).classList.remove('active');
}

// Close modal when clicking outside
document.addEventListener('click', function (e) {
    if (e.target.classList.contains('modal')) {
        e.target.classList.remove('active');
    }
});

// Area CRUD
async function loadAreas() {
    try {
        const response = await fetch(`${API_BASE}/areas`);
        const areas = await response.json();
        return areas;
    } catch (error) {
        console.error('Error loading areas:', error);
        return [];
    }
}

async function saveArea(data) {
    try {
        const method = data.id ? 'PUT' : 'POST';
        const url = data.id ? `${API_BASE}/areas/${data.id}` : `${API_BASE}/areas`;

        const response = await fetch(url, {
            method: method,
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });

        if (response.ok) {
            showAlert('Area berhasil disimpan!');
            location.reload();
        } else {
            const error = await response.json();
            showAlert(error.message || 'Gagal menyimpan area', 'danger');
        }
    } catch (error) {
        showAlert('Terjadi kesalahan: ' + error.message, 'danger');
    }
}

async function deleteArea(id) {
    if (!confirm('Yakin ingin menghapus area ini?')) return;

    try {
        const response = await fetch(`${API_BASE}/areas/${id}`, {method: 'DELETE'});
        if (response.ok) {
            showAlert('Area berhasil dihapus!');
            location.reload();
        } else {
            showAlert('Gagal menghapus area', 'danger');
        }
    } catch (error) {
        showAlert('Terjadi kesalahan: ' + error.message, 'danger');
    }
}

// Masjid CRUD
async function saveMasjid(data) {
    try {
        const method = data.id ? 'PUT' : 'POST';
        const url = data.id ? `${API_BASE}/masjids/${data.id}` : `${API_BASE}/masjids`;

        const response = await fetch(url, {
            method: method,
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });

        if (response.ok) {
            showAlert('Masjid berhasil disimpan!');
            location.reload();
        } else {
            const error = await response.json();
            showAlert(error.message || 'Gagal menyimpan masjid', 'danger');
        }
    } catch (error) {
        showAlert('Terjadi kesalahan: ' + error.message, 'danger');
    }
}

async function deleteMasjid(id) {
    if (!confirm('Yakin ingin menghapus masjid ini?')) return;

    try {
        const response = await fetch(`${API_BASE}/masjids/${id}`, {method: 'DELETE'});
        if (response.ok) {
            showAlert('Masjid berhasil dihapus!');
            location.reload();
        } else {
            showAlert('Gagal menghapus masjid', 'danger');
        }
    } catch (error) {
        showAlert('Terjadi kesalahan: ' + error.message, 'danger');
    }
}

// Khotib CRUD
async function saveKhotib(data) {
    try {
        const method = data.id ? 'PUT' : 'POST';
        const url = data.id ? `${API_BASE}/khotibs/${data.id}` : `${API_BASE}/khotibs`;

        const response = await fetch(url, {
            method: method,
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        });

        if (response.ok) {
            showAlert('Khotib berhasil disimpan!');
            location.reload();
        } else {
            const error = await response.json();
            showAlert(error.message || 'Gagal menyimpan khotib', 'danger');
        }
    } catch (error) {
        showAlert('Terjadi kesalahan: ' + error.message, 'danger');
    }
}

async function deleteKhotib(id) {
    if (!confirm('Yakin ingin menghapus khotib ini?')) return;

    try {
        const response = await fetch(`${API_BASE}/khotibs/${id}`, {method: 'DELETE'});
        if (response.ok) {
            showAlert('Khotib berhasil dihapus!');
            location.reload();
        } else {
            showAlert('Gagal menghapus khotib', 'danger');
        }
    } catch (error) {
        showAlert('Terjadi kesalahan: ' + error.message, 'danger');
    }
}

async function toggleKhotibActive(id) {
    try {
        const response = await fetch(`${API_BASE}/khotibs/${id}/toggle-active`, {method: 'PATCH'});
        if (response.ok) {
            showAlert('Status khotib berhasil diubah!');
            location.reload();
        } else {
            showAlert('Gagal mengubah status khotib', 'danger');
        }
    } catch (error) {
        showAlert('Terjadi kesalahan: ' + error.message, 'danger');
    }
}

// Schedule Generation
async function generateSchedule() {
    const bulanInput = document.getElementById('generateBulan');
    const numParticles = document.getElementById('numParticles')?.value || 50;
    const maxIterations = document.getElementById('maxIterations')?.value || 100;

    if (!bulanInput || !bulanInput.value) {
        showAlert('Pilih bulan terlebih dahulu', 'warning');
        return;
    }

    const generateBtn = document.getElementById('generateBtn');
    const originalText = generateBtn.innerHTML;
    generateBtn.innerHTML = '<span class="spinner"></span> Mengoptimasi...';
    generateBtn.disabled = true;

    try {
        const response = await fetch(`${API_BASE}/jadwal/generate`, {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify({
                bulan: bulanInput.value,
                numParticles: parseInt(numParticles),
                maxIterations: parseInt(maxIterations)
            })
        });

        const result = await response.json();

        if (response.ok) {
            showAlert(result.message);
            setTimeout(() => {
                window.location.href = `/jadwal/${bulanInput.value}`;
            }, 1000);
        } else {
            showAlert(result.message || 'Gagal membuat jadwal', 'danger');
        }
    } catch (error) {
        showAlert('Terjadi kesalahan: ' + error.message, 'danger');
    } finally {
        generateBtn.innerHTML = originalText;
        generateBtn.disabled = false;
    }
}

// Update Jadwal Khotib
async function updateJadwalKhotib(jadwalId, khotibId) {
    try {
        const response = await fetch(`${API_BASE}/jadwal/${jadwalId}/khotib/${khotibId}`, {
            method: 'PATCH'
        });

        if (response.ok) {
            showAlert('Khotib berhasil diubah!');
            location.reload();
        } else {
            const error = await response.json();
            showAlert(error.message || 'Gagal mengubah khotib', 'danger');
        }
    } catch (error) {
        showAlert('Terjadi kesalahan: ' + error.message, 'danger');
    }
}

// Delete Jadwal
async function deleteJadwal(id) {
    if (!confirm('Yakin ingin menghapus jadwal ini?')) return;

    try {
        const response = await fetch(`${API_BASE}/jadwal/${id}`, {method: 'DELETE'});
        if (response.ok) {
            showAlert('Jadwal berhasil dihapus!');
            location.reload();
        } else {
            showAlert('Gagal menghapus jadwal', 'danger');
        }
    } catch (error) {
        showAlert('Terjadi kesalahan: ' + error.message, 'danger');
    }
}

// Initialize page-specific functionality
document.addEventListener('DOMContentLoaded', function () {
    // Set current month as default for generate form
    const bulanInput = document.getElementById('generateBulan');
    if (bulanInput && !bulanInput.value) {
        const now = new Date();
        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, '0');
        bulanInput.value = `${year}-${month}`;
    }

    // Form submissions
    const areaForm = document.getElementById('areaForm');
    if (areaForm) {
        areaForm.addEventListener('submit', function (e) {
            e.preventDefault();
            const formData = new FormData(areaForm);
            saveArea({
                id: formData.get('id') || null,
                nama: formData.get('nama'),
                kecamatan: formData.get('kecamatan'),
                kota: formData.get('kota')
            });
        });
    }

    const masjidForm = document.getElementById('masjidForm');
    if (masjidForm) {
        masjidForm.addEventListener('submit', function (e) {
            e.preventDefault();
            const formData = new FormData(masjidForm);
            saveMasjid({
                id: formData.get('id') || null,
                nama: formData.get('nama'),
                alamat: formData.get('alamat'),
                kapasitasJamaah: parseInt(formData.get('kapasitasJamaah')) || null,
                latitude: parseFloat(formData.get('latitude')) || null,
                longitude: parseFloat(formData.get('longitude')) || null,
                areaId: parseInt(formData.get('areaId')) || null
            });
        });
    }

    const khotibForm = document.getElementById('khotibForm');
    if (khotibForm) {
        khotibForm.addEventListener('submit', function (e) {
            e.preventDefault();
            const formData = new FormData(khotibForm);

            const preferredAreaIds = [];
            const areaCheckboxes = document.querySelectorAll('input[name="preferredAreaIds"]:checked');
            areaCheckboxes.forEach(cb => preferredAreaIds.push(parseInt(cb.value)));

            saveKhotib({
                id: formData.get('id') || null,
                nama: formData.get('nama'),
                noTelepon: formData.get('noTelepon'),
                alamat: formData.get('alamat'),
                latitude: parseFloat(formData.get('latitude')) || null,
                longitude: parseFloat(formData.get('longitude')) || null,
                spesialisasiTema: formData.get('spesialisasiTema'),
                pengalamanTahun: parseInt(formData.get('pengalamanTahun')) || null,
                rating: parseFloat(formData.get('rating')) || null,
                aktif: formData.get('aktif') === 'on',
                maxJadwalPerBulan: parseInt(formData.get('maxJadwalPerBulan')) || 4,
                preferredAreaIds: preferredAreaIds
            });
        });
    }
});

// Edit functions
function editArea(id, nama, kecamatan, kota) {
    document.getElementById('areaId').value = id;
    document.getElementById('areaNama').value = nama;
    document.getElementById('areaKecamatan').value = kecamatan || '';
    document.getElementById('areaKota').value = kota || '';
    openModal('areaModal');
}

function editMasjid(id, nama, alamat, kapasitasJamaah, latitude, longitude, areaId) {
    document.getElementById('masjidId').value = id;
    document.getElementById('masjidNama').value = nama;
    document.getElementById('masjidAlamat').value = alamat || '';
    document.getElementById('masjidKapasitas').value = kapasitasJamaah || '';
    document.getElementById('masjidLatitude').value = latitude || '';
    document.getElementById('masjidLongitude').value = longitude || '';
    document.getElementById('masjidAreaId').value = areaId || '';
    openModal('masjidModal');
}

function editKhotib(data) {
    document.getElementById('khotibId').value = data.id;
    document.getElementById('khotibNama').value = data.nama;
    document.getElementById('khotibTelepon').value = data.noTelepon || '';
    document.getElementById('khotibAlamat').value = data.alamat || '';
    document.getElementById('khotibLatitude').value = data.latitude || '';
    document.getElementById('khotibLongitude').value = data.longitude || '';
    document.getElementById('khotibSpesialisasi').value = data.spesialisasiTema || '';
    document.getElementById('khotibPengalaman').value = data.pengalamanTahun || '';
    document.getElementById('khotibRating').value = data.rating || '';
    document.getElementById('khotibAktif').checked = data.aktif;
    document.getElementById('khotibMaxJadwal').value = data.maxJadwalPerBulan || 4;

    // Uncheck all areas first
    document.querySelectorAll('input[name="preferredAreaIds"]').forEach(cb => cb.checked = false);
    // Check preferred areas
    if (data.preferredAreaIds) {
        data.preferredAreaIds.forEach(areaId => {
            const cb = document.querySelector(`input[name="preferredAreaIds"][value="${areaId}"]`);
            if (cb) cb.checked = true;
        });
    }

    openModal('khotibModal');
}

function openChangeKhotibModal(jadwalId, currentKhotibId) {
    document.getElementById('changeJadwalId').value = jadwalId;
    document.getElementById('changeKhotibId').value = currentKhotibId;
    openModal('changeKhotibModal');
}

function confirmChangeKhotib() {
    const jadwalId = document.getElementById('changeJadwalId').value;
    const khotibId = document.getElementById('changeKhotibId').value;
    updateJadwalKhotib(jadwalId, khotibId);
}
