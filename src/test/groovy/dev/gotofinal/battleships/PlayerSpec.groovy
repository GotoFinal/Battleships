package dev.gotofinal.battleships

import org.junit.Before
import spock.lang.Specification

class PlayerSpec extends Specification {

    PlayerPrinter playerPrinterMock = Mock()
    MapRenderer mapRendererMock = Mock()
    Readable inputMock = Mock()
    Player player = new Player(playerPrinterMock, mapRendererMock, new InputReader(inputMock, playerPrinterMock))

    List<String> toInput = new ArrayList<>()

    @Before
    void stubInput() {
        inputMock.read(_) >> { args ->
            if (toInput.empty) return 0
            String input = toInput.remove(0)
            args[0].append(input + "\n")
            return input.length() + 1
        }
    }

    def "should append prefix when displaying message"() {
        when: "message is displayed to player"
            player.displayMessage("Hello!")
        then: "message is displayed with prefix"
            1 * playerPrinterMock.display(">> Hello!")
    }

    def "should ask for input until valid input is provided"() {
        when: "player is asked for input but provides invalid input for few times"
            mockInput("x", "x", "B2")
            Vector input = player.getNextMove()
        then: "asking for input repeats until valid input is provided"
            input == new Vector(1, 1)
            toInput.empty
    }

    private void mockInput(String... input) {
        toInput.addAll(input)
    }
}