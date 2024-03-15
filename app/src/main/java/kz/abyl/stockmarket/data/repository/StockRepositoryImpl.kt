package kz.abyl.stockmarket.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kz.abyl.stockmarket.data.csv.CSVParser
import kz.abyl.stockmarket.data.csv.IntradayInfoParser
import kz.abyl.stockmarket.data.local.StockDatabase
import kz.abyl.stockmarket.data.mapper.toCompanyInfo
import kz.abyl.stockmarket.data.mapper.toCompanyListing
import kz.abyl.stockmarket.data.mapper.toCompanyListingEntity
import kz.abyl.stockmarket.data.network.StockAPI
import kz.abyl.stockmarket.domain.model.CompanyInfo
import kz.abyl.stockmarket.domain.model.CompanyListing
import kz.abyl.stockmarket.domain.model.IntradayInfo
import kz.abyl.stockmarket.domain.repository.StockRepository
import kz.abyl.stockmarket.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val stockAPI: StockAPI,
    private val db: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListing>,
    private val intradayInfoParser: CSVParser<IntradayInfo>
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
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListings?.let { listings ->
                emit(Resource.Success(listings))
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = dao.searchCompanyListings("").map {
                        it.toCompanyListing()
                    }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = stockAPI.getIntradayInfo(symbol)
            val result = intradayInfoParser.parse(response.byteStream())
            Resource.Success(result)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = e.message.toString())
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = e.message())
        }
    }

    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val response = stockAPI.getCompanyInfo(symbol)
            Resource.Success(response.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(message = e.message.toString())
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(message = e.message())
        }
    }

}