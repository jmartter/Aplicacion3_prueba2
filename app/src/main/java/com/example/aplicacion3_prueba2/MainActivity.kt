package com.example.aplicacion3_prueba2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.exameneventosv3.CoordinateConverter
import org.json.JSONObject
import java.io.BufferedReader

class MainActivity : ComponentActivity() {

    private lateinit var jsonObject: JSONObject
    private lateinit var simpleInfoLayout: LinearLayout
    private var isInfoVisible = false  // Variable para controlar si la lista está visible

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia a los elementos de la interfaz
        val buttonSimpleInfo: Button = findViewById(R.id.buttonSimpleInfo)
        simpleInfoLayout = findViewById(R.id.simpleInfoLayout)

        // Lee el archivo JSON desde assets
        val json = assets.open("data.json").bufferedReader().use(BufferedReader::readText)
        jsonObject = JSONObject(json)

        // Configuración del botón
        buttonSimpleInfo.setOnClickListener { toggleSimpleInfo() }
    }

    private fun toggleSimpleInfo() {
        if (isInfoVisible) {
            // Si la lista está visible, la eliminamos
            simpleInfoLayout.removeAllViews()
        } else {
            // Si la lista no está visible, la mostramos
            showSimpleInfo()
        }
        // Alternar el estado de visibilidad
        isInfoVisible = !isInfoVisible
    }

    private fun showSimpleInfo() {
        val featuresArray = jsonObject.getJSONArray("features")
        simpleInfoLayout.removeAllViews()

        for (i in 0 until featuresArray.length()) {
            val feature = featuresArray.getJSONObject(i)
            val properties = feature.getJSONObject("properties")
            val title = properties.getString("title")
            val description = properties.getString("description")
            val coordinates = feature.getJSONObject("geometry").getJSONArray("coordinates")
            val easting = coordinates.getDouble(0) // Asume que las coordenadas están en formato [easting, northing]
            val northing = coordinates.getDouble(1)

            // Convertir las coordenadas UTM a latitud y longitud
            val (latitude, longitude) = CoordinateConverter.utmToLatLon(easting, northing)

            // Extraer el número de teléfono de la descripción
            val phoneRegex = "Teléfono: (\\d+)".toRegex()
            val phoneMatch = phoneRegex.find(description)
            val phoneNumber = phoneMatch?.groups?.get(1)?.value ?: "N/A"

            // Crear un LinearLayout horizontal para la imagen y el texto
            val itemLayout = LinearLayout(this)
            itemLayout.orientation = LinearLayout.HORIZONTAL
            val itemParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            itemParams.setMargins(0, 0, 0, 16)
            itemLayout.layoutParams = itemParams

            // Crear y agregar ImageView
            val imageView = ImageView(this)
            imageView.setImageResource(R.drawable.farmacia)
            val imageParams = LinearLayout.LayoutParams(
                110, // Ancho de la imagen
                110  // Alto de la imagen
            )
            imageParams.setMargins(0, 0, 16, 0)
            imageView.layoutParams = imageParams
            itemLayout.addView(imageView)

            // Crear y agregar TextView
            val textView = TextView(this)
            textView.text = "Title ${i + 1}: $title\nPhone ${i + 1}: $phoneNumber\nCoordinates ${i + 1}: ${latitude}, ${longitude}\n"
            itemLayout.addView(textView)

            // Configurar evento OnClickListener para abrir Google Maps
            itemLayout.setOnClickListener {
                val mapsUrl = "https://www.google.com/maps?q=$latitude,$longitude"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl))
                startActivity(intent)
            }

            // Agregar el itemLayout al simpleInfoLayout
            simpleInfoLayout.addView(itemLayout)
        }
    }
}
