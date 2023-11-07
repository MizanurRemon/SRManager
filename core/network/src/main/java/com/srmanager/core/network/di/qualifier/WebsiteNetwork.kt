package com.srmanager.core.network.di.qualifier

import com.srmanager.core.network.di.TypeEnum
import javax.inject.Qualifier


@Qualifier
@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
annotation class WebsiteNetwork(val value: TypeEnum)
