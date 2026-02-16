package com.hanyajasa.belajarberhitunganak

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.layout.Layout
import com.hanyajasa.belajarberhitunganak.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BelajarBerhitungAnakTheme {
                AplikasiBelajarBerhitung()
            }
        }
    }
}

// Data class untuk mode permainan
data class ModePermainan(
    val id: String,
    val nama: String,
    val ikon: @Composable () -> Unit,
    val warna: Color,
    val deskripsi: String
)

// Enum untuk layar aktif
enum class LayarAktif {
    MENU_UTAMA,
    MENGHITUNG_BENDA,
    PENJUMLAHAN,
    PENGURANGAN,
    KUIS,
    HASIL
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AplikasiBelajarBerhitung() {
    var layarAktif by remember { mutableStateOf(LayarAktif.MENU_UTAMA) }
    var skorTotal by remember { mutableIntStateOf(0) }
    var namaPemain by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            if (layarAktif != LayarAktif.MENU_UTAMA) {
                TopAppBar(
                    title = { 
                        Text(
                            "Belajar Berhitung",
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { layarAktif = LayarAktif.MENU_UTAMA }) {
                            Icon(Icons.Default.ArrowBack, "Kembali")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = BiruCerah,
                        titleContentColor = Putih,
                        navigationIconContentColor = Putih
                    )
                )
            }
        },
        containerColor = LatarBelakangCerah
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (layarAktif) {
                LayarAktif.MENU_UTAMA -> MenuUtama(
                    skorTotal = skorTotal,
                    onModeSelected = { mode ->
                        layarAktif = when (mode.id) {
                            "menghitung" -> LayarAktif.MENGHITUNG_BENDA
                            "penjumlahan" -> LayarAktif.PENJUMLAHAN
                            "pengurangan" -> LayarAktif.PENGURANGAN
                            "kuis" -> LayarAktif.KUIS
                            else -> LayarAktif.MENU_UTAMA
                        }
                    }
                )
                LayarAktif.MENGHITUNG_BENDA -> ModeMenghitungBenda(
                    onSkorUpdate = { skorTotal += it }
                )
                LayarAktif.PENJUMLAHAN -> ModePenjumlahan(
                    onSkorUpdate = { skorTotal += it }
                )
                LayarAktif.PENGURANGAN -> ModePengurangan(
                    onSkorUpdate = { skorTotal += it }
                )
                LayarAktif.KUIS -> ModeKuis(
                    onSkorUpdate = { skorTotal += it }
                )
                else -> {}
            }
        }
    }
}

@Composable
fun MenuUtama(
    skorTotal: Int,
    onModeSelected: (ModePermainan) -> Unit
) {
    val modePermainan = listOf(
        ModePermainan(
            "menghitung",
            "Menghitung Benda",
            { Icon(Icons.Default.Star, null, modifier = Modifier.size(48.dp), tint = KuningCerah) },
            OrangeCerah,
            "Hitung benda-benda lucu!"
        ),
        ModePermainan(
            "penjumlahan",
            "Penjumlahan",
            { Text("+", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = HijauCerah) },
            HijauCerah,
            "Belajar menambah angka!"
        ),
        ModePermainan(
            "pengurangan",
            "Pengurangan",
            { Text("-", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = MerahCerah) },
            MerahCerah,
            "Belajar mengurangi angka!"
        ),
        ModePermainan(
            "kuis",
            "Kuis Seru",
            { Text("?", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = UnguCerah) },
            UnguCerah,
            "Uji kemampuanmu!"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = BiruCerah),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Selamat Datang!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Putih,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Ayo Belajar Berhitung",
                    style = MaterialTheme.typography.titleLarge,
                    color = Putih
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = KuningCerah,
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Skor: $skorTotal",
                        style = MaterialTheme.typography.headlineSmall,
                        color = KuningCerah,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Text(
            text = "Pilih Permainan:",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            textAlign = TextAlign.Center
        )

        // Grid mode permainan
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(modePermainan) { mode ->
                KartuModePermainan(mode = mode, onClick = { onModeSelected(mode) })
            }
        }
    }
}

@Composable
fun KartuModePermainan(
    mode: ModePermainan,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .scale(scale)
            .clickable {
                isPressed = true
                onClick()
            },
        colors = CardDefaults.cardColors(containerColor = Putih),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(3.dp, mode.warna),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(mode.warna.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                mode.ikon()
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = mode.nama,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = mode.warna,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = mode.deskripsi,
                style = MaterialTheme.typography.bodySmall,
                color = Hitam.copy(alpha = 0.6f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun ModeMenghitungBenda(onSkorUpdate: (Int) -> Unit) {
    var level by remember { mutableIntStateOf(1) }
    var jumlahBenar by remember { mutableIntStateOf(0) }
    var jumlahBenda by remember { mutableIntStateOf(Random.nextInt(1, 6)) }
    var jawabanDipilih by remember { mutableIntStateOf(-1) }
    var isJawabanBenar by remember { mutableStateOf<Boolean?>(null) }
    var showCelebration by remember { mutableStateOf(false) }

    val pilihanJawaban = remember(jumlahBenda) {
        val jawabanBenar = jumlahBenda
        val pilihan = mutableSetOf(jawabanBenar)
        while (pilihan.size < 4) {
            val angka = Random.nextInt(1, 11)
            if (angka != jawabanBenar) pilihan.add(angka)
        }
        pilihan.shuffled()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Level indicator
        Card(
            colors = CardDefaults.cardColors(containerColor = OrangeCerah),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Level $level",
                style = MaterialTheme.typography.titleMedium,
                color = Putih,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Hitung jumlah bintang ini!",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Area benda
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Putih, RoundedCornerShape(20.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalArrangement = Arrangement.Center
            ) {
                repeat(jumlahBenda) { index ->
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = KuningCerah,
                        modifier = Modifier
                            .size(48.dp)
                            .padding(4.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Pilihan jawaban
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(pilihanJawaban.toList()) { jawaban ->
                val isSelected = jawabanDipilih == jawaban
                val backgroundColor = when {
                    isJawabanBenar == true && isSelected -> HijauCerah
                    isJawabanBenar == false && isSelected -> MerahCerah
                    isSelected -> BiruCerah
                    else -> Putih
                }
                val contentColor = if (isSelected || isJawabanBenar == false && isSelected) Putih else Hitam

                Button(
                    onClick = {
                        if (isJawabanBenar == null) {
                            jawabanDipilih = jawaban
                            isJawabanBenar = jawaban == jumlahBenda
                            if (isJawabanBenar == true) {
                                showCelebration = true
                                onSkorUpdate(10)
                                jumlahBenar++
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, if (isSelected) Color.Transparent else BiruCerah),
                    modifier = Modifier.height(80.dp)
                ) {
                    Text(
                        text = jawaban.toString(),
                        style = MaterialTheme.typography.displaySmall,
                        color = contentColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Tombol lanjut
        if (isJawabanBenar != null) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically()
            ) {
                Button(
                    onClick = {
                        jawabanDipilih = -1
                        isJawabanBenar = null
                        showCelebration = false
                        jumlahBenda = Random.nextInt(1, 6 + level)
                        if (jumlahBenar % 5 == 0) level++
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HijauCerah),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                ) {
                    Text(
                        text = if (isJawabanBenar == true) "Hebat! Lanjut →" else "Coba Lagi →",
                        style = MaterialTheme.typography.titleLarge,
                        color = Putih
                    )
                }
            }
        }

        // Animasi perayaan
        if (showCelebration) {
            LaunchedEffect(Unit) {
                delay(1500)
                showCelebration = false
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                repeat(6) { index ->
                    val offsetX = Random.nextInt(-200, 200)
                    val offsetY = Random.nextInt(-200, 200)
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = listOf(MerahCerah, KuningCerah, HijauCerah, BiruCerah, PinkCerah, OrangeCerah)[index],
                        modifier = Modifier
                            .offset(offsetX.dp, offsetY.dp)
                            .size(40.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        val hGapPx = 8.dp.roundToPx()
        val vGapPx = 8.dp.roundToPx()
        val rows = mutableListOf<List<androidx.compose.ui.layout.Placeable>>()
        val rowWidths = mutableListOf<Int>()
        val rowHeights = mutableListOf<Int>()

        var currentRow = mutableListOf<androidx.compose.ui.layout.Placeable>()
        var currentRowWidth = 0
        var currentRowHeight = 0

        measurables.forEach { measurable ->
            val placeable = measurable.measure(constraints)

            if (currentRow.isNotEmpty() &&
                currentRowWidth + hGapPx + placeable.width > constraints.maxWidth) {
                rows.add(currentRow)
                rowWidths.add(currentRowWidth)
                rowHeights.add(currentRowHeight)
                currentRow = mutableListOf()
                currentRowWidth = 0
                currentRowHeight = 0
            }

            currentRow.add(placeable)
            currentRowWidth += if (currentRow.size == 1) placeable.width else hGapPx + placeable.width
            currentRowHeight = maxOf(currentRowHeight, placeable.height)
        }

        if (currentRow.isNotEmpty()) {
            rows.add(currentRow)
            rowWidths.add(currentRowWidth)
            rowHeights.add(currentRowHeight)
        }

        val width = constraints.maxWidth
        val height = rowHeights.sum() + (rows.size - 1) * vGapPx

        layout(width, height) {
            var y = 0
            rows.forEachIndexed { rowIndex, row ->
                var x = when (horizontalArrangement) {
                    Arrangement.Center -> (width - rowWidths[rowIndex]) / 2
                    Arrangement.End -> width - rowWidths[rowIndex]
                    else -> 0
                }
                row.forEach { placeable ->
                    placeable.placeRelative(x, y)
                    x += placeable.width + hGapPx
                }
                y += rowHeights[rowIndex] + vGapPx
            }
        }
    }
}

@Composable
fun ModePenjumlahan(onSkorUpdate: (Int) -> Unit) {
    var level by remember { mutableIntStateOf(1) }
    var angka1 by remember { mutableIntStateOf(Random.nextInt(1, 6)) }
    var angka2 by remember { mutableIntStateOf(Random.nextInt(1, 6)) }
    var jawabanDipilih by remember { mutableIntStateOf(-1) }
    var isJawabanBenar by remember { mutableStateOf<Boolean?>(null) }

    val jawabanBenar = angka1 + angka2
    val pilihanJawaban = remember(angka1, angka2) {
        val pilihan = mutableSetOf(jawabanBenar)
        while (pilihan.size < 4) {
            val offset = Random.nextInt(-5, 6)
            val angka = jawabanBenar + offset
            if (angka in 2..20 && angka != jawabanBenar) pilihan.add(angka)
        }
        pilihan.shuffled()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = HijauCerah),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Level $level",
                style = MaterialTheme.typography.titleMedium,
                color = Putih,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Hitung penjumlahan ini!",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(48.dp))

        // Soal penjumlahan
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            KartuAngka(angka = angka1, warna = BiruCerah)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "+",
                style = MaterialTheme.typography.displayLarge,
                color = HijauCerah,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            KartuAngka(angka = angka2, warna = OrangeCerah)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "=",
                style = MaterialTheme.typography.displayLarge,
                color = Hitam,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        when (isJawabanBenar) {
                            true -> HijauCerah
                            false -> MerahCerah
                            null -> Putih
                        },
                        RoundedCornerShape(20.dp)
                    )
                    .border(3.dp, BiruCerah, RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (jawabanDipilih != -1) {
                    Text(
                        text = jawabanDipilih.toString(),
                        style = MaterialTheme.typography.displayMedium,
                        color = Putih,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = "?",
                        style = MaterialTheme.typography.displayMedium,
                        color = BiruCerah
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Pilihan jawaban
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(pilihanJawaban.toList()) { jawaban ->
                Button(
                    onClick = {
                        if (isJawabanBenar == null) {
                            jawabanDipilih = jawaban
                            isJawabanBenar = jawaban == jawabanBenar
                            if (isJawabanBenar == true) {
                                onSkorUpdate(10)
                                if (level < 5) level++
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when {
                            isJawabanBenar == true && jawaban == jawabanBenar -> HijauCerah
                            isJawabanBenar == false && jawaban == jawabanDipilih -> MerahCerah
                            jawaban == jawabanDipilih -> BiruCerah
                            else -> Putih
                        }
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, HijauCerah),
                    modifier = Modifier.height(80.dp)
                ) {
                    Text(
                        text = jawaban.toString(),
                        style = MaterialTheme.typography.displaySmall,
                        color = if (jawaban == jawabanDipilih || (isJawabanBenar == true && jawaban == jawabanBenar)) Putih else Hitam,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isJawabanBenar != null) {
            Button(
                onClick = {
                    val maxAngka = 5 + level * 2
                    angka1 = Random.nextInt(1, maxAngka)
                    angka2 = Random.nextInt(1, maxAngka)
                    jawabanDipilih = -1
                    isJawabanBenar = null
                },
                colors = ButtonDefaults.buttonColors(containerColor = HijauCerah),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = if (isJawabanBenar == true) "Benar! Lanjut →" else "Salah! Coba Lagi →",
                    style = MaterialTheme.typography.titleLarge,
                    color = Putih
                )
            }
        }
    }
}

@Composable
fun KartuAngka(angka: Int, warna: Color) {
    Card(
        modifier = Modifier.size(100.dp),
        colors = CardDefaults.cardColors(containerColor = warna),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = angka.toString(),
                style = MaterialTheme.typography.displayMedium,
                color = Putih,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ModePengurangan(onSkorUpdate: (Int) -> Unit) {
    var level by remember { mutableIntStateOf(1) }
    var angka1 by remember { mutableIntStateOf(Random.nextInt(5, 11)) }
    var angka2 by remember { mutableIntStateOf(Random.nextInt(1, 5)) }
    var jawabanDipilih by remember { mutableIntStateOf(-1) }
    var isJawabanBenar by remember { mutableStateOf<Boolean?>(null) }

    // Pastikan angka1 > angka2 agar hasil positif
    val jawabanBenar = angka1 - angka2
    val pilihanJawaban = remember(angka1, angka2) {
        val pilihan = mutableSetOf(jawabanBenar)
        while (pilihan.size < 4) {
            val offset = Random.nextInt(-3, 4)
            val angka = jawabanBenar + offset
            if (angka in 0..10 && angka != jawabanBenar) pilihan.add(angka)
        }
        pilihan.shuffled()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = MerahCerah),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = "Level $level",
                style = MaterialTheme.typography.titleMedium,
                color = Putih,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Hitung pengurangan ini!",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            KartuAngka(angka = angka1, warna = BiruCerah)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "-",
                style = MaterialTheme.typography.displayLarge,
                color = MerahCerah,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            KartuAngka(angka = angka2, warna = OrangeCerah)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "=",
                style = MaterialTheme.typography.displayLarge,
                color = Hitam,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(16.dp))
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        when (isJawabanBenar) {
                            true -> HijauCerah
                            false -> MerahCerah
                            null -> Putih
                        },
                        RoundedCornerShape(20.dp)
                    )
                    .border(3.dp, BiruCerah, RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (jawabanDipilih != -1) {
                    Text(
                        text = jawabanDipilih.toString(),
                        style = MaterialTheme.typography.displayMedium,
                        color = Putih,
                        fontWeight = FontWeight.Bold
                    )
                } else {
                    Text(
                        text = "?",
                        style = MaterialTheme.typography.displayMedium,
                        color = BiruCerah
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(pilihanJawaban.toList()) { jawaban ->
                Button(
                    onClick = {
                        if (isJawabanBenar == null) {
                            jawabanDipilih = jawaban
                            isJawabanBenar = jawaban == jawabanBenar
                            if (isJawabanBenar == true) {
                                onSkorUpdate(10)
                                if (level < 5) level++
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when {
                            isJawabanBenar == true && jawaban == jawabanBenar -> HijauCerah
                            isJawabanBenar == false && jawaban == jawabanDipilih -> MerahCerah
                            jawaban == jawabanDipilih -> BiruCerah
                            else -> Putih
                        }
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, MerahCerah),
                    modifier = Modifier.height(80.dp)
                ) {
                    Text(
                        text = jawaban.toString(),
                        style = MaterialTheme.typography.displaySmall,
                        color = if (jawaban == jawabanDipilih || (isJawabanBenar == true && jawaban == jawabanBenar)) Putih else Hitam,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isJawabanBenar != null) {
            Button(
                onClick = {
                    val maxAngka = 8 + level * 2
                    angka1 = Random.nextInt(level + 2, maxAngka)
                    angka2 = Random.nextInt(1, angka1)
                    jawabanDipilih = -1
                    isJawabanBenar = null
                },
                colors = ButtonDefaults.buttonColors(containerColor = HijauCerah),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = if (isJawabanBenar == true) "Benar! Lanjut →" else "Salah! Coba Lagi →",
                    style = MaterialTheme.typography.titleLarge,
                    color = Putih
                )
            }
        }
    }
}

@Composable
fun ModeKuis(onSkorUpdate: (Int) -> Unit) {
    var nomorSoal by remember { mutableIntStateOf(1) }
    var skor by remember { mutableIntStateOf(0) }
    var tipeSoal by remember { mutableIntStateOf(Random.nextInt(0, 3)) }
    var jawabanDipilih by remember { mutableIntStateOf(-1) }
    var isJawabanBenar by remember { mutableStateOf<Boolean?>(null) }
    var soalSelesai by remember { mutableIntStateOf(0) }

    val totalSoal = 10

    // Generate soal berdasarkan tipe
    val soal = remember(tipeSoal, nomorSoal) {
        when (tipeSoal) {
            0 -> { // Menghitung
                val jumlah = Random.nextInt(1, 11)
                SoalKuis(
                    teks = "Berapa jumlah bintang ini?",
                    tipe = TipeSoal.MENGHITUNG,
                    jumlahBenda = jumlah,
                    jawabanBenar = jumlah
                )
            }
            1 -> { // Penjumlahan
                val a = Random.nextInt(1, 11)
                val b = Random.nextInt(1, 11)
                SoalKuis(
                    teks = "$a + $b = ?",
                    tipe = TipeSoal.PENJUMLAHAN,
                    angka1 = a,
                    angka2 = b,
                    jawabanBenar = a + b
                )
            }
            else -> { // Pengurangan
                val a = Random.nextInt(3, 11)
                val b = Random.nextInt(1, a)
                SoalKuis(
                    teks = "$a - $b = ?",
                    tipe = TipeSoal.PENGURANGAN,
                    angka1 = a,
                    angka2 = b,
                    jawabanBenar = a - b
                )
            }
        }
    }

    val pilihanJawaban by remember(soal) {
        mutableStateOf(
            buildSet {
                val jawabanBenar = soal.jawabanBenar
                add(jawabanBenar)
                // Buat pool kandidat yang valid (0-20, kecuali jawaban benar)
                val kandidat = (0..20).filter { it != jawabanBenar }
                // Ambil 3 kandidat secara acak
                addAll(kandidat.shuffled().take(3))
            }.shuffled()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress bar
        LinearProgressIndicator(
            progress = { nomorSoal.toFloat() / totalSoal },
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp),
            color = UnguCerah,
            trackColor = Putih
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Soal $nomorSoal/$totalSoal",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Skor: $skor",
                style = MaterialTheme.typography.titleMedium,
                color = UnguCerah,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Card soal
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Putih),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = soal.teks,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Tampilan khusus untuk menghitung
                if (soal.tipe == TipeSoal.MENGHITUNG) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 150.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            repeat(soal.jumlahBenda ?: 0) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = KuningCerah,
                                    modifier = Modifier
                                        .size(32.dp)
                                        .padding(2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Pilihan jawaban
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(pilihanJawaban.toList()) { jawaban ->
                Button(
                    onClick = {
                        if (isJawabanBenar == null) {
                            jawabanDipilih = jawaban
                            isJawabanBenar = jawaban == soal.jawabanBenar
                            if (isJawabanBenar == true) {
                                skor += 10
                                onSkorUpdate(10)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = when {
                            isJawabanBenar == true && jawaban == soal.jawabanBenar -> HijauCerah
                            isJawabanBenar == false && jawaban == jawabanDipilih -> MerahCerah
                            jawaban == jawabanDipilih -> UnguCerah
                            else -> Putih
                        }
                    ),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(3.dp, UnguCerah),
                    modifier = Modifier.height(80.dp)
                ) {
                    Text(
                        text = jawaban.toString(),
                        style = MaterialTheme.typography.displaySmall,
                        color = if (jawaban == jawabanDipilih || (isJawabanBenar == true && jawaban == soal.jawabanBenar)) Putih else Hitam,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isJawabanBenar != null) {
            Button(
                onClick = {
                    if (nomorSoal < totalSoal) {
                        nomorSoal++
                        tipeSoal = Random.nextInt(0, 3)
                        jawabanDipilih = -1
                        isJawabanBenar = null
                    } else {
                        soalSelesai = nomorSoal
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = UnguCerah),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Text(
                    text = if (nomorSoal < totalSoal) "Lanjut →" else "Selesai",
                    style = MaterialTheme.typography.titleLarge,
                    color = Putih
                )
            }
        }

        // Layar hasil
        if (soalSelesai == totalSoal) {
            AlertDialog(
                onDismissRequest = { },
                title = {
                    Text(
                        text = "Selamat!",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Kuis selesai!",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Skor Akhir: $skor/${totalSoal * 10}",
                            style = MaterialTheme.typography.headlineSmall,
                            color = UnguCerah,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        when {
                            skor >= 80 -> Text("Luar Biasa!", color = HijauCerah)
                            skor >= 60 -> Text("Bagus Sekali!", color = BiruCerah)
                            skor >= 40 -> Text("Lumayan!", color = OrangeCerah)
                            else -> Text("Terus Berlatih!", color = MerahCerah)
                        }
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            soalSelesai = 0
                            nomorSoal = 1
                            skor = 0
                            tipeSoal = Random.nextInt(0, 3)
                            jawabanDipilih = -1
                            isJawabanBenar = null
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = UnguCerah)
                    ) {
                        Text("Main Lagi")
                    }
                }
            )
        }
    }
}

// Data class dan enum untuk soal kuis
enum class TipeSoal {
    MENGHITUNG, PENJUMLAHAN, PENGURANGAN
}

data class SoalKuis(
    val teks: String,
    val tipe: TipeSoal,
    val angka1: Int? = null,
    val angka2: Int? = null,
    val jumlahBenda: Int? = null,
    val jawabanBenar: Int
)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BelajarBerhitungAnakTheme {
        AplikasiBelajarBerhitung()
    }
}
