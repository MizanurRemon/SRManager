package com.srmanager.outlet_data.mapper

import com.srmanager.core.common.util.DATE_FORMAT
import com.srmanager.core.common.util.changeDateFormat
import com.srmanager.core.network.dto.CheckOutStatusDto
import com.srmanager.core.network.dto.CommonResponseDto
import com.srmanager.core.network.dto.MarketDto
import com.srmanager.core.network.dto.OutletDataDto
import com.srmanager.core.network.dto.OutletDetailsDto
import com.srmanager.core.network.dto.OutletProfile
import com.srmanager.outlet_domain.model.CheckOutStatusResponse
import com.srmanager.outlet_domain.model.CommonResponse
import com.srmanager.outlet_domain.model.MarketResponse
import com.srmanager.outlet_domain.model.OutletDetailsResponse
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

fun OutletDetailsDto.toResponse(): OutletDetailsResponse {
    return OutletDetailsResponse(
        data = OutletProfile(
            id = data?.id ?: 0,
            dateCreated = data?.dateCreated ?: "",
            unitInfoId = data?.unitInfoId ?: 0,
            expiryDate = changeDateFormat(
                data!!.lastUpdated!!,
                "yyyy-MM-dd'T'HH:mm:ss",
                DATE_FORMAT
            ).toString(),
            lastUpdated = data?.lastUpdated ?: "",
            secondaryMobileNo = data?.secondaryMobileNo ?: "",
            outletStatusId = data?.outletStatusId ?: 0,
            updatedBy = data?.updatedBy ?: 0,
            dateOfBirth = changeDateFormat(
                data!!.dateOfBirth!!,
                "yyyy-MM-dd'T'HH:mm:ss",
                DATE_FORMAT
            ).toString(),
            tradeLicense = data?.tradeLicense ?: "",
            domainStatusId = data?.domainStatusId ?: 0,
            latitude = data?.latitude ?: "",
            assignedTo = data?.assignedTo ?: 0,
            address = data?.address ?: "",
            ownerName = data?.ownerName ?: "",
            longitude = data?.longitude ?: "",
            mobileNo = data?.mobileNo ?: "",
            outletImage = data?.outletImage ?: "",
            statusRemarks = data?.statusRemarks ?: "",
            createdBy = data?.createdBy ?: 0,
            outletName = data?.outletName ?: "",
            lastVisited = data?.lastVisited ?: "",
            vat = data?.vat ?: "",
            shopEthnicity = data?.shopEthnicity ?: "",
            routeName = data?.routeName ?: "",
            ownerEmail = data?.ownerEmail ?: "",
            paymentTerms = data?.paymentTerms ?: "",
            marketId = data?.marketId ?: 0
        )
    )
}

fun MarketDto.toResponse(): MarketResponse {
    return MarketResponse(
        data = data
    )
}

fun CheckOutStatusDto.toResponse(): CheckOutStatusResponse {
    return CheckOutStatusResponse(
        data = data
    )
}


