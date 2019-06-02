package dev.gotofinal.battleships

import spock.lang.Specification

import static dev.gotofinal.battleships.ShipType.BATTLESHIP
import static dev.gotofinal.battleships.ShipType.DESTROYER

class GridRandomizerSpec extends Specification {

    private GridRandomizer gridRandomizer = new GridRandomizer(
            [
                    (DESTROYER) : 2,
                    (BATTLESHIP): 1
            ],
            new Random(1)
    )

    def "should randomly place all requested ships"() {
        given: "empty 10x10 grid"
            GridMap gridMap = new GridMap()

        when: "ships are randomly placed on map"
            gridRandomizer.placeShips(gridMap)
        then: "all ships are added to map"
            Set<Ship> ships = (0..9).collect { x ->
                (0..9).collect { y ->
                    gridMap.getCell(new Vector(x, y))
                }.findAll { it.hasShip() }.collect { it.ship }
            }.flatten().toSet()
            ships.size() == 3
            ships.findAll { it.shipType == DESTROYER }.size() == 2
            ships.findAll { it.shipType == BATTLESHIP }.size() == 1
    }
}