package kz.abyl.stockmarket.data.network

import kz.abyl.stockmarket.data.network.dto.CompanyInfoDto
import kz.abyl.stockmarket.util.Credentials
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockAPI {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListOfStocks(
        @Query("apikey") key: String = Credentials.API_KEY
    ): ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") key: String = Credentials.API_KEY
    ): ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") key: String = Credentials.API_KEY
    ): CompanyInfoDto

}