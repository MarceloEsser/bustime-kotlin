package esser.marcelo.busoclock.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import kotlinx.android.synthetic.main.row_favorite_line.view.*
import kotlinx.android.synthetic.main.row_favorites.view.tv_line_code
import kotlinx.android.synthetic.main.row_favorites.view.tv_line_name

class FavoriteLinesAdapter(val lines: List<FavoriteLine>, val context: Context) :
    RecyclerView.Adapter<FavoriteLinesAdapter.FavoriteLinesViewHolder>() {

    class FavoriteLinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLineName = itemView.tv_line_name
        val tvLineCode = itemView.tv_line_code
        val tvSogalCompany = itemView.tv_line_sogal_company
        val tvVicasaCompany = itemView.tv_line_vicasa_company
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteLinesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_favorite_line, parent, false)
        return FavoriteLinesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lines.size
    }

    override fun getItemViewType(position: Int): Int {
        if (lines[position].isSogal)
            return 1
        return 0
    }

    override fun onBindViewHolder(holder: FavoriteLinesViewHolder, position: Int) {
        val line = lines[position]

        with(holder) {
            tvLineCode.text = line.code
            tvLineName.text = line.name
            if (line.isSogal) {
                tvSogalCompany.visibility = VISIBLE
                tvVicasaCompany.visibility = GONE
            } else {
                tvSogalCompany.visibility = VISIBLE
                tvVicasaCompany.visibility = GONE
            }
        }
    }
}