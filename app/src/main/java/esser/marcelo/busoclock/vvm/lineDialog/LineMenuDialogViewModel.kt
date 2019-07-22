package esser.marcelo.busoclock.vvm.lineDialog

class LineMenuDialogViewModel {


    fun getWaysList(): ArrayList<String> {
        val waysList: ArrayList<String> = ArrayList()
        waysList.add("Centro circular")
        waysList.add("Bairro circular")
        waysList.add("Centro Bairro")
        waysList.add("Bairro Centro")

        return waysList
    }
}