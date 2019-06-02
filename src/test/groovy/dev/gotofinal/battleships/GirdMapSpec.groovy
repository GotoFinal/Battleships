package dev.gotofinal.battleships

import spock.lang.Specification

class GirdMapSpec extends Specification {

    def "should place ship on the grid on valid position"() {
        given: "empty 10x10 grid"
            GridMap gridMap = new GridMap()

        when: "ship is added too close to the bottom border"
            boolean result = gridMap.tryToPlaceShip(new Vector(2, 2), ShipType.BATTLESHIP, Direction.DOWN)
        then: "grid failed to add the ship"
            result == false

        when: "ship is added too close to the top border"
            result = gridMap.tryToPlaceShip(new Vector(2, 8), ShipType.BATTLESHIP, Direction.UP)
        then: "grid failed to add the ship"
            result == false

        when: "ship is added too close to the right border"
            result = gridMap.tryToPlaceShip(new Vector(8, 2), ShipType.BATTLESHIP, Direction.RIGHT)
        then: "grid failed to add the ship"
            result == false

        when: "ship is added too close to the right border"
            result = gridMap.tryToPlaceShip(new Vector(2, 2), ShipType.BATTLESHIP, Direction.LEFT)
        then: "grid failed to add the ship"
            result == false

        when: "ship is placed outside grid"
            result = gridMap.tryToPlaceShip(new Vector(20, 20), ShipType.BATTLESHIP, Direction.LEFT)
        then: "grid failed to add the ship"
            result == false

        when: "ship is placed on valid position"
            result = gridMap.tryToPlaceShip(new Vector(2, 2), ShipType.BATTLESHIP, Direction.UP)
        then: "ship is added to grid"
            result == true
    }

    def "should not allow ships to collide when adding ships"() {
        given: "10x10 grid with ship on it"
            GridMap gridMap = new GridMap()
            gridMap.tryToPlaceShip(new Vector(2, 2), ShipType.BATTLESHIP, Direction.DOWN)

        when: "ship is added at this same location"
            boolean result = gridMap.tryToPlaceShip(new Vector(2, 2), ShipType.BATTLESHIP, Direction.DOWN)
        then: "grid failed to add the ship"
            result == false

        when: "ship is added at location that is colliding with other ship"
            result = gridMap.tryToPlaceShip(new Vector(2, 0), ShipType.BATTLESHIP, Direction.DOWN)
        then: "grid failed to add the ship"
            result == false

        when: "ship is added at location that is not colliding with other ship"
            result = gridMap.tryToPlaceShip(new Vector(2, 7), ShipType.BATTLESHIP, Direction.RIGHT)
        then: "ship is added to grid"
            result == true
    }

    def "should allow to hit ships on the grid"() {
        given: "10x10 grid with ship on it"
            GridMap gridMap = new GridMap()
            gridMap.tryToPlaceShip(new Vector(2, 2), ShipType.DESTROYER, Direction.UP)

        when: "cell containing this ship is hit"
            HitResult hitResult = gridMap.hit(new Vector(2, 3))
        then: "hit is registered"
            hitResult.hit == true
            hitResult.typeOfShip == ShipType.DESTROYER

        when: "cell not containing this ship is hit"
            hitResult = gridMap.hit(new Vector(3, 3))
        then: "hit is not registered"
            hitResult.hit == false
    }

    def "should inform when ship is destroyed and when it was the last ship on the grid"() {
        given: "10x10 grid with two ships on it"
            GridMap gridMap = new GridMap()
            gridMap.tryToPlaceShip(new Vector(2, 2), ShipType.DESTROYER, Direction.UP)
            gridMap.tryToPlaceShip(new Vector(5, 2), ShipType.DESTROYER, Direction.UP)

        when: "cell containing this ship is hit"
            HitResult hitResult = gridMap.hit(new Vector(2, 2))
        then: "ship is not yet destroyed"
            hitResult.shipDestroyed == false

        when: "another cell containing this ship is hit"
            hitResult = gridMap.hit(new Vector(2, 3))
        then: "ship is not yet destroyed"
            hitResult.shipDestroyed == false

        when: "last cells containing this ship is hit"
            gridMap.hit(new Vector(2, 4))
            hitResult = gridMap.hit(new Vector(2, 5))
        then: "ship is destroyed"
            hitResult.shipDestroyed == true
        and: "there are still other ships"
            hitResult.lastShipDestroyed == false

        when: "another ship is also destroyed"
            (0..2).forEach() { i -> gridMap.hit(new Vector(5, 2 + i)) }
            hitResult = gridMap.hit(new Vector(5, 5))
        then: "ship is destroyed"
            hitResult.shipDestroyed == true
        and: "there are no other ships on the grid"
            hitResult.lastShipDestroyed == true
    }

    def "should not do anything if ship it hit multiple times"() {
        given: "10x10 grid with ship on it"
            GridMap gridMap = new GridMap()
            gridMap.tryToPlaceShip(new Vector(2, 2), ShipType.DESTROYER, Direction.UP)

        when: "cell containing this ship is hit"
            HitResult hitResult = gridMap.hit(new Vector(2, 3))
        then: "hit is registered"
            hitResult.hit == true
            hitResult.typeOfShip == ShipType.DESTROYER

        when: "same cell is hit again"
            hitResult = gridMap.hit(new Vector(2, 3))
        then: "hit is not registered"
            hitResult.hit == false
    }
}