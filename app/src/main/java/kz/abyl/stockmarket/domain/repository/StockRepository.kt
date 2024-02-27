package kz.abyl.stockmarket.domain.repository

import kotlinx.coroutines.flow.Flow
import kz.abyl.stockmarket.domain.model.CompanyListing
import kz.abyl.stockmarket.util.Resource

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromNetwork: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

}