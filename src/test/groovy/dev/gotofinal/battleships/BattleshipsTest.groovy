package dev.gotofinal.battleships

import spock.lang.Specification

// end to end test based on recorded input and output of game
class BattleshipsTest extends Specification {

    StringBuilder output = new StringBuilder()
    Appendable renderOutputMock = Mock()
    Readable inputMock = Mock()

    PlayerPrinter playerPrinter = new PlayerPrinter(output)
    MapRenderer mapRenderer = new MapRenderer(renderOutputMock)
    InputReader inputReader = new InputReader(inputMock, playerPrinter)
    Player player = new Player(playerPrinter, mapRenderer, inputReader)

    void stubInput(List<String> inputToStub) {
        List<String> recordedInput = new ArrayList<>(inputToStub)
        inputMock.read(_) >> { args ->
            String input = recordedInput.remove(0)
            args[0].append(input + "\n")
            return input.length() + 1
        }
    }

    def "should be possible to play example pre-recorded game"() {
        given: "game instance using pre-recorded settings"
            Battleships game = Battleships.initializeGame(new Random(1))
        when: "game is played using pre-recorded input"
            stubInput(['x', 'e5', 'd5', 'c5', 'e5', 'D6',
                       'D7', 'D8', 'E4', 'e3', 'e6',
                       'r7', 'e7', 'F6', 'f7', 'f8', 'F9'])
            game.play(player)
        then: "output is matching recorded output of example game"
            output.toString().readLines() == getRecordedOutput
        and: "map is rendered each move"
            15 * renderOutputMock.append(_)
    }

    static List<String> getRecordedOutput = [
            '>> Welcome to battleships!',
            *invalid(),
            *hit(ShipType.BATTLESHIP),
            *hit(ShipType.DESTROYER),
            *miss(),
            *miss(),
            *hit(ShipType.DESTROYER),
            *hit(ShipType.DESTROYER),
            *destroy(ShipType.DESTROYER),
            *hit(ShipType.BATTLESHIP),
            *hit(ShipType.BATTLESHIP),
            *hit(ShipType.BATTLESHIP),
            *invalid(),
            *destroy(ShipType.BATTLESHIP),
            *hit(ShipType.DESTROYER),
            *hit(ShipType.DESTROYER),
            *hit(ShipType.DESTROYER),
            *destroyLast(ShipType.DESTROYER)
    ]

    static String prompt = 'Choose field (ex: C7): '

    static String[] invalid() {
        return [prompt, 'Invalid input!']
    }

    static String[] miss() {
        return [prompt, '>> You missed!']
    }

    static String[] hit(ShipType type) {
        return [prompt, ">> You hit ${type.name} ship!"]
    }

    static String[] destroy(ShipType type) {
        return [prompt, ">> You hit and destroyed ${type.name} ship!"]
    }

    static String[] destroyLast(ShipType type) {
        return [prompt, ">> You hit and destroyed (${type.name}) last ship of your opponent and won this game!"]
    }
}