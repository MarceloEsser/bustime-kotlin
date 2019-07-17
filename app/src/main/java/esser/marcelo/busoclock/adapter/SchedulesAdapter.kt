package esser.marcelo.busoclock.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.row_schedule.view.*
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.model.sogal.SchedulesDTO

/**
 * @author Marcelo Esser
 * @since 12/03/19
 */
class SchedulesAdapter(
    private val context: Context,
    private val schedules: List<SchedulesDTO>
) : RecyclerView.Adapter<SchedulesAdapter.SchedulesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): SchedulesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_schedule, parent, false)
        return SchedulesViewHolder(view)
    }

    override fun getItemCount(): Int {
        if (!schedules.isNullOrEmpty())
            return schedules.size
        return 0
    }

    override fun onBindViewHolder(viewHolder: SchedulesViewHolder, position: Int) {
        val schedule = schedules[position]

        with(viewHolder) {
            tvSchedule.text = schedule.hour
            if (getItemViewType(position) == 0) {
                imgAPD.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (schedules[position].isApd())
            return 1
        return 0
    }


    class SchedulesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgAPD: ImageView = itemView.img_apd
        val tvSchedule: TextView = itemView.row_schedule_tv_bus_hour
    }
}