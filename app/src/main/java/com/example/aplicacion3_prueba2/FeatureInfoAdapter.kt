package com.example.aplicacion3_prueba2

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import org.json.JSONObject

data class FeatureInfo(
    val title: String,
    val description: String,
    val phoneNumber: String,
    val latitude: Double,
    val longitude: Double
)

class FeatureInfoAdapter(private val context: Context) {

    fun createFeatureView(featureInfo: FeatureInfo): View {
        val itemLayout = LinearLayout(context)
        itemLayout.orientation = LinearLayout.HORIZONTAL
        val itemParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        itemParams.setMargins(0, 0, 0, 16)
        itemLayout.layoutParams = itemParams

        // Crear y agregar ImageView
        val imageView = ImageView(context)
        imageView.setImageResource(R.drawable.farmacia)
        val imageParams = LinearLayout.LayoutParams(
            100, // Ancho de la imagen
            100  // Alto de la imagen
        )
        imageParams.setMargins(0, 0, 16, 0)
        imageView.layoutParams = imageParams
        itemLayout.addView(imageView)

        // Crear y agregar TextView
        val textView = TextView(context)
        textView.text = "Title: ${featureInfo.title}\nPhone: ${featureInfo.phoneNumber}\n"
        itemLayout.addView(textView)

        // Configurar coordenadas como tag en el itemLayout
        itemLayout.tag = Pair(featureInfo.latitude, featureInfo.longitude)

        // Configurar evento OnClickListener para abrir Google Maps
        itemLayout.setOnClickListener {
            val (latitude, longitude) = itemLayout.tag as Pair<Double, Double>
            val mapsUrl = "https://www.google.com/maps?q=$latitude,$longitude"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl))
            context.startActivity(intent)
        }

        return itemLayout
    }
}
