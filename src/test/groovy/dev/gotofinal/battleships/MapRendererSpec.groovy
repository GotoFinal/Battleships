package dev.gotofinal.battleships

import spock.lang.Specification 

class MapRendererSpec extends Specification {

    Appendable output = new StringBuilder()
    MapRenderer mapRenderer = new MapRenderer(output)
    GridMap map = new GridMap()

    def "should render empty maps"() {
        when: "empty map is rendered"
            mapRenderer.render(map)

        then: "only undiscovered fields are visible"
            output.toString() == EMPTY_MAP
    }

    def "should render maps with ships, hits and misses"() {
        given: "state of map in the middle of the game"
            map.tryToPlaceShip(new Vector(2, 2), ShipType.BATTLESHIP, Direction.UP)
            map.hit(new Vector(1, 1))
            map.hit(new Vector(1, 2))
            map.hit(new Vector(2, 4))
            map.hit(new Vector(2, 2))
            map.hit(new Vector(2, 3))

        when: "map is rendered"
            mapRenderer.render(map)

        then: "all actions are visible on the map"
            output.toString() == FULL_MAP
    }

    private static final String EMPTY_MAP = '''
        View of enemy map: 
            A  B  C  D  E  F  G  H  I  J 
        1  [] [] [] [] [] [] [] [] [] [] 
        2  [] [] [] [] [] [] [] [] [] [] 
        3  [] [] [] [] [] [] [] [] [] [] 
        4  [] [] [] [] [] [] [] [] [] [] 
        5  [] [] [] [] [] [] [] [] [] [] 
        6  [] [] [] [] [] [] [] [] [] [] 
        7  [] [] [] [] [] [] [] [] [] [] 
        8  [] [] [] [] [] [] [] [] [] [] 
        9  [] [] [] [] [] [] [] [] [] [] 
        10 [] [] [] [] [] [] [] [] [] [] 
        ## - hit
        [] - undiscovered field
           - (empty) miss
        ==================================
        '''.stripIndent().stripLeading()

    private static final String FULL_MAP = '''
        View of enemy map: 
            A  B  C  D  E  F  G  H  I  J 
        1  [] [] [] [] [] [] [] [] [] [] 
        2  []    [] [] [] [] [] [] [] [] 
        3  []    ## [] [] [] [] [] [] [] 
        4  [] [] ## [] [] [] [] [] [] [] 
        5  [] [] ## [] [] [] [] [] [] [] 
        6  [] [] [] [] [] [] [] [] [] [] 
        7  [] [] [] [] [] [] [] [] [] [] 
        8  [] [] [] [] [] [] [] [] [] [] 
        9  [] [] [] [] [] [] [] [] [] [] 
        10 [] [] [] [] [] [] [] [] [] [] 
        ## - hit
        [] - undiscovered field
           - (empty) miss
        ==================================
        '''.stripIndent().stripLeading()
}