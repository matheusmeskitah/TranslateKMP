package com.meskitah

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI

class MainActivity : ComponentActivity(), DIAware {

    override val di: DI by closestDI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
//    App()
}