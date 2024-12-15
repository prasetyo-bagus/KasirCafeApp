    package com.example.kasircafeapp.ui.view

    import android.os.Bundle
    import android.util.Log
    import android.view.View
    import android.widget.FrameLayout
    import android.widget.TextView
    import android.widget.Toast
    import androidx.appcompat.app.AppCompatActivity
    import androidx.cardview.widget.CardView
    import androidx.lifecycle.ViewModelProvider
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.example.kasircafeapp.R
    import com.example.kasircafeapp.data.entity.Minuman
    import com.example.kasircafeapp.data.entity.Transaksi
    import com.example.kasircafeapp.databinding.ActivityMenuBinding
    import com.example.kasircafeapp.ui.adapter.MakananAdapter
    import com.example.kasircafeapp.ui.adapter.MinumanAdapter
    import com.example.kasircafeapp.ui.view.fragment.DetilTransaksiFragment
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
        private var namaMinumanList = mutableListOf<String>()

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMenuBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.btnMenuPembayaran.setOnClickListener {

                val jumlahBayarString = binding.etJumlahBayarMenu.editText?.text.toString()

                if (jumlahBayarValid (jumlahBayarString, totalHarga)) {

                    val jumlahBayar = jumlahBayarString.toDouble()

                    val nominalKembalian = hitungNominalKembalian(jumlahBayar, totalHarga)

                    val transaksi = Transaksi(
                        namaPesanan = namaMinumanList,
                        jumlahPesanan = jumlahPesanan,
                        jumlahBayar = jumlahBayar,
                        totalHarga = totalHarga,
                        nominalKembalian = nominalKembalian,
                        tanggal = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
                    )

                    val bundle = Bundle()
                    bundle.putParcelable("transaksi", transaksi)

                    val fragment = DetilTransaksiFragment()
                    fragment.arguments = bundle

                    // Mengganti tampilan activity
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_transaksi, fragment)
                        .addToBackStack(null)
                        .commit()

                    binding.apply {
                        recyclerViewMenuMakanan.visibility = View.GONE
                        recyclerViewMenuMinuman.visibility = View.GONE
                        tvTitleMakanan.visibility = View.GONE
                        tvTitleMinuman.visibility = View.GONE
                        cardView.visibility = View.GONE
                    }
                    binding.fragmentContainerTransaksi.visibility = View.VISIBLE
                }
            }

            setSupportActionBar(binding.toolbar)
            supportActionBar?.apply {
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

        private fun jumlahBayarValid(jumlahBayarString: String, totalHarga: Double): Boolean {
            if (totalHarga <= 0) {
                Toast.makeText(this, "Buat pesanan terlebih dahulu", Toast.LENGTH_SHORT).show()
                return false
            }

            if (jumlahBayarString.isEmpty()) {
                Toast.makeText(this, "Jumlah bayar tidak boleh kosong", Toast.LENGTH_SHORT).show()
                return false
            }

            val jumlahBayar = jumlahBayarString.toDouble()

            if (jumlahBayar < totalHarga) {
                Toast.makeText(this, "Jumlah bayar kurang dari total harga", Toast.LENGTH_SHORT).show()
                return false
            }
            return true
        }

        private fun hitungNominalKembalian(jumlahBayar: Double, totalHarga: Double): Double {
            return jumlahBayar - totalHarga
        }

        override fun onDataChanged(totalHarga: Double, jumlahPesanan: Int, namaMinumanList: List<String>) {
            this.totalHarga = totalHarga
            this.jumlahPesanan = jumlahPesanan
            this.namaMinumanList = namaMinumanList.toMutableList()

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

        override fun onBackPressed() {
            if (supportFragmentManager.backStackEntryCount > 0) {
                // Hapus fragment dari Back Stack
                supportFragmentManager.popBackStack()

                binding.apply {
                    recyclerViewMenuMakanan.visibility = View.VISIBLE
                    recyclerViewMenuMinuman.visibility = View.VISIBLE
                    tvTitleMakanan.visibility = View.VISIBLE
                    tvTitleMinuman.visibility = View.VISIBLE
                    cardView.visibility = View.VISIBLE
                    fragmentContainerTransaksi.visibility = View.GONE
                }
            } else {
                super.onBackPressed()
            }
        }
    }
