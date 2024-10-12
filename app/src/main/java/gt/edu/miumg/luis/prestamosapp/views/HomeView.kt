package gt.edu.miumg.luis.prestamosapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import gt.edu.miumg.luis.prestamosapp.components.MainButton
import gt.edu.miumg.luis.prestamosapp.components.MainTextField
import gt.edu.miumg.luis.prestamosapp.components.ShowInfoCards
import gt.edu.miumg.luis.prestamosapp.components.SpaceH
import java.math.BigDecimal

@Composable
fun ContentHomeView(
    paddingValues: PaddingValues
){
    var montoPrestamo by remember {
        mutableStateOf("")
    }
    var cantCuotas by remember {
        mutableStateOf("")
    }
    var tasa by remember {
        mutableStateOf("")
    }
    var montoInteres by remember {
        mutableStateOf(0.0)
    }
    var montoCuota by remember {
        mutableStateOf(0.0)
    }

    var showAlert by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(10.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShowInfoCards(
            titleInteres = "Interes",
            montoInteres = montoInteres,
            titleMonto = "Monto",
            monto = montoCuota
        )
        MainTextField(
            value = montoPrestamo,
            onValueChange = {montoPrestamo = it},
            label = "Monto del prestamo"
        )
        SpaceH()
        MainTextField(
            value = cantCuotas,
            onValueChange = {cantCuotas = it},
            label = "Cuotas"
        )
        SpaceH(10.dp)
        MainTextField(
            value = tasa,
            onValueChange = {tasa = it},
            label = "Tasa"
        )
        SpaceH(20.dp)
        MainButton(
            text = "Calcular",
            onClick = { /*TODO*/ }
        ) {
            if (montoPrestamo != "" && cantCuotas != ""){
                montoInteres = calcularTotal(montoInteres.toDouble(), cantCuotas.toInt(), tasa.toDouble())
                montoCuota = calcularCuota(montoPrestamo.toDouble(), cantCuotas.toInt(), tasa.toDouble())
            } else {
                showAlert = true
            }
        }
    }
}

fun calcularTotal(monto: Double, cuotas: Int, tasa: Double): Double {
    val res = cuotas * calcularCuota(monto, cuotas, tasa)
    return BigDecimal(res).setScale(2, BigDecimal.ROUND_UP).toDouble()
}

fun calcularCuota(monto: Double, cuotas: Int, tasa: Double): Double {
    val tasaInteresMensual = tasa / 12 / 100
    val cuota = monto * tasaInteresMensual * Math.pow(1 + tasaInteresMensual, cuotas.toDouble()) /
      (Math.pow(1 + tasaInteresMensual, cuotas.toDouble()) - 1)
    val cuotaRedondeada = BigDecimal(cuota).setScale(2, BigDecimal.ROUND_UP).toDouble()
    return cuotaRedondeada
    return cuota
}
