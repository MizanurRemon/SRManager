package com.srmanager.outlet_data.mapper

import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.dto.OutletDataDto
import com.srmanager.outlet_domain.model.CommonResponse
import com.srmanager.outlet_domain.model.OutletResponse


fun CommonResponseDto.toResponse(): CommonResponse {
    return CommonResponse(
        message = "success"
    )
}

fun OutletDataDto.toResponse(): OutletResponse {
    return OutletResponse(
        data = data
    )
}


