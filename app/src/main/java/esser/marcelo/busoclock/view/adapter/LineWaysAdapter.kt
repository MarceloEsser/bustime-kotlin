package esser.marcelo.busoclock.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.interfaces.LineMenuDelegate
import esser.marcelo.busoclock.model.LineWay
import esser.marcelo.busoclock.repository.LineHolder

class LineWaysAdapter (
    private val context: Context,
    private val wayList: List<LineWay>,
    val delegate: LineMenuDelegate
) : RecyclerView.Adapter<LineWaysAdapter.LineWaysViewHolder>() {

    class LineWaysViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LineWaysViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_line_way, parent, false)
        return LineWaysViewHolder(view)
    }

    override fun onBindViewHolder(holder: LineWaysViewHolder, position: Int) {
        holder.tvDescription.text = wayList[position].description
        holder.itemView.setOnClickListener {
            LineHolder.lineWay = wayList[position]

            delegate.goToSchedules()
        }
    }

    override fun getItemCount(): Int {
        return wayList.size
    }
}