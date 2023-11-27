package com.example.cupcake.ui

import androidx.lifecycle.ViewModel
import com.example.cupcake.data.OrderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

//Harga untuk per 1 kue
private const val PRICE_PER_CUPCAKE = 2.00
//Tambahan biaya untuk pick up hari yang sama
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00


//membuat fungsi OrderviewModel untuk menyimpan informasi tentang data pemesanan kue
class OrderViewModel : ViewModel() {
    //Menyimpan status terkini dari pesanan dalam variabel private _uiState
    private val _uiState = MutableStateFlow(OrderUiState(pickupOptions = pickupOptions()))
    //Menyimpan ke uiState agar dapat diakses sebagai StateFlow
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    //Fungsi setQuantity untuk menyimpan jumlah pesanan dan update harga
    fun setQuantity(numberCupcakes: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                quantity = numberCupcakes,
                price = calculatePrice(quantity = numberCupcakes)
            )
        }
    }

    //Fungsi setFlavour untuk menyimpan rasa yang dipilih
    fun setFlavor(desiredFlavor: String) {
        _uiState.update { currentState ->
            currentState.copy(flavor = desiredFlavor)
        }
    }

    //fungsi setDate untuk menyimpan data tanggal
    fun setDate(pickupDate: String) {
        _uiState.update { currentState ->
            currentState.copy(
                date = pickupDate,
                price = calculatePrice(pickupDate = pickupDate)
            )
        }
    }

    //fungsi resetOrder digunakan untuk mengembalikan status pesanan ke default
    fun resetOrder() {
        _uiState.value = OrderUiState(pickupOptions = pickupOptions())
    }

    //fungsi calculate digunakan untuk mengitung harga yang harus dibayarkan
    private fun calculatePrice(
        quantity: Int = _uiState.value.quantity,
        pickupDate: String = _uiState.value.date
    ): String {
        var calculatedPrice = quantity * PRICE_PER_CUPCAKE
        // If the user selected the first option (today) for pickup, add the surcharge
        if (pickupOptions()[0] == pickupDate) {
            calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
        }
        val formattedPrice = NumberFormat.getCurrencyInstance().format(calculatedPrice)
        return formattedPrice
    }

    //fungsi pickupOptions digunakan untuk mendapatkan opsi pickup yang dipilih
    private fun pickupOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // add current date and the following 3 dates.
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dateOptions
    }
}
