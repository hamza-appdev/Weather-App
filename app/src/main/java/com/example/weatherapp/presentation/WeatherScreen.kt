package com.example.weatherapp.presentation

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PresentToAll
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Vrpano
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.filled.WindPower
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.data.remote.dto.WeatherModel
import com.example.weatherapp.domain.repository.weather.WeatherReposiroey
import com.example.weatherapp.util.Resultt
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(WeatherViewModel: WeatherViewModel= viewModel()) {
    val weatherresut = WeatherViewModel.weatherresut.value
    var city by remember { mutableStateOf("") }
    var keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(modifier = Modifier.fillMaxSize(), containerColor =MaterialTheme.colorScheme.background,
        //top app bar
        topBar = {
           TopAppBar(
               title = {
                   Text("Weather App",
                       fontWeight = FontWeight.Bold
                       )
               },
               colors = TopAppBarDefaults.topAppBarColors(
                   containerColor = colorResource(R.color.skyblue),
                   titleContentColor =  MaterialTheme.colorScheme.onPrimary,
               )

           )
        }

    ) {innerpadding->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerpadding)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)

            ){
                //search bar session
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = city,
                    onValueChange = {
                        city=it
                    },
                  placeholder = {
                      Text("Enter city name",
                          color = Color.Black
                      )
                  },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorResource(R.color.skyblue),
                        unfocusedBorderColor = colorResource(R.color.lightblue),
                        focusedContainerColor = Color.LightGray,
                        unfocusedContainerColor = Color.LightGray,
                        cursorColor = Color.Black
                    ),
                    textStyle = androidx.compose.ui.text.TextStyle(
                        color = Color.Black,
                        fontSize = 18.sp
                    )


                    )
                Spacer(modifier = Modifier.width(4.dp))
                Button(
                    onClick = {
                        WeatherViewModel.getdata(city)
                        keyboardController?.hide()
                    },
                    modifier = Modifier.height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.skyblue)
                    )
                ) {
                   Text("Search", color = Color.White,
                       fontSize = 15.sp
                       )
                }
            }
            when(val result =weatherresut){
                is Resultt.Initial -> {
                    EmptyState()
                }
                is Resultt.Loading -> {
                    CircularProgressIndicator()
                }
                is Resultt.Success<*> -> {
                   Weatherdetails(data = result.data as WeatherModel)
                }
                is Resultt.Error->{
                    Text(result.message)
                }
            }
        }
    }
}

@Composable
fun  Weatherdetails(data: WeatherModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ){
            Icon(imageVector = Icons.Default.LocationOn, contentDescription = "location",
                modifier = Modifier.size(37.dp)
                )
            Text(data.location.name,
                fontSize = 29.sp,
                )
            Spacer(modifier = Modifier.width(8.dp))
            Text(data.location.country,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
                )
        }

    Spacer(modifier = Modifier.height(13.dp))
    Text(text = "${data.current.temp_c.toInt()} °C",
        fontSize = 56.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
    AsyncImage(
        model ="https:${data.current.condition.icon}".replace("64x64","128x128"),
        contentDescription = "icon image",
        modifier = Modifier.size(110.dp)
    )
    Text(text = data.current.condition.text,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        color =  MaterialTheme.colorScheme.onSurfaceVariant
    )
    Spacer(modifier = Modifier.height(10.dp))

//        Card session
    Card(
        modifier = Modifier.padding(10.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(colorResource(R.color.skyblue))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ){
                weatherKeyvalue(icon = Icons.Default.Bloodtype,"Humidity","${data.current.humidity.toString()}%")
                weatherKeyvalue(icon = Icons.Default.WindPower,"Wind Speed","${data.current.wind_kph.toString()}km/h")
            }

            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
            ){
                weatherKeyvalue(icon = Icons.Default.Air,"WindChill","${data.current.windchill_c.toString()}")
                weatherKeyvalue(icon = Icons.Default.PresentToAll,"Pressure","${data.current.wind_kph.toString()}mb")
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
               weatherKeyvalue(icon = Icons.Default.WatchLater,"Local Time",data.location.localtime.split(" ")[1])
               weatherKeyvalue(icon = Icons.Default.CalendarMonth,"Local Date",data.location.localtime.split(" ")[0])
            }

        }
    }

}}

@Composable
fun weatherKeyvalue(icon: ImageVector?= null, key: String, value: String) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = key,
                modifier = Modifier.size(22.dp) ,
                tint = colorResource(R.color.white),
            )
        }
        Text(key, fontWeight = FontWeight.Bold, color = Color.White)
        Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold,color = Color.White)
    }
}



@Composable
fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(R.color.lightblue)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_weather),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint =colorResource(R.color.white)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Search for a city",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter a city name above to see the current weather",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

