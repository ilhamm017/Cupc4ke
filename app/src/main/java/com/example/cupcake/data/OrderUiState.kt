package com.example.cupcake.data

data class OrderUiState(
    //Jumlah kuantitan pesanan yang dipilih
    val quantity: Int = 0,
    //Rasa pesanan yang dipilih
    val flavor: String = "",
    //Tanggal Pengambilan
    val date: String = "",
   //Harga yang harus dibayarkan
    val price: String = "",
    //Opsi pengambilan
    val pickupOptions: List<String> = listOf()
)
