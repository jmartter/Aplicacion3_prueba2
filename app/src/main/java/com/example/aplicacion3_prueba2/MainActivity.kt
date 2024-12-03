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
        val buttonInfo: Button = findViewById(R.id.buttonInfo)
        val buttonBack: Button = findViewById(R.id.buttonBack)

        // Lee el archivo JSON desde assets
        val json = assets.open("data.json").bufferedReader().use(BufferedReader::readText)
        jsonObject = JSONObject(json)

        // Configuración de los botones
        buttonInfo.setOnClickListener { showInfo() }
        buttonBack.setOnClickListener { finish() }
    }

    private fun showInfo() {
        val featuresArray = jsonObject.getJSONArray("features")
        val info = StringBuilder()

        for (i in 0 until featuresArray.length()) {
            val feature = featuresArray.getJSONObject(i)
            val properties = feature.getJSONObject("properties")
            val title = properties.getString("title")
            val description = properties.getString("description")
            val link = properties.getString("link")
            val coordinates = feature.getJSONObject("geometry").getJSONArray("coordinates")

            info.append("Title ${i + 1}: $title\n")
            info.append("Description ${i + 1}: $description\n")
            info.append("Link ${i + 1}: $link\n")
            info.append("Coordinates ${i + 1}: ${coordinates.join(", ")}\n\n")
        }
        jsonTextView.text = info.toString()
    }
}