package com.srmanager.core.designsystem

import com.srmanager.core.common.util.LanguageListEnum
import com.srmanager.core.designsystem.components.LanguageResponse

val languageList = mutableListOf(
    LanguageResponse(LanguageListEnum.English, R.drawable.ic_flag_england, tag = "en"),
    LanguageResponse(LanguageListEnum.Nederlands, R.drawable.ic_flag_netherland, tag = "nl"),
/*    LanguageResponse(LanguageListEnum.Español, R.drawable.ic_flag_spain, tag = "es"),
    LanguageResponse(LanguageListEnum.Deutsch, R.drawable.ic_flag_germany, tag = "de"),
    LanguageResponse(LanguageListEnum.Français, R.drawable.ic_flag_france, tag = "fr"),
    LanguageResponse(LanguageListEnum.Italiano, R.drawable.ic_flag_italy, tag = "it"),*/
)