package com.example.aplicacion3_prueba2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import org.json.JSONObject
import java.io.BufferedReader

class MainActivity : ComponentActivity() {

    private lateinit var jsonTextView: TextView
    private lateinit var jsonObject: JSONObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia a los elementos de la interfaz
        jsonTextView = findViewById(R.id.jsonTextView)
        val buttonTitle: Button = findViewById(R.id.buttonTitle)
        val buttonCoordinates: Button = findViewById(R.id.buttonCoordinates)
        val buttonLink: Button = findViewById(R.id.buttonLink)
        val buttonDescription: Button = findViewById(R.id.buttonDescription)
        val buttonTelefono: Button = findViewById(R.id.buttonTelefono)

        // Lee el archivo JSON desde assets
        val json = assets.open("data.json").bufferedReader().use(BufferedReader::readText)
        jsonObject = JSONObject(json)

        // Configuración de los botones
        buttonTitle.setOnClickListener { showSpecificField("title") }
        buttonCoordinates.setOnClickListener { showSpecificField("coordinates") }
        buttonLink.setOnClickListener { showSpecificField("link") }
        buttonDescription.setOnClickListener { showSpecificField("description") }
        buttonTelefono.setOnClickListener { showTelefonos() }
    }

    private fun showSpecificField(field: String) {
        val featuresArray = jsonObject.getJSONArray("features")
        val result = StringBuilder()

        for (i in 0 until featuresArray.length()) {
            val feature = featuresArray.getJSONObject(i)
            when (field) {
                "title" -> {
                    val title = feature.getJSONObject("properties").getString("title")
                    result.append("Title ${i + 1}: $title\n")
                }
                "coordinates" -> {
                    val coordinates = feature.getJSONObject("geometry").getJSONArray("coordinates")
                    result.append("Coordinates ${i + 1}: ${coordinates.join(", ")}\n")
                }
                "link" -> {
                    val link = feature.getJSONObject("properties").getString("link")
                    result.append("Link ${i + 1}: $link\n")
                }
                "description" -> {
                    val description = feature.getJSONObject("properties").getString("description")
                    result.append("Description ${i + 1}: $description\n")
                }
            }
        }
        jsonTextView.text = result.toString()
    }

    private fun showTelefonos() {
        val featuresArray = jsonObject.getJSONArray("features")
        val telefonos = StringBuilder()

        for (i in 0 until featuresArray.length()) {
            val feature = featuresArray.getJSONObject(i)
            val description = feature.getJSONObject("properties").getString("description")
            val telefono = Regex("Teléfono: ([0-9]+)").find(description)?.groupValues?.get(1) ?: "No encontrado"
            telefonos.append("Teléfono ${i + 1}: $telefono\n")
        }
        jsonTextView.text = telefonos.toString()
    }
}