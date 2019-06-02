package dev.gotofinal.battleships

import spock.lang.Specification

class PlayerPrinterSpec extends Specification {
    def "should print message"() {
        given: "player printer and output"
            StringBuilder output = new StringBuilder()
            PlayerPrinter playerPrinter = new PlayerPrinter(output)

        when: "simple 'hello world' message is displayed"
            playerPrinter.display("hello world")

        then: "output contains given message as separate line"
            output.toString() == "hello world\n"
    }
}