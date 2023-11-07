package com.srmanager.core.designsystem.components

import com.srmanager.core.common.util.LanguageListEnum

data class LanguageResponse(
    val title: LanguageListEnum,
    val image: Int,
    val tag: String,
    var selected: Boolean = false
)


