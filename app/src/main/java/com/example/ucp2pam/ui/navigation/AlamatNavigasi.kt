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

object DestinasiInsertBrg: AlamatNavigasi{
    override val route = "Insert-Brg"
}

object DestinasiDetailBarang : AlamatNavigasi{
    override val route ="detail"
    const val Nama = "Nama"
    val  routesWithArg = "$route/{$Nama}"
}

object DestinasiUpdateBrg : AlamatNavigasi{
    override val route = "update"
    const val Nama = "nama"
    val routesWithArg = "$route/{$Nama}"
}