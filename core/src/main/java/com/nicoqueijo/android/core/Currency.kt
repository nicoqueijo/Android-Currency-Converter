package com.nicoqueijo.android.core

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a currency with its code and exchange rate.
 *
 * @property currencyCode The three-letter ISO 4217 currency code.
 * @property exchangeRate The exchange rate of this currency to one US Dollar (USD).
 */
@Entity(tableName = "table_currency")
data class Currency(
    @PrimaryKey
    @ColumnInfo(name = "column_currencyCode")
    val currencyCode: String,
    @ColumnInfo(name = "column_exchangeRate")
    val exchangeRate: Double,
    @ColumnInfo(name = "column_order")
    var order: Int = Order.INVALID.position,
    @ColumnInfo(name = "column_isSelected")
    var isSelected: Boolean = false,
)

enum class Order(val position: Int) {
    INVALID(-1),
    FIRST(0),
    SECOND(1),
    THIRD(2),
    FOURTH(3)
}