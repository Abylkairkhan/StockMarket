package kz.abyl.stockmarket.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.abyl.stockmarket.data.local.StockDatabase
import kz.abyl.stockmarket.data.mapper.toCompanyListing
import kz.abyl.stockmarket.data.network.StockAPI
import kz.abyl.stockmarket.domain.model.CompanyListing
import kz.abyl.stockmarket.domain.repository.StockRepository
import kz.abyl.stockmarket.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val stockAPI: StockAPI,
    val db: StockDatabase
) : StockRepository {

    private val dao = db.dao
    override suspend fun getCompanyListings(
        fetchFromNetwork: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListings(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))

            val isDatabaseEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDatabaseEmpty && !fetchFromNetwork

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = stockAPI.getListOfStocks()


            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            }
        }
    }

}