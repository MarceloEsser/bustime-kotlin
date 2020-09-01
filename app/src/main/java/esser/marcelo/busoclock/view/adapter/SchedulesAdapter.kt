package esser.marcelo.busoclock.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.model.schedules.BaseSchedule
import kotlinx.android.synthetic.main.row_schedule.view.*

/**
 * @author Marcelo Esser
 * @author marcelo.v.esser@gmail.com
 *
 * @location Rio Grande do Sul, Brazil
 * @since 31/09/20
 */

class SchedulesAdapter(
    private val context: Context,
    private val schedules: List<BaseSchedule>
) : androidx.recyclerview.widget.RecyclerView.Adapter<SchedulesAdapter.SchedulesViewHolder>() {

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


    class SchedulesViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
        val imgAPD: ImageView = itemView.img_apd
        val tvSchedule: TextView = itemView.row_schedule_tv_bus_hour
    }
}