package esser.marcelo.busoclock.vvm.favorite.schedules

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import esser.marcelo.busoclock.R

class FavoriteSchedulesAcitivty : AppCompatActivity() {

    private val viewModel:FavoriteSchedulesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedules)
        shcedule_activity_tv_line_name.text = LineDAO.name
    }
}