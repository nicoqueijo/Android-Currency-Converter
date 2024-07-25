package com.nicoqueijo.android.network

import com.nicoqueijo.android.network.model.ApiOperation
import com.nicoqueijo.android.network.model.ExchangeRates
import com.nicoqueijo.android.network.model.OpenExchangeRatesEndPoint
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeTypeOf
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.junit.jupiter.params.provider.ArgumentsSource
import java.util.stream.Stream

class KtorClientTest {

    private lateinit var httpEngine: MockEngine
    private lateinit var httpClient: HttpClient

    private lateinit var subject: KtorClient

    @Nested
    inner class GetExchangeRates {

        @Nested
        inner class Success {

            @Test
            fun `given valid JSON response, when getExchangeRates is called, then it should return success with correct data`() =
                runTest {
                    httpEngine = MockEngine {
                        respond(
                            content = ByteReadChannel(
                                text = getJsonPayload()
                            ),
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json")
                        )
                    }
                    httpClient = HttpClient(httpEngine) {
                        install(ContentNegotiation) {
                            json(
                                Json {
                                    prettyPrint = true
                                    ignoreUnknownKeys = true
                                }
                            )
                        }
                    }
                    subject = KtorClient(
                        httpClient = httpClient,
                    )

                    val actual = subject.getExchangeRates()

                    actual.shouldBeTypeOf<ApiOperation.Success<OpenExchangeRatesEndPoint>>()
                    actual.data.timestamp.shouldBe(1721865600L)
                    actual.data.exchangeRates.shouldBeTypeOf<ExchangeRates>()
                }

            private fun getJsonPayload() =
                """
            {
              "disclaimer": "Usage subject to terms: https://openexchangerates.org/terms",
              "license": "https://openexchangerates.org/license",
              "timestamp": 1721865600,
              "base": "USD",
              "rates": {
                "AED": 3.673,
                "AFN": 70.554853,
                "ALL": 92.739014,
                "AMD": 388.213696,
                "ANG": 1.802659,
                "AOA": 874.545333,
                "ARS": 927.9915,
                "AUD": 1.521273,
                "AWG": 1.8025,
                "AZN": 1.7,
                "BAM": 1.804227,
                "BBD": 2,
                "BDT": 117.526602,
                "BGN": 1.80426,
                "BHD": 0.376897,
                "BIF": 2882.850792,
                "BMD": 1,
                "BND": 1.345006,
                "BOB": 6.911375,
                "BRL": 5.6553,
                "BSD": 1,
                "BTC": 0.000015300135,
                "BTN": 83.726795,
                "BWP": 13.57183,
                "BYN": 3.273401,
                "BZD": 2.016218,
                "CAD": 1.381187,
                "CDF": 2846.081526,
                "CHF": 0.885131,
                "CLF": 0.034342,
                "CLP": 947.888589,
                "CNH": 7.26919,
                "CNY": 7.2622,
                "COP": 4019.741121,
                "CRC": 530.253431,
                "CUC": 1,
                "CUP": 25.75,
                "CVE": 101.954097,
                "CZK": 23.443349,
                "DJF": 177.762377,
                "DKK": 6.885671,
                "DOP": 59.289192,
                "DZD": 134.505669,
                "EGP": 48.3401,
                "ERN": 15,
                "ETB": 57.691136,
                "EUR": 0.92262,
                "FJD": 2.2554,
                "FKP": 0.775316,
                "GBP": 0.775316,
                "GEL": 2.725,
                "GGP": 0.775316,
                "GHS": 15.506128,
                "GIP": 0.775316,
                "GMD": 67.775,
                "GNF": 8616.972084,
                "GTQ": 7.760999,
                "GYD": 209.269301,
                "HKD": 7.809069,
                "HNL": 24.794013,
                "HRK": 6.951451,
                "HTG": 132.028288,
                "HUF": 363.006267,
                "IDR": 16258.59734,
                "ILS": 3.649715,
                "IMP": 0.775316,
                "INR": 83.743048,
                "IQD": 1310.190565,
                "IRR": 42092.5,
                "ISK": 137.94,
                "JEP": 0.775316,
                "JMD": 156.539115,
                "JOD": 0.7087,
                "JPY": 153.92984615,
                "KES": 131.814292,
                "KGS": 84.2273,
                "KHR": 4111.913711,
                "KMF": 454.224949,
                "KPW": 900,
                "KRW": 1383.735,
                "KWD": 0.305694,
                "KYD": 0.833572,
                "KZT": 474.072686,
                "LAK": 22174.668164,
                "LBP": 89580.623308,
                "LKR": 303.631848,
                "LRD": 195.308862,
                "LSL": 18.327426,
                "LYD": 4.836342,
                "MAD": 9.894636,
                "MDL": 17.730591,
                "MGA": 4542.112397,
                "MKD": 56.838042,
                "MMK": 2098,
                "MNT": 3398,
                "MOP": 8.046051,
                "MRU": 39.696373,
                "MUR": 46.729997,
                "MVR": 15.35,
                "MWK": 1734.833749,
                "MXN": 18.365863,
                "MYR": 4.6725,
                "MZN": 63.899991,
                "NAD": 18.327426,
                "NGN": 1584.25,
                "NIO": 36.820203,
                "NOK": 11.029445,
                "NPR": 133.962812,
                "NZD": 1.687564,
                "OMR": 0.384943,
                "PAB": 1,
                "PEN": 3.758594,
                "PGK": 3.923756,
                "PHP": 58.581491,
                "PKR": 278.540774,
                "PLN": 3.964878,
                "PYG": 7575.468219,
                "QAR": 3.642627,
                "RON": 4.5857,
                "RSD": 108.002,
                "RUB": 86.244226,
                "RWF": 1314.239208,
                "SAR": 3.751361,
                "SBD": 8.475946,
                "SCR": 13.865286,
                "SDG": 586,
                "SEK": 10.77918,
                "SGD": 1.343901,
                "SHP": 0.775316,
                "SLL": 20969.5,
                "SOS": 571.403939,
                "SRD": 29.152,
                "SSP": 130.26,
                "STD": 22281.8,
                "STN": 22.700423,
                "SVC": 8.751764,
                "SYP": 2512.53,
                "SZL": 18.350428,
                "THB": 36.139214,
                "TJS": 10.652577,
                "TMT": 3.505,
                "TND": 3.107,
                "TOP": 2.376628,
                "TRY": 32.855661,
                "TTD": 6.79883,
                "TWD": 32.7309,
                "TZS": 2691.407213,
                "UAH": 41.250632,
                "UGX": 3701.55012,
                "USD": 1,
                "UYU": 40.183011,
                "UZS": 12590.467801,
                "VES": 36.512874,
                "VND": 25358.188023,
                "VUV": 118.722,
                "WST": 2.8,
                "XAF": 605.198972,
                "XAG": 0.03460956,
                "XAU": 0.00041687,
                "XCD": 2.70255,
                "XDR": 0.754421,
                "XOF": 605.198972,
                "XPD": 0.00110126,
                "XPF": 110.097838,
                "XPT": 0.00105702,
                "YER": 250.350066,
                "ZAR": 18.3568,
                "ZMW": 26.056032,
                "ZWL": 322
              }
            }
        """.trimIndent()
        }

        @Nested
        inner class Failure {

            @ParameterizedTest
            @ArgumentsSource(HttpStatusCodeProvider::class)
            fun `given non-OK HTTP status code, when getExchangeRates is called, then it should return failure with correct exception message`(
                statusCode: HttpStatusCode
            ) = runTest {
                httpEngine = MockEngine {
                    respond(
                        content = ByteReadChannel(
                            text = ""
                        ),
                        status = statusCode,
                        headers = headersOf(HttpHeaders.ContentType, "application/json")
                    )
                }
                httpClient = HttpClient(httpEngine) {
                    install(ContentNegotiation) {
                        json(
                            Json {
                                prettyPrint = true
                                ignoreUnknownKeys = true
                            }
                        )
                    }
                }
                val subject = KtorClient(
                    httpClient = httpClient,
                )

                val actual = subject.getExchangeRates()

                actual.shouldBeTypeOf<ApiOperation.Failure<OpenExchangeRatesEndPoint>>()
                actual.exception.message.shouldBe("Failed to fetch exchange rates.")
            }
        }
    }
}

object HttpStatusCodeProvider : ArgumentsProvider {
    override fun provideArguments(context: ExtensionContext?): Stream<out Arguments> {
        return Stream.of(
            Arguments.of(HttpStatusCode.BadRequest),
            Arguments.of(HttpStatusCode.NoContent),
            Arguments.of(HttpStatusCode.Forbidden),
            Arguments.of(HttpStatusCode.NotFound),
            Arguments.of(HttpStatusCode.RequestTimeout),
            Arguments.of(HttpStatusCode.InternalServerError),
        )
    }
}