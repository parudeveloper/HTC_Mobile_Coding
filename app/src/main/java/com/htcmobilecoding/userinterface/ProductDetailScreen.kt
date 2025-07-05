package com.htcmobilecoding.userinterface

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.htcmobilecoding.data.Resource
import com.htcmobilecoding.model.ProductDetails
import com.htcmobilecoding.viewmodel.ProductDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int, navController: NavController,
    productDetailsViewModel: ProductDetailsViewModel = hiltViewModel()
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Product Details") }, navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back",tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF00897B),
                    titleContentColor = Color.White
                )
            )
        }, containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            val productDetailsData by productDetailsViewModel.productDetails.collectAsStateWithLifecycle()

            LaunchedEffect(productId) {
                productDetailsViewModel.getProductDetails(productId)
            }

            when (productDetailsData) {
                is Resource.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is Resource.Error -> {
                    Text("Error loading product details")
                }

                is Resource.Success -> {
                    val product = (productDetailsData as Resource.Success<ProductDetails>).data

                    ProductDetailContent(product!!)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductDetailContent(product: ProductDetails) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Image
        val context = LocalContext.current
       /* AsyncImage(
            model = ImageRequest.Builder(context)
                .data(product.imageUrl)
                .addHeader("User-Agent", "Mozilla/5.0") // for Meijer URLs
                .crossfade(true)
                .build(),
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray)
        )*/

        GlideImage(
            model = product.imageUrl,
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Title
        Text(
            text = "Title: ${product.title}",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Description
        Text(
            text = "Description:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        //Summary
        Text(
            text = product.summary,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Price: ${product.price}",
            style = MaterialTheme.typography.titleMedium
        )


        Spacer(modifier = Modifier.height(24.dp))

        // Share Button
        /*Button(onClick = {
            val message = "${product.title} - ${product.price} from Hyderabad added to list"
            shareProduct(context, message)
        }) {
            Text("Add to List / Share")
        }*/
        IconButton(onClick = {
            val message = "${product.title} - ${product.price} from Hyderabad added to list"
            shareProduct(context, message)
        }) {
            Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
        }
    }
}

fun shareProduct(context: Context, message: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, message)
    }
    context.startActivity(Intent.createChooser(intent, "Share via"))
}