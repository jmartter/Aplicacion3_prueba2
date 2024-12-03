package com.example.aplicacion3_prueba2

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.ComponentActivity
import org.json.JSONObject
import java.io.BufferedReader

class MainActivity : ComponentActivity() {

    private lateinit var jsonTextView: TextView
    private lateinit var jsonObject: JSONObject
    private lateinit var simpleInfoLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia a los elementos de la interfaz
        jsonTextView = findViewById(R.id.jsonTextView)
        val buttonSimpleInfo: Button = findViewById(R.id.buttonSimpleInfo)
        simpleInfoLayout = findViewById(R.id.simpleInfoLayout)

        // Lee el archivo JSON desde assets
        val json = assets.open("data.json").bufferedReader().use(BufferedReader::readText)
        jsonObject = JSONObject(json)

        // Configuración del botón
        buttonSimpleInfo.setOnClickListener { showSimpleInfo() }
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
            textView.text = "Title ${i + 1}: $title\nPhone ${i + 1}: $phoneNumber\nCoordinates ${i + 1}: ${coordinates.join(", ")}\n"
            itemLayout.addView(textView)

            // Agregar el itemLayout al simpleInfoLayout
            simpleInfoLayout.addView(itemLayout)
        }
    }
}
