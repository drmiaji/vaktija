package vk.vaktija

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import vk.vaktija.ui.theme.VaktijaTheme
import vk.vaktija.view.HomeScreenView
import vk.vaktija.view.HomeScreenViewModel
import vk.vaktija.view.viewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VaktijaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewModel = viewModel<HomeScreenViewModel>(factory = viewModelFactory {
                        HomeScreenViewModel(MyApp.appModule.prayerTimeRepository)
                    })
                    HomeScreenView(viewModel)
                }
            }
        }
    }
}