package com.example.siponapp

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest

@Composable
fun StartScreen(onStartClicked: () -> Unit) {
    val context =LocalContext.current
    val imageLoader=coil.ImageLoader.Builder(context)
        .components {
            if (Build.VERSION.SDK_INT>=28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }
        .build()

    Box(modifier= Modifier.fillMaxSize()) {
        AsyncImage(
            model= ImageRequest.Builder(context)
                .data(R.drawable.gifstartingscreen)
                .crossfade(true)
                .build(),
            contentDescription=null,
            imageLoader=imageLoader,
            modifier= Modifier.fillMaxSize(),
            contentScale = androidx.compose.ui.layout.ContentScale.Crop
        )
        Box(
            modifier= Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color.Black.copy(alpha=0.4f))
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(modifier=Modifier.height(100.dp),
                text = "SIP",
                fontFamily=Atop,
                fontSize = 72.sp,
                textAlign = TextAlign.Center,
                color=androidx.compose.ui.graphics.Color(0xFF7a003d)
            )
            Text(modifier=Modifier.height(100.dp),
                text = "ON",
                fontFamily=Atop,
                fontSize = 72.sp,
                textAlign = TextAlign.Center,
                color=androidx.compose.ui.graphics.Color(0xFFf63e50)
            )
            Spacer(modifier = Modifier.height(150.dp))
            Button(
                onClick = onStartClicked,
                modifier = Modifier
                    .height(75.dp)
                    .padding(bottom=25.dp)
                ,
                colors = ButtonDefaults.buttonColors(
                    containerColor=androidx.compose.ui.graphics.Color(0xFFee6d50),
                    contentColor=androidx.compose.ui.graphics.Color.White
                )
            ) {
                Text(
                    text="Start the party",
                    fontFamily=ComicSans,
                    fontSize=22.sp,
                    color=androidx.compose.ui.graphics.Color(0xFFf0d5c0)
                )


            }
        }
    }
}
