package com.example.ucp2pam.ui.navigation



import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2pam.ui.view.Barang.DestinasiInsertBrg
import com.example.ucp2pam.ui.view.Barang.DetailBrgView
import com.example.ucp2pam.ui.view.Barang.HomeBrgView
import com.example.ucp2pam.ui.view.Barang.InsertBrgView
import com.example.ucp2pam.ui.view.Barang.UpdateBrgView
import com.example.ucp2pam.ui.view.PilihanHomeView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiPertama.route // Mulai dari halaman pilihan home
    ) {
        // Halaman Pilihan Home
        composable(
            route = DestinasiPertama.route
        ) {
            PilihanHomeView(
                onAddBrgClick = {
                    navController.navigate(DestinasiInsertBrg.route)
                },
                onListBrgClick = {
                    navController.navigate(DestinasiHomeBrg.route)
                }
            )
        }
        //HomeBrgView
        composable(
            route = DestinasiHomeBrg.route
        ) {
            HomeBrgView(
                onDetailClik = {id ->
                    navController.navigate("${DestinasiDetailBarang.route}/$id")
                    println(
                        "Pengelolahalaman: id = $id"
                    )
                },
                modifier = modifier

            )
        }
        //InsertBrgView
        composable(
            route = DestinasiInsertBrg.route
        ) {
            InsertBrgView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = Modifier
            )
        }
        //detailview
        composable(
            DestinasiDetailBarang.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailBarang.ID){
                    type = NavType.StringType
                }
            )
        ){
            val id = it.arguments?.getString(DestinasiDetailBarang.ID)
            id?.let { id ->
                DetailBrgView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateBrg.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            DestinasiUpdateBrg.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateBrg.ID){
                    type = NavType.StringType
                }
            )
        ){
            UpdateBrgView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier
            )
        }
    }
}
