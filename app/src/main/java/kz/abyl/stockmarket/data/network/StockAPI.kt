package kz.abyl.stockmarket.data.network

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockAPI {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListOfStocks(
        @Query("apikey") key: String
    ): ResponseBody

}