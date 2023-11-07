package com.srmanager.core.common.util

fun String?.capitalizeFirstCharacter(): String {
    return this?.replaceFirstChar{it.titlecase()} ?: ""
}