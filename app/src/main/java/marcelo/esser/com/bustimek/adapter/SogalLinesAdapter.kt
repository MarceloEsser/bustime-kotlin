package marcelo.esser.com.bustimek.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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

    override fun onBindViewHolder(p0: SogalLinesViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class SogalLinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lineName: TextView = itemView.findViewById(R.id.tv_line_name)

    }
}