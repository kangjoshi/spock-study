package com.example.spockstudy

import spock.lang.Ignore
import spock.lang.PendingFeatureIf
import spock.lang.Requires
import spock.lang.Retry
import spock.lang.Specification
import spock.lang.TempDir
import spock.lang.Timeout

import java.util.concurrent.TimeUnit

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
     *  @Stepwise
     *   - 클래스 붙이면, 테스트가 선언된 순서대로 수행된다
     *   - 해당 어노테이션이 붙은 클래스에만 적용, 상속을 통한 적용은 되지 않음
     *
     *  @Timeout
     *   - 주어진 시간을 초과하면 실패 발생
     *   - 기본 : 초, unit 속성으로 조정 가능
     *
     *  @Retry
     *   - 실패 가능성이 있는 원격 테스트를 위해 사용
     *   - count (기본값 3), exceptions, condition등 설정 가능
     *
     *  @TempDir
     *   - 임시 폴더 생성, 테스트 종료 후에는 삭제 된다
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

    @Retry(count = 5)
    @Timeout(5)
    def "fail if run for more then five seconds"() {

    }

    @Retry(exceptions = IOException)
    @Timeout(value = 10, unit = TimeUnit.MILLISECONDS)
    def "fail if run for more then ten milliseconds"() {

    }

    @TempDir
    File tempDir




}
