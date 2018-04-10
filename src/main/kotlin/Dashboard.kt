import javafx.application.Application
import javafx.geometry.Orientation
import tornadofx.*


fun main(args: Array<String>) = Application.launch(PriceWarApp::class.java, *args)

class PriceWarApp: App(PriceWarView::class)

class PriceWarView: View() {

    override val root = borderpane {

        title = "Price War"

        center = tableview<Nothing> {

        }

        left = toolbar {
            orientation = Orientation.VERTICAL

            button("+") {
                setOnAction {
                }
            }
        }
    }
}