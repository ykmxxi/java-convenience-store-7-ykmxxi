# 3주차 공통 피드백


# 📌 3주 차 미션의 목표

- 클래스 분리
- 단위 테스트 시작
- 클래스의 역할
- 테스트 검증 포인트

# 📌 함수(메서드) 라인에 대한 기준 적용

- 15라인 제한, 초과하면 역할을 더 명확하게 나누고, 가독성과 유지보수성을 높일 수 있는 신호로 인식
- main() 함수도 동일하게 적용, 공백 라인도 한 라인으로 간주

# 📌 예외 상황에 대한 고민

- 정상적인 상황보다 예외 상황을 모두 고려하여 프로그래밍하는 것이 훨씬 어렵다
- 예외 상황을 처리하는 습관
- 예외를 미리 고려, 프로그램이 비정상적으로 종료되거나 잘못된 결과를 내지 않도록 만든다
    - 어떤 예외를 복구할 수 있는지
    - 복구 가능한 예외는 잡아서 다시 정상 흐름으로 돌아가게 만들고
    - 복구 불가능하다고 생각되는 예외는 어떻게 처리할 것인가? 그냥 종료 시키고 끝인가?

# 📌 왜 비즈니스 로직과 UI 로직은 분리해야 할까?

- 단일 책임 원칙
- 비즈니스 로직: 데이터 처리, 도메인 규칙을 담당
    - toString()은 로깅과 디버거를 위한 상태 표현
- UI 로직: 화면에 데이터를 표시하거나, 입력을 받는 역할(InputView, OutputView)
    - 사용할 데이터를 비즈니스 로직에서 getter로 받아와 View 계층에게 전달

# 📌 연관성이 있는 상수는 enum

- 연관성이 있는 상수는 enum 활용
    - 연관성이 있다고 무조건 묶는 방법이 좋을까?
- 상수를 그룹화하고, 각 상수에 관련된 속성과 행동을 부여
    - 객체는 상태와 행위를 갖는다. enum도 클래스 중 하나로 객체를 표현하는 메커니즘 중 하나, 고유한 상태(고정된 상태값)를 가지고 상태에 변화를 주는 행위가 아닌, 상태를 이용해 값을 계산(데이터 처리)하는 행위를 갖을 수 있다
    - 도메인 규칙(고정된 규칙 값)들을 enum? 현재는 반대

# 📌 final 키워드를 사용해 값의 변경을 막는다: 불변 객체

- 값의 변경을 방지, 특히 도메인 상태는 한 번 설정되면 외부에서 변경이 되지 않도록 막아야 한다
    - 변경이란 상태의 변화가 아니다. 변경은 상태를 새로운 상태, 즉 새로운 객체 참조를 할당하는 것(참조 재할당)
- 왜 최근 언어들이 기본적으로 불변성을 추구할까?
- 불변 객체의 장점을 나는 설명할 수 있나?
- 모든 객체가 불변이여야 하나? 불변 객체의 장점과 가변 객체를 갖을 수 밖에 없다 생각되는 상황이 오면?

# 📌 상태 접근 제한: 캡슐화

- 객체의 상태 접근을 제한
- 인스턴스 변수의 접근 제어자 private? 얻는 이점은?
    - 외부에서 객체의 상태에 직접 접근하는 것을 막는다
    - **!! 객체의 상태는 외부에서 통제되지 않고, 객체 내에서만 관리(제어, 통제) !!**
    - 도메인의 상태가 외부에게 통제당하면? 서비스의 핵심 가치를 외부에게 맡기는 꼴

# 📌 객체가 객체답다는 것은?

[getter를 사용하는 대신 객체에 메시지를 보내자](https://tecoble.techcourse.co.kr/post/2020-04-28-ask-instead-of-getter/)

- Tell, Don’t Ask: 객체에게 묻지 말고 일하도록 시켜라
    - 강한 어조이지만, 즉 협력을 요청하라는 뜻이다
    - 객체가 자신의 데이터를 스스로 처리하도록 메시지를 던지게, 즉 송신자가 요청하면 필요에 따라 스스로 상태를 변화한다
- 객체는 스스로 자신의 상태 변화를 처리할 수 있는 신기한 존재다
    - 현실 세계의 로또: 인간이 당첨 번호와 비교해 번호를 비교한다, 즉 데이터를 가져와 직접 처리(프로시저)
    - 객체 세계의 로또: 스스로 비교할 수 있고, 스스로 번호를 관리한다

# 📌 필드(인스턴스 변수)의 수를 줄이기 위해 노력한다

- 필드가 많으면 복잡도가 증가하고, 관리가 어렵다 -> 버그 발생 가능성이 증가
- 해당 필드가 객체가 꼭 상태로 가져야할 값인가? 의심하자
- **즉, 개념의 상태인지 행위인지 생각하자**
    - 상태를 설정하고 다른 행위에서나, 별다른 협력 요청이 들어오지 않고 getter로만 반환한다면 상태인지 의심한다

# 📌 예외(실패) 케이스도 테스트한다

- 예외 상황에 대한 테스트 중요도, 결함이 자주 발생하는 경계값이나 잘못된 입력에 대한 테스트

# 📌 테스트 코드도 코드

- 테스트 코드도 리팩터링의 대상, 프로덕션과 함께 리팩터링한다
- 반복적으로 수행되는 부분은 중복을 제거해 유지보수성을 높이고 가독성을 향상시킨다
    - 올바른 테스트 픽스처 생성 방법은
    - setup & 메서드 활용

# 📌 테스트를 위한 코드는 구현 코드에서 분리

- 테스트를 위해 프로덕션 코드를 변경하는 것은 좋지 않은 습관, 코드를 작성하다 테스트를 위해 접근 제어자를 변경하거나, 테스트를 위한 메서드를 작성하면 혼란을 가중시킨다
- 테스트가 구현에 종속되게, 구현이 테스트에 종속되면 안된다. 테스트가 구현에 의존하게 만들자

# 📌 단위 테스트가 어려운 코드를 단위 테스트하기

- 어떻게 협력자가 많은 presentation 계층의 코드를 테스트?
- 테스트 대역 사용?
    - 테스트 하기 어려운(외부 API), 즉 제어와 통제가 불가능한 요소를 분리하고, 인터페이스를 도입해 테스트 때 대역을 사용하는 방법이 최선같음
- 테스트하기 어려운 의존성을 외부에서 주입하거나 분리하여 테스트 가능한 상태로 만드는 것
    - 의존성 주입(DI)
- 테스트 하기 어려운 코드(서비스 계층, 프리젠테이션 계층)는 의존성 주입을 사용하면 가능
    - Lotto 미션에서 놓친 부분, Service가 클라이언트에게 의존성을 받았다면 서비스 계층도 테스트가 가능했음. 이 부분을 놓쳤다

# 📌 private 함수를 테스트 하고 싶다면 분리 고려

- 중요한 역할을 수행하는 것 같다? 즉, 테스트 하고 싶다?는 생각이 들면 개념을 행위로 착각한 것이 아닌지 의심한다

# 📌 TDD

- 테스트 주도 개발: 테스트를 먼저 작성하는 것만이 TDD 인가?
    - 주도란?
    - 테스트가 주도하는 개발이란 정확히 무엇인가?
- TDD의 궁극적인 목표: 문제를 작은 단위로 정의하고, 피드백을 자주 받으면서 해답을 찾아간다
    - 개념을 설정하고, 개념의 상태와 행위가 올바른 작은 단위인가 테스트를 통해 피드백을 받는다
- TDD는 왜 필요할까?
- 먼저 요구사항 분석, 가능한 범주 내에서(완벽 X) 분석을 하고 시작하는 것은 똑같음
- 개념별로 요구사항을 분리
- 먼저 통과를 시키고(테스트 파라미터에 맞는 값만 반환하도록 일단 작성) 리팩터링
- 테스트 하기 어려운 요소, 즉 제어와 통제가 불가능한 것이 존재하면?