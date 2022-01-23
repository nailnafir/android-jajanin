package com.nailnafir.jajanin.utils

import android.widget.TextView
import com.google.gson.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Helpers {
    fun TextView.formatPrice(value: String) {
        this.text = getCurrencyIDR(java.lang.Double.parseDouble(value))
    }

    private fun getCurrencyIDR(price: Double): String {
        val format = DecimalFormat("#,###,###")
        return "IDR " + format.format(price).replace(",".toRegex(), ".")
    }

    fun getDefaultGson(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .registerTypeAdapter(Date::class.java, JsonDeserializer { json, _, _ ->
                val formatServer = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                formatServer.timeZone = TimeZone.getTimeZone("UTC")
                formatServer.parse(json.asString)
            })
            .registerTypeAdapter(Date::class.java, JsonSerializer<Date> { src, _, _ ->
                val format = SimpleDateFormat("", Locale.ENGLISH)
                format.timeZone = TimeZone.getTimeZone("UTC")
                if (src != null) {
                    JsonPrimitive(format.format(src))
                } else {
                    null
                }
            }).create()
    }

    fun Long.convertLongToTime(formatDate: String): String {
        val date = Date(this)
        val formatted = SimpleDateFormat(formatDate)

        return formatted.format(date)
    }
}