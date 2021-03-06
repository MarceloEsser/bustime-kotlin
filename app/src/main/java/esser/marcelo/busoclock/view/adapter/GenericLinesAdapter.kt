package esser.marcelo.busoclock.view.adapter

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.interfaces.GenericLinesAdapterDelegate
import esser.marcelo.busoclock.model.BaseLine
import kotlinx.android.synthetic.main.row_line.view.*

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class GenericLinesAdapter(
    val lines: List<BaseLine>,
    val context: Context,
    val delegate: GenericLinesAdapterDelegate
) : RecyclerView.Adapter<GenericLinesAdapter.GenericLinesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): GenericLinesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_line, parent, false)
        return GenericLinesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lines.size
    }

    override fun onBindViewHolder(viewHolder: GenericLinesViewHolder, position: Int) {
        val line = lines[position]

        with(viewHolder) {
            tvLineName.text = line.name
            tvLineCode.text = line.code
            onItemClickListener(line)
        }
    }

    private fun GenericLinesViewHolder.onItemClickListener(line: BaseLine) {
        this.itemView.setOnClickListener {
            delegate.onItemClickLitener(line)
        }
    }

    class GenericLinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLineName: TextView = itemView.tv_line_name
        val tvLineCode: TextView = itemView.tv_line_code
    }

}