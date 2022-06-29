package com.example.spockstudy

import spock.lang.Ignore
import spock.lang.PendingFeature
import spock.lang.PendingFeatureIf
import spock.lang.Requires
import spock.lang.Specification

// https://spockframework.org/spock/docs/2.1/extensions.html
class SpockStudy_Extensions extends Specification {

    /**
     * Built-In Extensions
     *
     *  @Ignore
     *   - 테스트를 실행 되지 않도록 한다
     *   - 클래스에 붙이면 해당 클래스 전체 테스트가 실행 되지 않는다
     *   - @Ignore(inherited=true) 지정시 서브 테스트 클래스의 테스트도 무시된다
     *
     *  @IgnoreRest
     *   - 클래스가 @Ignore일 때 특정 테스트에 추가하면 해당 테스트는 수행된다
     *
     *  @IgnoreIf
     *   - 조건에 만족한 경우 테스트가 무시된다
     *   - properties
     *      - sys : System Properties
     *      - env : Environment Variables
     *      - os : OS 정보
     *      - jvm : JVM 정보
     *      - data : 테스트에 지정된 data
     *
     *  @Requires
     *   - 조건에 만족한 경우만 테스트 수행
     *
     *  @PendingFeature
     *   - 기능이 아직 구현이 되지 않아 오류 보고에 포함되지 않아야 함을 나타 냄
     *   - @PendingFeatureIf : 조건 추가
     *
     * */

    @Ignore("임시로 테스트 수행 금지")
    def "feature" () {
        when:
        print("hello")

        then:
        noExceptionThrown()
    }

    @Requires({ os.windows })
    def "It will only run on Windows"() {

    }

    @PendingFeatureIf({ os.windows })
    def "It will not support on Windows"() {

    }




}
