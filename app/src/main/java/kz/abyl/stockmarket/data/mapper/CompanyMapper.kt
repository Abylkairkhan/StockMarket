package kz.abyl.stockmarket.data.mapper

import kz.abyl.stockmarket.data.local.CompanyListingEntity
import kz.abyl.stockmarket.data.network.dto.CompanyInfoDto
import kz.abyl.stockmarket.domain.model.CompanyInfo
import kz.abyl.stockmarket.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        name = name ?: "",
        description = description ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}