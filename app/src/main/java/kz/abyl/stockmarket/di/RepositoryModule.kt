package kz.abyl.stockmarket.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kz.abyl.stockmarket.data.csv.CSVParser
import kz.abyl.stockmarket.data.csv.CompanyListingParser
import kz.abyl.stockmarket.data.csv.IntradayInfoParser
import kz.abyl.stockmarket.data.repository.StockRepositoryImpl
import kz.abyl.stockmarket.domain.model.CompanyListing
import kz.abyl.stockmarket.domain.model.IntradayInfo
import kz.abyl.stockmarket.domain.repository.StockRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>

}