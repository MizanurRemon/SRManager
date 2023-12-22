package com.srmanager.outlet_domain.use_cases


import com.srmanager.outlet_domain.repository.OutletRepository
import com.srmanager.core.network.model.OutletAddRequest
import com.srmanager.outlet_domain.model.CommonResponse
import com.srmanager.outlet_domain.model.OutletAddModel

class OutletAddUseCase(private val outletRepository: OutletRepository) {
    suspend operator fun invoke(outletAddModel: OutletAddModel): Result<CommonResponse> {
        return outletRepository.addOutlet(
            OutletAddRequest(
                id = outletAddModel.id,
                outletImage = outletAddModel.outletImage,
                outletName = outletAddModel.outletName,
                ownerName = outletAddModel.ownerName,
                dateOfBirth = outletAddModel.dateOfBirth,
                mobileNo = outletAddModel.mobileNo,
                secondaryMobileNo = outletAddModel.secondaryMobileNo,
                tradeLicense = outletAddModel.tradeLicense,
                expiryDate = outletAddModel.expiryDate,
                vat = outletAddModel.vat,
                address = outletAddModel.address,
                latitude = outletAddModel.latitude,
                longitude = outletAddModel.longitude
            )
        )
    }
}