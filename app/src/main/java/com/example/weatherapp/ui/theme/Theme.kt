import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.weatherapp.ui.theme.CloudWhite
import com.example.weatherapp.ui.theme.DarkGray
import com.example.weatherapp.ui.theme.DeepBlue
import com.example.weatherapp.ui.theme.LightBlue
import com.example.weatherapp.ui.theme.NightBlue
import com.example.weatherapp.ui.theme.RainyBlue
import com.example.weatherapp.ui.theme.SkyBlue
import com.example.weatherapp.ui.theme.Typography

// Light Theme
private val LightColorScheme = lightColorScheme(
    primary = SkyBlue,
    secondary = LightBlue,
    background = CloudWhite,
    surface = CloudWhite,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

//  Dark Theme
private val DarkColorScheme = darkColorScheme(
    primary = DeepBlue,
    secondary = NightBlue,
    background = RainyBlue,
    surface = RainyBlue,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color.White
)

@Composable
fun WeatherAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // ✅ system setting check karega
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}
