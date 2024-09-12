package com.meskitah

import android.app.Application
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.androidXModule
import di.mainModule

class TranslateApplication : Application(), DIAware {
    override val di: DI by DI.lazy {
        import(androidXModule(this@TranslateApplication))
        import(mainModule)
    }
}