package com.srmanager.outlet_data.mapper

import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.outlet_domain.model.CommonResponse

fun CommonResponseDto.toResponse(): CommonResponse{
    return CommonResponse(
        message = "success"
    )
}