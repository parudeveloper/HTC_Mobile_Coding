package com.htcmobilecoding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.htcmobilecoding.ui.theme.HTCMobileCodingTheme
import com.htcmobilecoding.userinterface.ProductDetailScreen
import com.htcmobilecoding.userinterface.ProductListScreen
import com.htcmobilecoding.utils.ROUTE_PRODUCT_DETAIL
import com.htcmobilecoding.utils.ROUTE_PRODUCT_LIST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HTCMobileCodingTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = ROUTE_PRODUCT_LIST) {
                    composable(ROUTE_PRODUCT_LIST) {
                        ProductListScreen(
                            onProductClick = { productId ->
                                navController.navigate("product_detail/$productId")
                            }
                        )
                    }

                    composable(
                        ROUTE_PRODUCT_DETAIL,
                        arguments = listOf(navArgument("productId") { type = NavType.IntType })
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getInt("productId") ?: -1
                        ProductDetailScreen(productId,navController)
                    }
                }

            }
        }
    }
}
