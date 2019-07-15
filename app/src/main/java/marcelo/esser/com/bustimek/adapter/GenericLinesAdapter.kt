package marcelo.esser.com.bustimek.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_line.view.*
import marcelo.esser.com.bustimek.R
import marcelo.esser.com.bustimek.interfaces.GenericLinesAdapterDelegate
import marcelo.esser.com.bustimek.model.LineCodeLineName

class GenericLinesAdapter(
    val lines: List<LineCodeLineName>,
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
            onItemClicListener(line)
        }
    }

    private fun GenericLinesViewHolder.onItemClicListener(line: LineCodeLineName) {
        this.itemView.setOnClickListener {
            delegate.onItemClickLitener(lineCode = line.code, lineName = line.name)
        }
    }

    class GenericLinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLineName = itemView.tv_line_name
        val tvLineCode = itemView.tv_line_code
    }

}