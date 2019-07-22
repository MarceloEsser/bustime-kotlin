package esser.marcelo.busoclock.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.airbnb.lottie.LottieDrawable
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.interfaces.GenericLinesAdapterDelegate
import esser.marcelo.busoclock.model.BaseLine
import kotlinx.android.synthetic.main.row_line.view.*


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
            onItemClicListener(line)
        }
    }

    private fun GenericLinesViewHolder.onItemClicListener(line: BaseLine) {
        this.itemView.setOnClickListener {
            delegate.onItemClickLitener(line)
        }
    }

    class GenericLinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLineName = itemView.tv_line_name
        val tvLineCode = itemView.tv_line_code
    }

}