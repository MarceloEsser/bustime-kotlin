package marcelo.esser.com.bustimek.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_line.view.*
import marcelo.esser.com.bustimek.R

class VicasaLinesAdapter(
    val linesList: List<String>,
    val context: Context
) : RecyclerView.Adapter<VicasaLinesAdapter.VicasaLinesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): VicasaLinesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_line, parent, false)
        return VicasaLinesViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (linesList.isNullOrEmpty())
            return 0
        return linesList.size
    }

    override fun onBindViewHolder(viewHolder: VicasaLinesViewHolder, position: Int) {
        val line = linesList[position]
        with(viewHolder) {
            lineName.text = line
            lineCode.visibility = View.GONE
        }
    }

    class VicasaLinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lineName = itemView.tv_line_name
        val lineCode = itemView.tv_line_code
    }
}