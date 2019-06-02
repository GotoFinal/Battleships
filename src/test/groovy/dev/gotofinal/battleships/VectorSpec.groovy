package dev.gotofinal.battleships

import spock.lang.Specification

class VectorSpec extends Specification {
    def "should add two vectors"() {
        given: "two different vectors"
            Vector a = new Vector(1, 3)
            Vector b = new Vector(2, 2)

        when: "vector are added together"
            Vector result = a.add(b)
        then: "new vector coordinates are sum of these two vectors"
            result.x == 3
            result.y == 5
        and: "vectors are unchanged"
            a == new Vector(1, 3)
            b == new Vector(2, 2)
    }
}