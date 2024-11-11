package com.example.kasircafeapp.data.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.kasircafeapp.data.entity.Makanan
import com.example.kasircafeapp.data.entity.Menu
import com.example.kasircafeapp.data.entity.Minuman

data class MenuWithDetails(
    @Embedded val menu: Menu,

    @Relation(
        parentColumn = "makanan_id",
        entityColumn = "id_makanan"
    )
    val makanan: Makanan?,

    @Relation(
        parentColumn = "minuman_id",
        entityColumn = "id_minuman"
    )
    val minuman: Minuman?
)
