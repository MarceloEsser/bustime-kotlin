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
    private val itinerariesDTO: List<ItinerariesDTO>?
) : androidx.recyclerview.widget.RecyclerView.Adapter<ItinerariesAdapter.ItinerariesViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ItinerariesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_itineraries, viewGroup, false)
        return ItinerariesViewHolder(view)
    }

    override fun getItemCount(): Int = itinerariesDTO!!.size

    override fun onBindViewHolder(viewHolder: ItinerariesViewHolder, p1: Int) {
        val itinerarie = itinerariesDTO!![p1]

        with(viewHolder) {
            tvItinerarie.text = itinerarie.street
            tvItinerarieCity.text = itinerarie.city
        }
    }

    class ItinerariesViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val tvItinerarie = itemView.findViewById<TextView>(R.id.tv_itinerarie_street)
        val tvItinerarieCity = itemView.findViewById<TextView>(R.id.tv_itinerarie_city)
    }
}