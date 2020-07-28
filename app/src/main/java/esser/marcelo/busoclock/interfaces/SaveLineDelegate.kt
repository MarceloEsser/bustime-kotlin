package esser.marcelo.busoclock.interfaces

interface SaveLineDelegate {
    fun onError(message: String)
    fun onSuccess()
}