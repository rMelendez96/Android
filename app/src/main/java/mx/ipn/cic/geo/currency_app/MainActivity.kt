package mx.ipn.cic.geo.currency_app

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SeekBar
import android.widget.TextView
import com.google.gson.Gson
import mx.ipn.cic.geo.currency_app.databinding.ActivityMainBinding
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Se coloca como comentario, cambio por usar viewbinding.
        // setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewMonedaBase: TextView = findViewById(R.id.textMonedaBase) as TextView

        val seek = findViewById<SeekBar>(R.id.cambioBarra)

        /*val minimo = 1
        val maximo = 50000
        val paso = 0.5

        seek.max = (maximo - minimo) / paso as Int*/

        seek?.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seek: SeekBar, p1: Int, fromUser: Boolean) {

                //val progress = p1 * (seek.width - 2 * seek.thumbOffset) / seek.max
                //val progreso:Float = p1/4 as Float
                viewMonedaBase.text = "MXN: \$ $p1"

                //viewMonedaBase.x = seek.x + progress + seek.thumbOffset / 2
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped
                val entradita = seek.progress
                getCurrencyData(entradita).start()
            }
        })

        // Invocar el método para equivalencia de monedas.
        //getCurrencyData().start()
    }




    private fun getCurrencyData(valorsito: Int): Thread
    {
        return Thread {
            val url = URL("https://open.er-api.com/v6/latest/mxn")
            val connection = url.openConnection() as HttpsURLConnection

            Log.d("Resultado Petición: ", connection.responseCode.toString())

            if(connection.responseCode == 200) {
                val inputSystem = connection.inputStream
                val inputStreamReader = InputStreamReader(inputSystem, "UTF-8")
                val request = Gson().fromJson(inputStreamReader, Request::class.java)
                updateUI(request, valorsito)
                inputStreamReader.close()
                inputSystem.close()
            }
            else {
                binding.textMonedaBase.text = "PROBLEMA EN CONEXIÓN"
            }
        }
    }

    private fun updateUI(request: Request, multiplicadorsito: Int)
    {
        runOnUiThread {
            kotlin.run {
                val fechita: String = request.time_last_update_utc.dropLast(5)
                binding.textActualizacion.text = fechita
                binding.textMonedaColombia.text = String.format("COP: \$ %.2f", request.rates.COP * multiplicadorsito)
                binding.textMonedaBrasil.text = String.format("BRL: R\$ %.2f", request.rates.BRL * multiplicadorsito)
                binding.textMonedaKorea.text = String.format("KRW: ₩ %.2f", request.rates.KRW * multiplicadorsito)
                binding.textMonedaJapon.text = String.format("JPY: ¥ %.2f", request.rates.JPY * multiplicadorsito)
                binding.textMonedaCanada.text = String.format("CAD: C\$ %.2f", request.rates.CAD * multiplicadorsito)
                binding.textMonedaSuiza.text = String.format("CHF: SFr %.2f", request.rates.CHF * multiplicadorsito)
                binding.textMonedaNoruega.text = String.format("NOK: NOK %.2f", request.rates.NOK * multiplicadorsito)
                binding.textMonedaSuecia.text = String.format("SEK: SEK %.2f", request.rates.SEK * multiplicadorsito)
                binding.textMonedaSingapur.text = String.format("SGD: S\$ %.2f", request.rates.SGD * multiplicadorsito)


            }
        }
    }

    private fun cambiaTexto(){

    }
}