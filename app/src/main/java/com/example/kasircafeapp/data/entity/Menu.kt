package com.example.kasircafeapp.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "menu_table",
        foreignKeys = [
            ForeignKey (
                entity = Minuman::class,
                parentColumns = ["id_minuman"],
                childColumns = ["minuman_id"],
                onDelete = ForeignKey.CASCADE
            ),
            ForeignKey(
                entity = Makanan::class,
                parentColumns = ["id_makanan"],
                childColumns = ["makanan_id"],
                onDelete = ForeignKey.CASCADE
            )
        ],
        indices = [
            Index(value = ["minuman_id"]),
            Index(value = ["makanan_id"])
        ]
    )
data class Menu(
    @PrimaryKey(autoGenerate = true)
    val id_menu: Int = 0,
    val makanan_id: Int? = null,
    val minuman_id: Int? = null,
    val jumlah: Int,
    val totalHarga: Double
)
