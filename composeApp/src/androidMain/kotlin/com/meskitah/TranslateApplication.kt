package com.meskitah

import android.app.Application
import org.kodein.di.DI
import org.kodein.di.DIAware
import translate.di.mainModule

class TranslateApplication : Application(), DIAware {
    override val di: DI by DI.lazy {
        import(mainModule)
    }
}