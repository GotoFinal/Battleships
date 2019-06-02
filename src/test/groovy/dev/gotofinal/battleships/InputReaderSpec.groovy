package dev.gotofinal.battleships

import org.junit.Before
import spock.lang.Specification
import spock.lang.Unroll

class InputReaderSpec extends Specification {
    Readable inputMock = Mock()
    Appendable outputMock = Mock()
    InputReader inputReader = new InputReader(inputMock, new PlayerPrinter(outputMock))

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

    def "should read correct input"() {
        when: "user type valid input 'G3'"
            mockInput("G3")

        and: "input is read by game"
            Vector readInput = inputReader.nextPosition().get()
        then: "coordinates of G3 field are read"
            readInput == new Vector(6, 2)

        and: "user was prompted to type field"
            1 * outputMock.append("Choose field (ex: C7): \n")
    }

    @Unroll
    def "should handle invalid input #input"() {
        when: "user type invalid input"
            mockInput(input)

        and: "input is read by game"
            Optional<Vector> readInput = inputReader.nextPosition()
        then: "game failed to read input"
            readInput.isEmpty()

        and: "user was warned about his mistake"
            1 * outputMock.append("Choose field (ex: C7): \n")
            1 * outputMock.append("Invalid input!\n")

        where:
            input << ['Z9', 'x', 'xx']

    }

    private void mockInput(String input) {
        toInput.add(input)
    }
}