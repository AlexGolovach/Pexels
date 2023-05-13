package com.renovavision.file_helper

import org.koin.dsl.module

val helpersModule = module {
    single { FileHelper(get()) }
}