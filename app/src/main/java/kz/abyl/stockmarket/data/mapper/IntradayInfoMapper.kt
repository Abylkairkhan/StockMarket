package kz.abyl.stockmarket.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import kz.abyl.stockmarket.data.network.dto.IntradayInfoDto
import kz.abyl.stockmarket.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localeDateTime = LocalDateTime.parse(timestamp, formatter)
    return IntradayInfo(
        date = localeDateTime,
        close = close
    )
}