package com.srmanager.core.designsystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.srmanager.core.designsystem.r

@Composable
fun ImageComposer(imagePath: String, modifier: Modifier){
    Image(
        painter = rememberAsyncImagePainter(imagePath),
        contentDescription = null,
        modifier = modifier
    )
}

@Composable
@Preview
fun PreviewImageLoader(){

    ImageComposer(imagePath = "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg", modifier = Modifier.size(100.r()))
}