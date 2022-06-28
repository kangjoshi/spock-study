package com.example.spockstudy

import spock.lang.Specification

// https://spockframework.org/spock/docs/2.1/interaction_based_testing.html
class SpockStudy_InteractionBasedTesting extends Specification {

    /**
     * Mock
     *  - 실제 객체를 구현하기 어려운 경우 만드는 가짜 객체
     * */

    Publisher publisher = new Publisher()
    def subscriber = Mock(Subscriber)
    Subscriber subscriber2 = Mock()

    def setup() {
        publisher.subscribers << subscriber
        publisher.subscribers << subscriber2
    }

    def "should send message to all subscribers"() {
        when:
        publisher.send("hello")

        then:
        1 * subscriber.receive("hello")     // 한번만 호출 되어야 한다. 만약 충족하지 못하면 TooManyInvocationsError, TooFewInvocationsError 발생
        1 * subscriber2.receive("hello")
        /*
        횟수 검증
        0 * subscriber.receive("hello")      // 호출되지 않아야 함
        (1..3) * subscriber.receive("hello") // 1-3번 사이 호출
        (1.._) * subscriber.receive("hello") // 1번 이상 호출
        (_..3) * subscriber.receive("hello") // 3번 이하 호출
        _ * subscriber.receive("hello")      // 0 - N번 사이 호출

        파라미터 검증
        1 * subscriber.receive("hello")        // hello와 동일해야 한다
        1 * subscriber.receive(!"hello")       // hello와 달라야 한다
        1 * subscriber.receive()               // 빈 값이여야 한다
        1 * subscriber.receive(_)              // 모든 단일 값 (null 포함)
        1 * subscriber.receive(*_)             // 모든 리스트 형태 값 (빈 배열 포함)
        1 * subscriber.receive(_ as String)    // String 형태의 모든 값
        1 * subscriber.receive({               // 객체의 여러 값을 검증 시
            verifyAll(it, Person) {
                firstname == 'kang'
                age == 33
            }
        })
        */

    }

    /**
     * Stub
     *  - 작성자가 의도한 대로 응답하도록 지정
     * */
    def "should send message to all subscribers2"() {
        when:
        publisher.send("hello")

        then:
        subscriber.receive("hello") >> "OK" // 응답값을 OK로 지정
        /*
        subscriber.receive(_) >>> ["ok", "error", "error", "ok"] // 매번 다른 값 리턴 할 수 있도록 배열로 지정
        subscriber.receive(_) >> { String message -> message.size() > 3 ? "ok" : "fail" } // 계산이 필요하다면 closure 영역에서 계산
        subscriber.receive(_) >> { throw new InternalError("ouch") } // 예외 값 지정
        */
    }

    /**
     *  mocking and Stubbing 결합
     *   - 1 * subscriber.receive("message1") >> "ok"       mocking과 stubbing을 분리하지 않고 한번에 해야 한다
     *   - 1 * subscriber.receive("message2") >> "fail"
     * */
}
