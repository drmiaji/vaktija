package vk.vaktija

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import vk.vaktija.ui.theme.VaktijaTheme
import vk.vaktija.ui.HomeScreenView
import vk.vaktija.ui.HomeScreenViewModel
import vk.vaktija.ui.shared.TopBar
import vk.vaktija.ui.viewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VaktijaTheme {
                // A surface container using the 'background' color from the theme
                Scaffold(
                    topBar = {
                        TopBar()
                    }
                ) { innerPadding ->
                    Box(Modifier.padding(innerPadding)) {
                        val viewModel = viewModel<HomeScreenViewModel>(factory = viewModelFactory {
                            HomeScreenViewModel(MyApp.appModule.prayerTimeRepository)
                        })

                        HomeScreenView(viewModel)
                    }
                }
            }
        }
    }
}