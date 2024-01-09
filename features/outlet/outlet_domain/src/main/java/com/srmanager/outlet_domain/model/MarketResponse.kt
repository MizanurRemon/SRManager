package com.srmanager.outlet_domain.model

import com.srmanager.core.network.dto.MarketItem

data class MarketResponse(
    var data: List<MarketItem> = listOf()
)