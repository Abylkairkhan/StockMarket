package kz.abyl.stockmarket.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_listings")
data class CompanyListingEntity(
    @PrimaryKey val id: Int? = null,
    val name: String,
    val symbol: String,
    val exchange: String
)
