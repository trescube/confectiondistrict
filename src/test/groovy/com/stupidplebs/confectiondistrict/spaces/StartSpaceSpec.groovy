package com.stupidplebs.confectiondistrict.spaces

import com.stupidplebs.confectiondistrict.spaces.StartSpace;

import spock.lang.Specification

class StartSpaceSpec extends Specification {
    def "instance should be unequal to null"() {
        given:
        def space = new StartSpace()

        and:
        def other = null

        expect:
        !space.equals(other)

    }

    def "instance should be equal to itself"() {
        given:
        def space = new StartSpace()

        and:
        def other = space

        expect:
        space.equals(other)

    }

    def "instance should be unequal to a non-Player object"() {
        given:
        def space = new StartSpace()

        and:
        def other = "this is not a StartSpace object"

        expect:
        !space.equals(other)

    }

    def "2 instances of StartSpace should be equal and have equal hashCodes"() {
        given:
        def space = new StartSpace()

        and:
        def other = new StartSpace()

        expect:
        space.equals(other)
        space.hashCode() == other.hashCode()

    }

    def "toString should output class name"() {
        given:
        def space = new StartSpace()

        expect:
        space.toString() == "StartSpace{}"

    }

}
