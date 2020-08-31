package esser.marcelo.busoclock.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.interfaces.IFavoriteLineAdapterDelegate
import esser.marcelo.busoclock.model.favorite.FavoriteLine
import kotlinx.android.synthetic.main.row_favorite_line.view.*

class FavoriteLinesAdapter(
    val lines: List<FavoriteLine>,
    val context: Context,
    val delegate: IFavoriteLineAdapterDelegate
) :
    RecyclerView.Adapter<FavoriteLinesAdapter.FavoriteLinesViewHolder>() {

    class FavoriteLinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLineName: TextView = itemView.tv_line_name
        val tvLineCode: TextView = itemView.tv_line_code
        val tvSogalCompany: TextView = itemView.tv_line_sogal_company
        val tvVicasaCompany: TextView = itemView.tv_line_vicasa_company
        val tvLineWay: TextView = itemView.tv_line_way
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
            tvLineWay.text = line.way
            if (line.isSogal) {
                tvSogalCompany.visibility = VISIBLE
                tvVicasaCompany.visibility = GONE
            } else {
                tvSogalCompany.visibility = GONE
                tvVicasaCompany.visibility = VISIBLE
            }
            itemView.setOnClickListener {
                delegate.onLineClicked(line)
            }
        }
    }
}