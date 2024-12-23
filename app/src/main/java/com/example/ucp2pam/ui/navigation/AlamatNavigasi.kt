package com.example.ucp2pam.ui.navigation

interface AlamatNavigasi{
    val route :String
}

object DestinasiPertama : AlamatNavigasi{
    override val route ="Pertama"
}

object DestinasiHomeBrg : AlamatNavigasi{
    override val route = "Home-Barang"
}

object DestinasiHomeSpl : AlamatNavigasi{
    override val route = "Home-Suplier"
}
object DestinasiDetailBarang : AlamatNavigasi{
    override val route ="detail"
    const val ID = "id-detail"
    val  routesWithArg = "$route/{$ID}"
}

object DestinasiUpdateBrg : AlamatNavigasi{
    override val route = "update"
    const val ID = "id-update"
    val routesWithArg = "$route/{$ID}"
}