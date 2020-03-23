package esser.marcelo.busoclock.adapter.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import esser.marcelo.busoclock.R
import esser.marcelo.busoclock.model.LineWay
import kotlinx.android.synthetic.main.spinner_default_row.view.*

class SpinnerDefaultAdapter(context: Context, val ways: List<LineWay>,  val resource: Int =  R.layout.spinner_default_row) :
    ArrayAdapter<LineWay>(context, resource, ways) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, parent)
    }

    private fun createView(position: Int, parent: ViewGroup) : View {
        val view  = LayoutInflater.from(context).inflate(resource, parent, false)
        view.tvDescription.text = ways[position].description

        return view
    }
}