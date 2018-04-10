import tornadofx.*
import java.io.File
import java.time.LocalDate

val inputs = File("price_war.csv").readLines()
        .asSequence()
        .drop(1)
        .map { it.split(",") }
        .map {
            PriceEntry(it[0].toInt(),
                    it[1].toInt(),
                    it[2].toInt(),
                    it[3].toInt(),
                    it[4].toInt(),
                    LocalDate.parse(it[5]),
                    it[6]
            )
        }.toList()

val productIds = inputs.asSequence().map { it.product }.toSet()
val companyIds = inputs.asSequence().map { it.company }.toSet()

data class PriceEntry(val id: Int,
                      val company: Int,
                      val product: Int,
                      val price: Int,
                      val discount: Int,
                      val effDate: LocalDate,
                      val type: String)

data class BayesModel(val effDate: LocalDate) {

    val relevantRecords = inputs
            .reversed()
            .asSequence()
            .filter { it.effDate <= effDate }
            .let {
                val effFromDt = it.dropWhile { it.type != "INITIAL" }.first().effDate
                it.filter { it.effDate >= effFromDt }.toList()
            }
}
object Model {

    var lastAddedIndex = -1

    val selectedItems = listOf<PriceEntry>().observable().also {
        it.addAll(inputs.filter { it.effDate == LocalDate.of(2018,1,1) } )
    }

    fun addEntry() {
        if (lastAddedIndex == inputs.size)
            lastAddedIndex = -1

        selectedItems += inputs[lastAddedIndex++]
    }
}
