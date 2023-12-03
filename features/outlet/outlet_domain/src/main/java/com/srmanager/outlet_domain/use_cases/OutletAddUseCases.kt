package com.srmanager.outlet_domain.use_cases

import com.srmanager.core.network.model.OutletAddRequest
import com.srmanager.outlet_domain.model.CommonResponse
import com.srmanager.outlet_domain.model.OutletModel
import com.srmanager.outlet_domain.repository.OutletRepository

data class OutletAddUseCases(private val outletRepository: OutletRepository) {
    suspend operator fun invoke(outletModel: OutletModel): Result<CommonResponse> {
        return outletRepository.addOutlet(
            OutletAddRequest(
                outletImage = outletModel.outletImage,
                outletName = outletModel.outletName,
                ownerName = outletModel.ownerName,
                dateOfBirth = outletModel.dateOfBirth,
                mobileNo = outletModel.mobileNo,
                secondaryMobileNo = outletModel.secondaryMobileNo,
                tradeLicense = outletModel.tradeLicense,
                expiryDate = outletModel.expiryDate,
                vat = outletModel.vat,
                address = outletModel.address,
                latitude = outletModel.latitude,
                longitude = outletModel.longitude
            )
        )
    }
}
