package com.srmanager.core.network.dto


import kotlinx.serialization.Serializable

@Serializable
data class MarketDto(
    var data: List<MarketItem> = listOf()
)

@Serializable
data class MarketItem(
    var id: Int?,
    var text: String?
)