package marcelo.esser.com.bustimek.delegate

import android.content.Context
import android.support.v7.widget.RecyclerView
import marcelo.esser.com.bustimek.model.sogal.LinesDTO

/**
 * @author Marcelo Esser
 * @since 11/03/19
 */
interface SogalLinesAdapterDelegate{
    fun onLineCLickListener(lineCode: String)
}