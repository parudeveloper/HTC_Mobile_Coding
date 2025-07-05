package com.htcmobilecoding.userinterface

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.htcmobilecoding.data.Resource
import com.htcmobilecoding.model.ProductItem
import com.htcmobilecoding.ui.theme.HTCMobileCodingTheme
import com.htcmobilecoding.viewmodel.ProductsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    productsViewModel: ProductsViewModel = hiltViewModel(), onProductClick: (Int) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("Product List") }, colors = TopAppBarDefaults.smallTopAppBarColors(
                   /* containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.background*/
                    containerColor = Color(0xFF00897B),
                    titleContentColor = Color.White


                )
            )
        }, containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        val products by productsViewModel.products.collectAsStateWithLifecycle()

        Column {
            when (products) {
                is Resource.Error -> {
                    Text("Error message : ${(products as Resource.Error).message}")
                }

                is Resource.Loading -> {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.testTag("loading_indicator")
                        )
                    }
                }

                is Resource.Success -> {
                    /* Text("Success")
                    Text("Data : ${(products as Resource.Success<List<ProductItem>>).data?.size}")*/

                    LazyColumn(contentPadding = innerPadding) {
                        items(products.data ?: emptyList()) { productItem ->
                            ProductItemCard(
                                product = productItem,
                                onItemClick = {
                                    /*Toast.makeText(
                                        context,
                                        productItem.id.toString(),
                                        Toast.LENGTH_LONG
                                    ).show()*/
                                    onProductClick(productItem.id)

                                }
                            )
                        }
                    }

                }
            }
        }

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductItemCard(
    product: ProductItem, onItemClick: (ProductItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(product) }
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Log.d("ImageURL", product.imageUrl)

        GlideImage(
            model = product.imageUrl,
            contentDescription = product.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
        )

       /* AsyncImage(
            model = product.imageUrl,
            contentDescription = product.title,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )*/


        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = product.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = product.summary,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
    HTCMobileCodingTheme {
        ProductItemCard(
            product = ProductItem(
                1,
                "https://www.meijer.com/content/dam/meijer/product/7503/01/8074/35/7503018074351_0_A1C1_0200.jpg",
                "This is Product Summary",
                "This is Product"
            ),
            onItemClick = {}
        )
    }
}