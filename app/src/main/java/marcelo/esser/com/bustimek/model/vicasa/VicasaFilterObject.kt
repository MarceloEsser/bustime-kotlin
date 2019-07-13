package marcelo.esser.com.bustimek.model.vicasa

class VicasaFilterObject(
    val description: String = "",
    val id: String = ""
) {
    override fun toString(): String {
        return description
    }
}