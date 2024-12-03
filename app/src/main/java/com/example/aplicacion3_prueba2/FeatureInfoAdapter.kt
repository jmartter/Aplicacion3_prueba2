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
            140, // Ancho de la imagen
            140  // Alto de la n
        )
        imageParams.setMargins(0, 20, 16, 0)
        imageView.layoutParams = imageParams
        itemLayout.addView(imageView)

        // Crear y agregar TextView
        val textView = TextView(context)
        textView.text = "Title: ${featureInfo.title}\nPhone: ${featureInfo.phoneNumber}\nCoordinates: ${featureInfo.latitude}, ${featureInfo.longitude}\n"
        itemLayout.addView(textView)

        // Configurar evento OnClickListener para abrir Google Maps
        itemLayout.setOnClickListener {
            val mapsUrl = "https://www.google.com/maps?q=${featureInfo.latitude},${featureInfo.longitude}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(mapsUrl))
            context.startActivity(intent)
        }

        return itemLayout
    }
}