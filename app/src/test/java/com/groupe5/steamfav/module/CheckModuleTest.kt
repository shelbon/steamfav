package com.groupe5.steamfav.module

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.groupe5.steamfav.di.appModule
import org.junit.jupiter.api.Test
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.test.KoinTest
import org.koin.test.verify.verify

@OptIn(KoinExperimentalAPI::class)
class CheckModulesTest : KoinTest {

    @Test
    fun checkAllModules() {
        appModule.verify(extraTypes = listOf(Context::class, SavedStateHandle::class))
    }
}