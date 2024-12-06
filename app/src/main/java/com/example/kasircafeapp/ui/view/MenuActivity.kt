    package com.example.kasircafeapp.ui.view

    import android.os.Bundle
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import androidx.lifecycle.ViewModelProvider
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.example.kasircafeapp.R
    import com.example.kasircafeapp.data.entity.Minuman
    import com.example.kasircafeapp.data.entity.Transaksi
    import com.example.kasircafeapp.databinding.ActivityMenuBinding
    import com.example.kasircafeapp.ui.adapter.MakananAdapter
    import com.example.kasircafeapp.ui.adapter.MinumanAdapter
    import com.example.kasircafeapp.ui.viewmodel.MakananViewModel
    import com.example.kasircafeapp.ui.viewmodel.MenuViewModel
    import com.example.kasircafeapp.ui.viewmodel.MinumanViewModel
    import java.text.NumberFormat
    import java.text.SimpleDateFormat
    import java.util.Date
    import java.util.Locale

    class MenuActivity : AppCompatActivity(), MinumanAdapter.onDataChangedListener {

        private lateinit var binding: ActivityMenuBinding
        private lateinit var minumanViewModel: MinumanViewModel
        private lateinit var makananViewModel: MakananViewModel
        private lateinit var menuViewModel: MenuViewModel
        private lateinit var makananAdapter: MakananAdapter
        private lateinit var minumanAdapter: MinumanAdapter

        private var totalHarga: Double = 0.0
        private var jumlahPesanan = 0
        private var namaPesananList = mutableListOf<String>()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMenuBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnMenuPembayaran.setOnClickListener{
                val namaPesanan = this.namaPesananList
                val kategoriPesanan = "minuman"
                val jumlahPesanan = this.jumlahPesanan
                val totalHarga = this.totalHarga
                val tanggalFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val tanggalSekarang = tanggalFormat.format(Date())

                val transaksi = Transaksi(
                    namaPesanan = namaPesananList,
                    kategoriPesanan = kategoriPesanan,
                    jumlahPesanan = jumlahPesanan,
                    jumlahBayar = totalHarga,
                    totalHarga = totalHarga,
                    tanggal = tanggalSekarang
                )

                // Masukkan transaksi ke dalam Room Database
                menuViewModel.insertTransaksi(transaksi)

                // Optional: Tampilkan pesan atau navigasi ke halaman berikutnya
                // Contoh: Menampilkan pesan
                Toast.makeText(this, "Pembayaran berhasil!", Toast.LENGTH_SHORT).show()

                // Optional: Navigasi atau lakukan aksi lain setelah pembayaran
                finish()  // Menutup activity, jika ingin kembali ke halaman sebelumnya

            }

            setSupportActionBar(binding.toolbar)
            supportActionBar?.apply {
                title = "Menu"
                setDisplayHomeAsUpEnabled(true)
                setDisplayShowHomeEnabled(true)
            }

            binding.toolbar.setNavigationOnClickListener {
                finish()
            }

            minumanViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MinumanViewModel::class.java)
            makananViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MakananViewModel::class.java)
            menuViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(MenuViewModel::class.java)

            val recyclerViewMakanan: RecyclerView = findViewById(R.id.recyclerViewMenuMakanan)
            makananAdapter = MakananAdapter(
                useLayoutMenuMakanan = true
            )

            recyclerViewMakanan.adapter = makananAdapter
            recyclerViewMakanan.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            val recyclerViewMinuman: RecyclerView = findViewById(R.id.recyclerViewMenuMinuman)
            minumanAdapter = MinumanAdapter(
                object : MinumanAdapter.OnItemClickListener {
                    override fun onItemClick(minuman: Minuman) {
                        
                    }
                },
                this
            )

            minumanAdapter.useLayoutMenuMinuman = true
            recyclerViewMinuman.adapter = minumanAdapter
            recyclerViewMinuman.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            // observe data makanan
            makananViewModel.allMakanan.observe(this) { makananList ->
                makananAdapter.setMakanan(makananList)
            }

            // observer data minuman
            minumanViewModel.allMinuman.observe(this) { minumanList ->
                minumanAdapter.submitList(minumanList)
            }
        }

        override fun onDataChanged(totalHarga: Double, jumlahPesanan: Int, namaMinumanList: List<String>) {
            this.totalHarga = totalHarga
            this.jumlahPesanan = jumlahPesanan
            this.namaPesananList = namaPesananList.toMutableList()


            // Update UI untuk menampilkan total harga dan jumlah pesanan
            binding.tvTotalHarga.text = formatCurrency(totalHarga)
            binding.tvJumlahPesanan.text = jumlahPesanan.toString()
        }

        // Helper function to format currency (IDR)
        private fun formatCurrency(amount: Double): String {
            val locale = Locale("id", "ID")
            val formatter = NumberFormat.getCurrencyInstance(locale)
            return formatter.format(amount)
        }
    }
