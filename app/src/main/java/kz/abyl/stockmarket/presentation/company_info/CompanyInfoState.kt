package kz.abyl.stockmarket.presentation.company_info

import kz.abyl.stockmarket.domain.model.CompanyInfo
import kz.abyl.stockmarket.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfo: List<IntradayInfo> = emptyList(),
    val companyInfo: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
