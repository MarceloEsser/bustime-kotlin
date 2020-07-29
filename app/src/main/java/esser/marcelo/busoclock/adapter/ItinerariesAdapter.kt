package esser.marcelo.busoclock.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.model.sogal.ItinerariesDTO

class ItinerariesAdapter(
    private val context: Context,
    private val itinerariesDTO: List<ItinerariesDTO>
) : RecyclerView.Adapter<ItinerariesAdapter.ItinerariesViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ItinerariesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_itineraries, viewGroup, false)
        return ItinerariesViewHolder(view)
    }

    override fun getItemCount(): Int = itinerariesDTO.size

    override fun onBindViewHolder(viewHolder: ItinerariesViewHolder, position: Int) {
        val itinerary = itinerariesDTO[position]

        with(viewHolder) {
            tvItinerary.text = itinerary.street
            tvItineraryCity.text = itinerary.city
        }
    }

    class ItinerariesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvItinerary: TextView = itemView.findViewById(R.id.tv_itinerarie_street)
        val tvItineraryCity: TextView = itemView.findViewById<TextView>(R.id.tv_itinerarie_city)
    }
}