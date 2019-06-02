package dev.gotofinal.battleships

import spock.lang.Specification

class ShipSpec extends Specification {

    def "should destroy ship after hitting it enough times"() {
        given: "ship of Destroyer type with 4 hit points"
            Ship ship = new Ship(new Vector(0, 0), Direction.UP, ShipType.DESTROYER)

        when: "ship is hit 3 times"
            (1..3).forEach() { ship.hit() }
        then: "ship is still not destroyed"
            !ship.isDestroyed()
        
        when: "shit is hit again"
            ship.hit()
        then: "ship is destroyed"
            ship.isDestroyed()
    }
}