# java-convenience-store-precourse

- 사용자가 입력한 상품의 가격과 수량을 기반으로 최종 결제 금액을 계산한다.
  - 총구매액은 상품별 가격과 수량을 곱하여 계산하며, 프로모션 및 멤버십 할인 정책을 반영하여 최종 결제 금액을 산출한다. 
- 구매 내역과 산출한 금액 정보를 영수증으로 출력한다.
- 영수증 출력 후 추가 구매를 진행할지 또는 종료할지를 선택할 수 있다.

# 기능 목록

- [x] 상품 목록을 불러온다
- [x] 행사 목록을 불러온다
- [ ] 환영 인사와 함께 편의점 정보를 출력한다
  - 편의점 정보: 상품명, 가격, 프로모션 이름, 재고 
- [ ] 구매할 상품과 수량을 입력 받는다
  - `[상품명-수량],[상품명-수량]`
- [ ] 프로모션 적용 가능 시 추가 여부를 입력 받는다
  - 혜택에 대한 메시지를 함께 출력한다 
  - `Y`, `N`
- [ ] 프로모션 적용 불가능 시 정가 결제 여부를 입력 받는다
  - 정가로 결제해야 하는 상품과 개수를 함께 출력한다  
  - `Y`, `N`
- [ ] 멤버십 할인 적용 여부를 입력 받는다
  - 멤버십 할인 적용 안내 문구를 함께 출력한다  
  - `Y`, `N`
- [ ] 추가 구매 여부를 입력 받는다
  - 추가 구매 여부 안내 문구를 함께 출력한다 
  - `Y`, `N`
- [x] 상품을 입고한다
  - 시스템 예외: 상품의 이름이 `null`인 경우 
  - 시스템 예외: 상품의 이름이 빈 문자열(공백)인 경우
  - 시스템 예외: 가격이 1원 미만인 경우
- [ ] 상품이 존재하는지 조회한다
- [ ] 상품의 재고 수량을 조회한다
- [ ] 결제 가능 여부를 확인한다
- [ ] 결제된 수량만큼 해당 상품의 재고에서 차감한다
- [ ] 상품의 재고 정보를 제공한다
- [ ] 올바른 프로모션인지 검증한다
  - 동일 상품에 여러 프로모션이 적용되지 않는다
  - 시스템 예외: 동일 상품에 두 가지 프로모션이 적용된 경우
- [ ] 프로모션을 적용할 수 있는지 확인한다
  - 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인을 적용한다
  - 프로모션 혜택은 프로모션 재고 내에서만 적용할 수 있다
- [ ] 프로모션 혜택을 받을 수 있음을 알린다
  - 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우
- [ ] 프로모션 혜택을 받을 수 없음을 알린다
  - 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우
- [ ] 프로모션 혜택을 적용한다
  - 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감
  - 프로모션 재고가 부족할 경우에는 일반 재고를 사용
- [ ] 멤버십 할인의 최대 한도를 넘는지 검사한다
- [ ] 멤버십 할인을 적용한다
  - 프로모션 적용 후 남은 금액에 대해 멤버십 할인을 적용
- [ ] 영수증을 생성한다
  - 구매 상품 내역
  - 증정 상품 내역
  - 금액 정보(총구매액, 행사할인, 멤버십할인, 내실돈)
- [ ] 영수증의 구성 요소를 정렬해 출력한다
  - 칸을 맞추어 포맷한다
