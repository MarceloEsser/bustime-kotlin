package marcelo.esser.com.bustimek.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_line.view.*
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.delegate.SogalLinesAdapterDelegate
import marcelo.esser.com.bustimek.model.sogal.LinesDTO


/**
 * @author Marcelo Esser
 * @since 11/03/19
 */
class SogalLinesAdapter(
    private val context: Context,
    private val linesDTO: List<LinesDTO>,
    private val delegate: SogalLinesAdapterDelegate
) : RecyclerView.Adapter<SogalLinesAdapter.SogalLinesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): SogalLinesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_line, parent, false)
        return SogalLinesViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (linesDTO.isNullOrEmpty())
            return 0
        return linesDTO.size
    }

    override fun onBindViewHolder(viewHolder: SogalLinesViewHolder, position: Int) {
        val line = linesDTO[position]
        with(viewHolder) {
            lineName.text = line.name
            lineCode.text = line.lineCode
        }

        viewHolder.itemView.setOnClickListener {
            delegate.onLineCLickListener(line.lineCode.toString(), line.name.toString())
        }
    }

    class SogalLinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lineName = itemView.tv_line_name
        val lineCode = itemView.tv_line_code
    }
}