package com.example.spockstudy

import org.assertj.core.internal.bytebuddy.utility.visitor.StackAwareMethodVisitor
import spock.lang.Shared
import spock.lang.Specification

// https://spockframework.org/spock/docs/2.1/spock_primer.html
class SpockStudy_Primer extends Specification {

    /**
     * fields
     *  instance field
     *   - 변수 선언, 초기화시 setup 메서드에서 초기화 하는 것과 동일
     *   - 각 테스트(feature methods)마다 공유되지 않음.
     *
     *  @Shared
     *   - 각 테스트에서 공유할 수 있도록 변수 앞에 붙여 준다.
     *   - 초기화 비용이 비싸거나, 각 테스트 마다 상호 작용이 필요할 경우 사용
     *
     *  static
     *   - 상수를 만들 때 사용, (상수가 아닌 공유 객체의 경우 @Shared 필드가 선호 됨(더 의미가 명확 하게 표현 가능하므로))
     */
    def obj = new ClassUnderSpecification()
    @Shared resource = new VeryExpensiveResource()
    static final PI = 3.141592

    /**
     * fixture methods
     *  setupSpec()
     *   - 첫번째 테스트 실행 전 한번 실행
     *
     *  setup()
     *   - 테스트 마다 시작 전 실행
     *
     *  cleanup()
     *   - 테스트 마다 종료 후 실행
     *
     *  cleanupSpec()
     *   - 마지막 테스트 종료 후 실행
     *
     *  - 테스트가 실행되는 환경을 설정하고 정리
     *  - 테스트는 예상 가능 해야하고, 독립적으로 수행 하는 것이 좋으므로 setup(), cleanup()을 지향
     */
    def setupSpec() {}
    def setup() {}
    def cleanup() {}
    def cleanupSpec() {}

    /**
     * feature methods
     *  blocks
     *   - 테스트의 각각의 개념적 단계를 구현
     *   - given, when, then, expect, cleanup, where이 있다
     *   given
     *    - 테스트에 대한 설정 작업을 수행
     *    - 선택 사항이며 생략 될 수 있다
     *
     *   when, then
     *    - when : 테스트 수행 코드
     *    - then : 결과에 대한 검증 코드 (condition assertion)
     *    - 두 블럭은 항상 같이 사용 된다
     *
     *   expect
     *    - 테스트 수행과 검증을 단일 표현으로 작성하기 위한 블록
     *    - when-then과 의미론적으로 동일하지만 보다 제한적
     *
     *   cleanup
     *    - 테스트 수행 후 자원 해제등 기능을 위한 블록
     *
     *   where
     *    - data-driven 테스트 수행을 위한 블록
     *    - 블록 중 가장 마지막에 위치 하지만, feature method가 수행 될 때 포함 된다
     *
     */
    def "feature method"() {
        given:
        def stack = new Stack()
        def element = "first"

        when:
        stack.push(element)

        then:
        noExceptionThrown()         // 예외 발생하지 않아야 한다
        !stack.empty                // 조건이 true
        stack.size() == 1
        stack.peek() == element
    }

    def "feature method - exception condition"() {
        given:
        def stack = new Stack()

        when:
        stack.pop()

        then:
        def e = thrown(EmptyStackException) // 해당 테스트가 특정 예외 발생
        e.cause == null
    }

    def "feature method - expect"() {
        expect:
        Math.max(1, 2) == 2D
    }

    def "feature method - where"() {
        expect:
        Math.max(a, b) == c

        where: // 두가지 버전의 a, b, c (515, 399)가 수행
        a << [5, 3]
        b << [1, 9]
        c << [5, 9]
    }

    /**
     * helper methods
     *  - feature method 코드에 반복된 코드나 많은 기능으로 크기가 커질 경우 helper method로 분리
     *
     * with, verifyAll
     *  - helper method의 대안으로 사용 가능
     *
     */
    def "helper method"() {
     given:
     def os = "Linux"
     def ram = 4096
     def vendor = "Dell"

     when:
     def pc = new PC(os, ram, vendor)

     then:
     matchesPcConfiguration(pc, os, ram, vendor)
    }

    def matchesPcConfiguration(pc, os, ram,vendor) {
     pc != null
     && pc.os == os
     && pc.ram == ram
     && pc.vendor == vendor
    }

    def "helper method - with"() {
        given:
        def os = "Linux"
        def ram = 4096
        def vendor = "Dell"

        when:
        def pc = new PC(os, ram, vendor)

        then:
        with(pc) {
            os == os
            ram == ram
            vendor == vendor
        }
    }

    def "helper method - verifyAll"() {
        given:
        def os = "Linux"
        def ram = 4096
        def vendor = "Dell"

        when:
        def pc = new PC(os, ram, vendor)

        then:
        verifyAll (pc) {// 보통 검증시 첫번째 실패가 발생한다면 테스트는 바로 종료, verifyAll은 실패가 발생해도 모든 검증을 수행
            os == os + 1
            ram == ram + 2
            vendor == vendor
        }
    }

    /**
     * Documentation
     *  - 블록에 텍스트 설명을 첨부
     */
    def "documentation"() {
        given: "an empty bank account"
        // ...
        and: "set customer"
        // ...

        when: "the account is credited \$10"
        // ...

        then: "the account's balance is \$10"
        // ...
    }
}
