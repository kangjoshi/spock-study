package com.example.spockstudy

import spock.lang.Rollup
import spock.lang.Specification

// https://spockframework.org/spock/docs/2.1/data_driven_testing.html
class SpockStudy_DataDrivenTesting extends Specification {

    /**
     * Data Driven Tesing
     *  - 같은 테스트를 여러개의 input으로 여러번 수행
     *  - 반복 중 실패가 발생해도 나머지 반복은 수행된다
     *  - 각 반복은 독립적이다, 즉 매 반복시 setup과 cleanup 메소드가 호출된다
     *  - 공유 객체를 사용하려면 @Shared 객체를 사용
     *      - 참고로 다른 테스트도 공유 된다, 현재 동일한 테스트 사이에만 공유 할 수 있는 방법은 없다
     *
     *  data table
     *   - 테이블 형식으로 구성
     *
     *  data pipes
     *   - << 연산자 이용
     *
     *  data variable assignment
     *   - = 연산자 이용
     */

    def "maximum of two numbers"(int a, int b, int c) {
        expect:
        Math.max(a, b) == c

        where:      // where절에 data table 셋팅, 각 row마다 테스트는 한번씩 수행 된다
        a | b | c   // header : 변수 정의
        1 | 3 | 3   // rows : 값 정의
        7 | 4 | 7
        0 | 0 | 0
    }

    def "maximum of two numbers2"(int a, int b, int c) {
        expect:
        Math.max(a, b) == c

        where:
        a | _
        1 | _
        7 | _
        0 | _
        _____
        b | c
        3 | 3
        4 | 7
        0 | 0
    }

    def "maximum of two numbers3"() {
        expect:
        Math.max(a, b) == c
        Math.max(d, e) == f

        where:
        a | b | c
        1 | 3 | 3
        7 | 4 | 7
        0 | 0 | 0
        __________
        d ; e ; f
        1 ; 3 ; 3
        7 ; 4 ; 7
        0 ; 0 ; 0
    }

    def "maximum of two numbers4"() {
        expect:
        Math.max(a, b) == c

        where:
        a << [1, 7, 0]
        b << [3, 4, 0]
        c << [3, 7, 0]
    }

    def "maximum of two numbers5"() {
        expect:
        Math.max(a, b) == c

        where:
        a = 1
        b = 3
        c = a > b ? a : b
    }

    /**
     * @Rollup
     *  - 모든 반복에 대해서 보고하지 않고 집계만 한다
     *
     * @Unroll
     *  - @Rollup의 반대 개념
     *  - 기본 설정
     * */
    @Rollup
    def "rollup test"() {
        expect:
        Math.max(a, b) == c

        where:
        a | b | c
        1 | 3 | 3
        7 | 4 | 7
        0 | 0 | 0
    }

    // @Unroll
    def "maximum of #a and #b is #c"() {
        expect:
        Math.max(a, b) == c

        where:
        a << [1, 7, 0]
        b << [3, 4, 0]
        c << [3, 7, 0]
    }
}
