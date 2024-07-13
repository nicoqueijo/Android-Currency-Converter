package com.nicoqueijo.android.network

import com.nicoqueijo.android.core.model.Currency
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class representing exchange rates for various currencies against the USD.
 *
 * This class is designed to reflect the `rates` object from the JSON response of the Open Exchange Rates API.
 * Each property in this class corresponds to a specific currency and its exchange rate against the USD.
 * The properties are annotated with @Serializable to specify the JSON key names during serialization and
 * deserialization.
 */
@Serializable
data class ExchangeRates(

    @SerialName("AED")
    val USD_AED: Double,

    @SerialName("AFN")
    val USD_AFN: Double,

    @SerialName("ALL")
    val USD_ALL: Double,

    @SerialName("AMD")
    val USD_AMD: Double,

    @SerialName("AOA")
    val USD_AOA: Double,

    @SerialName("ARS")
    val USD_ARS: Double,

    @SerialName("AUD")
    val USD_AUD: Double,

    @SerialName("AZN")
    val USD_AZN: Double,

    @SerialName("BAM")
    val USD_BAM: Double,

    @SerialName("BBD")
    val USD_BBD: Double,

    @SerialName("BDT")
    val USD_BDT: Double,

    @SerialName("BGN")
    val USD_BGN: Double,

    @SerialName("BHD")
    val USD_BHD: Double,

    @SerialName("BMD")
    val USD_BMD: Double,

    @SerialName("BOB")
    val USD_BOB: Double,

    @SerialName("BRL")
    val USD_BRL: Double,

    @SerialName("BSD")
    val USD_BSD: Double,

    @SerialName("BTC")
    val USD_BTC: Double,

    @SerialName("BWP")
    val USD_BWP: Double,

    @SerialName("BYN")
    val USD_BYN: Double,

    @SerialName("BZD")
    val USD_BZD: Double,

    @SerialName("CAD")
    val USD_CAD: Double,

    @SerialName("CDF")
    val USD_CDF: Double,

    @SerialName("CHF")
    val USD_CHF: Double,

    @SerialName("CLP")
    val USD_CLP: Double,

    @SerialName("CNY")
    val USD_CNY: Double,

    @SerialName("COP")
    val USD_COP: Double,

    @SerialName("CRC")
    val USD_CRC: Double,

    @SerialName("CUP")
    val USD_CUP: Double,

    @SerialName("CVE")
    val USD_CVE: Double,

    @SerialName("CZK")
    val USD_CZK: Double,

    @SerialName("DKK")
    val USD_DKK: Double,

    @SerialName("DOP")
    val USD_DOP: Double,

    @SerialName("DZD")
    val USD_DZD: Double,

    @SerialName("EGP")
    val USD_EGP: Double,

    @SerialName("ETB")
    val USD_ETB: Double,

    @SerialName("EUR")
    val USD_EUR: Double,

    @SerialName("FJD")
    val USD_FJD: Double,

    @SerialName("GBP")
    val USD_GBP: Double,

    @SerialName("GEL")
    val USD_GEL: Double,

    @SerialName("GHS")
    val USD_GHS: Double,

    @SerialName("GMD")
    val USD_GMD: Double,

    @SerialName("GNF")
    val USD_GNF: Double,

    @SerialName("GTQ")
    val USD_GTQ: Double,

    @SerialName("HKD")
    val USD_HKD: Double,

    @SerialName("HNL")
    val USD_HNL: Double,

    @SerialName("HRK")
    val USD_HRK: Double,

    @SerialName("HTG")
    val USD_HTG: Double,

    @SerialName("HUF")
    val USD_HUF: Double,

    @SerialName("IDR")
    val USD_IDR: Double,

    @SerialName("ILS")
    val USD_ILS: Double,

    @SerialName("INR")
    val USD_INR: Double,

    @SerialName("IQD")
    val USD_IQD: Double,

    @SerialName("IRR")
    val USD_IRR: Double,

    @SerialName("ISK")
    val USD_ISK: Double,

    @SerialName("JMD")
    val USD_JMD: Double,

    @SerialName("JOD")
    val USD_JOD: Double,

    @SerialName("JPY")
    val USD_JPY: Double,

    @SerialName("KES")
    val USD_KES: Double,

    @SerialName("KGS")
    val USD_KGS: Double,

    @SerialName("KHR")
    val USD_KHR: Double,

    @SerialName("KPW")
    val USD_KPW: Double,

    @SerialName("KRW")
    val USD_KRW: Double,

    @SerialName("KWD")
    val USD_KWD: Double,

    @SerialName("KYD")
    val USD_KYD: Double,

    @SerialName("KZT")
    val USD_KZT: Double,

    @SerialName("LBP")
    val USD_LBP: Double,

    @SerialName("LKR")
    val USD_LKR: Double,

    @SerialName("LRD")
    val USD_LRD: Double,

    @SerialName("LSL")
    val USD_LSL: Double,

    @SerialName("LYD")
    val USD_LYD: Double,

    @SerialName("MAD")
    val USD_MAD: Double,

    @SerialName("MDL")
    val USD_MDL: Double,

    @SerialName("MKD")
    val USD_MKD: Double,

    @SerialName("MMK")
    val USD_MMK: Double,

    @SerialName("MNT")
    val USD_MNT: Double,

    @SerialName("MOP")
    val USD_MOP: Double,

    @SerialName("MVR")
    val USD_MVR: Double,

    @SerialName("MXN")
    val USD_MXN: Double,

    @SerialName("MYR")
    val USD_MYR: Double,

    @SerialName("MZN")
    val USD_MZN: Double,

    @SerialName("NAD")
    val USD_NAD: Double,

    @SerialName("NGN")
    val USD_NGN: Double,

    @SerialName("NIO")
    val USD_NIO: Double,

    @SerialName("NOK")
    val USD_NOK: Double,

    @SerialName("NPR")
    val USD_NPR: Double,

    @SerialName("NZD")
    val USD_NZD: Double,

    @SerialName("OMR")
    val USD_OMR: Double,

    @SerialName("PAB")
    val USD_PAB: Double,

    @SerialName("PEN")
    val USD_PEN: Double,

    @SerialName("PGK")
    val USD_PGK: Double,

    @SerialName("PHP")
    val USD_PHP: Double,

    @SerialName("PKR")
    val USD_PKR: Double,

    @SerialName("PLN")
    val USD_PLN: Double,

    @SerialName("PYG")
    val USD_PYG: Double,

    @SerialName("QAR")
    val USD_QAR: Double,

    @SerialName("RON")
    val USD_RON: Double,

    @SerialName("RSD")
    val USD_RSD: Double,

    @SerialName("RUB")
    val USD_RUB: Double,

    @SerialName("RWF")
    val USD_RWF: Double,

    @SerialName("SAR")
    val USD_SAR: Double,

    @SerialName("SEK")
    val USD_SEK: Double,

    @SerialName("SGD")
    val USD_SGD: Double,

    @SerialName("SOS")
    val USD_SOS: Double,

    @SerialName("SSP")
    val USD_SSP: Double,

    @SerialName("SVC")
    val USD_SVC: Double,

    @SerialName("SYP")
    val USD_SYP: Double,

    @SerialName("THB")
    val USD_THB: Double,

    @SerialName("TND")
    val USD_TND: Double,

    @SerialName("TRY")
    val USD_TRY: Double,

    @SerialName("TTD")
    val USD_TTD: Double,

    @SerialName("TWD")
    val USD_TWD: Double,

    @SerialName("TZS")
    val USD_TZS: Double,

    @SerialName("UAH")
    val USD_UAH: Double,

    @SerialName("UGX")
    val USD_UGX: Double,

    @SerialName("USD")
    val USD_USD: Double,

    @SerialName("UYU")
    val USD_UYU: Double,

    @SerialName("VES")
    val USD_VES: Double,

    @SerialName("VND")
    val USD_VND: Double,

    @SerialName("XAG")
    val USD_XAG: Double,

    @SerialName("XAU")
    val USD_XAU: Double,

    @SerialName("ZAR")
    val USD_ZAR: Double,

    @SerialName("ZMW")
    val USD_ZMW: Double,

    @SerialName("ZWL")
    val USD_ZWL: Double,
) {
    /**
     * Creates a list of [Currency]s from the declared fields of this class using reflection to
     * instantiate each object's currency code and exchange rate with the declared field's name and
     * value respectively. Filters out the "Companion" field generated by the @Serializable
     * annotation.
     */
    val currencies: List<Currency>
        get() = javaClass.declaredFields
            .filterNot { it.name == "Companion" }
            .map { declaredField ->
                Currency(
                    currencyCode = declaredField.name,
                    exchangeRate = declaredField[this] as Double
                )
            }

    override fun toString() = currencies.toString()
}
