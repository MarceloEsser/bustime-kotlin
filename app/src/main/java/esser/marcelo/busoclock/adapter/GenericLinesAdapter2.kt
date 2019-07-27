package esser.marcelo.busoclock.adapter

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import esser.marcelo.busoclock.R
import kotlinx.android.synthetic.main.row_line.view.*
import kotlinx.android.synthetic.main.row_section.view.*

class GenericLinesAdapter2(
        var lines: List<Base>,
        var context: Context,
        var layoutInflater: LayoutInflater = LayoutInflater.from(context)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClickListener: ((Line) -> Unit)? = null
    var onFavoriteClickListener: ((Line) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view = layoutInflater.inflate(viewType, viewGroup, false)

        return when (viewType) {
            R.layout.row_line -> GenericLinesViewHolder(view)
            else -> SectionViewHolder(view)
        }
    }

    override fun getItemCount(): Int = 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {

            is SectionViewHolder -> {
                val section = lines[holder.adapterPosition] as Section
                holder.tvNameSection.text = section.name
            }

            is GenericLinesViewHolder -> {
                val line = lines[holder.adapterPosition] as Line
                holder.tvLineName.text = line.name
                holder.tvLineCode.text = line.code

                val progress = if(line.isFavorite) 60f else 0f
                holder.lottieFavorite.progress = progress

                holder.lottieFavorite.setOnClickListener {


                    val animate = if(line.isFavorite) ValueAnimator.ofFloat(60f,  0f) else ValueAnimator.ofFloat(0f,  60f)

                    animate.addUpdateListener {
                        holder.lottieFavorite.progress = it.animatedValue as Float
                    }

                    animate.duration = 250
                    animate.start()

                    onFavoriteClickListener?.invoke(line)
                }

                holder.itemView.setOnClickListener {
                    onItemClickListener?.invoke(line)
                }
            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return when (lines[position]) {
            is Line -> R.layout.row_line
            else -> R.layout.row_section
        }

    }

    abstract class Base

    class Section(
            var name: String = ""
    ) : Base()

    class Line(
            var name: String = "",
            var code: String = "",
            var isFavorite: Boolean = false
    ) : Base()

    class GenericLinesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLineName = itemView.tv_line_name
        val tvLineCode = itemView.tv_line_code
        val lottieFavorite = itemView.lottie_favorite__line
    }

    class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNameSection = itemView.tv_name_row_section
    }

}