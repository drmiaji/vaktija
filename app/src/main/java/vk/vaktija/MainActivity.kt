package vk.vaktija

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import vk.vaktija.ui.theme.VaktijaTheme
import vk.vaktija.ui.HomeScreenView
import vk.vaktija.ui.HomeScreenViewModel
import vk.vaktija.ui.viewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VaktijaTheme {
                // A surface container using the 'background' color from the theme
                Surface {
                    val viewModel = viewModel<HomeScreenViewModel>(factory = viewModelFactory {
                        HomeScreenViewModel(MyApp.appModule.prayerTimeRepository)
                    })
                    HomeScreenView(viewModel)
                }
            }
        }
    }
}