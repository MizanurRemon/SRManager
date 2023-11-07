package com.srmanager.core.designsystem

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.srmanager.core.common.R as CommonR
import com.srmanager.core.designsystem.R as DesignSystemR

val IPRankingList = listOf(
    IpRanksType.OFFICER, IpRanksType.DETECTIVE, IpRanksType.SERGEANT, IpRanksType.LIEUTENANT
)

enum class IpRanksType(
    @StringRes val rankNameStringRes: Int,
    @DrawableRes val iconResId: Int,
    val minLimit: Int,
    val maxLimit: Int,
) {
    OFFICER(
        CommonR.string.officer,
        DesignSystemR.drawable.ic_rank_officer,
        minLimit = 0,
        maxLimit = 250
    ),
    DETECTIVE(
        CommonR.string.detective,
        DesignSystemR.drawable.ic_rank_detective,
        minLimit = 251,
        maxLimit = 1500
    ),
    SERGEANT(
        CommonR.string.sergeant,
        DesignSystemR.drawable.ic_sergeant,
        minLimit = 1501,
        maxLimit = 10000
    ),
    LIEUTENANT(
        CommonR.string.lieutenant,
        DesignSystemR.drawable.ic_lieutenant,
        minLimit = 10001,
        maxLimit = Int.MAX_VALUE
    );

    companion object {
        @JvmStatic
        fun fromScore(score: Int): IpRanksType =
            values().find { value -> value.maxLimit >= score && value.minLimit <= score } ?: OFFICER
    }

}

enum class IpReportStatusType(
    @StringRes val reportStatusTypeNameStringRes: Int,
    @DrawableRes val iconResId: Int,
) {
    NEW(CommonR.string.new_status, DesignSystemR.drawable.ic_pending),
    PENDING(CommonR.string.pending, DesignSystemR.drawable.ic_pending),
    APPROVED(CommonR.string.verified, DesignSystemR.drawable.ic_verified),
    VERIFIED(CommonR.string.confirmed, DesignSystemR.drawable.ic_green_check);
}

