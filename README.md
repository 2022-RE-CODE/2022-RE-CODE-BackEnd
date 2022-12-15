## 📖 상세 내용

<aside>
🐷 자바 수행평가 결과물로 만든 프로젝트입니다. 개발을 시작한지 얼마 되지 않은 주니어 개발자들은 자신의 코드가 어떤지, 리팩토링은 어떤식으로 해야하는지, 성능은 잘 나오는지 궁금한 경우가 많습니다.

하지만 회사를 다니고 있는것도 아니고, 주변에 물어볼 사람도 없다면 자신의 코드가 어떻고 뭐가 문제이며 어떤식으로 재구성해야 하는지 알기 힘듭니다.

따라서 **주니어 개발자**를 타깃으로 한 코드리뷰 서비스를 만들고자 했습니다. 타깃은 **주니어 개발자**이지만 자신의 코드가 어떤지 궁금하며 **피드백 받고 싶은 모든 개발자들**에게 도움이 되는 서비스입니다.

</aside>

## 💫 담당한 기능

- Spring Security & JWT(Json Web Token)를 활용한 인증/인가 개발
- 인증된 정보를 바탕으로 유저의 정보 저장 및 관리
- 사용자 인증/인가 구현과 토큰 관리
- 비즈니스화를 위한 가상 TossPayments 도입
- S3를 활용한 유저 프로필 이미지 업로드
- 게시글 및 카테고리 기능 개발
- 최근 인기 게시글 끌어올리기

## 💡 개발하면서 만난 문제점과 해결에 대해서

- **결제 시스템 도입에 대해서**
    
    이번 프로젝트를 진행하면서 단순 사이드 프로젝트로만 남는 것이 아닌 만약 미래 사업화 아이템이 된다면 어떻게 비즈니스 가치를 끌어 올릴 수 있으며, 결제 시스템은 어떻게 구축할 것인가에 대해서 고민했습니다. 
    
    우선 지금 당장은 사용하기 쉬운 토스페이먼츠를 도입했지만, 사업자 번호 이슈로 인해서 가상 결제만 되도록 구축했습니다.
    
- **Session과 JWT 무엇을 사용할까**
인증 인가와 관련해서 무엇을 사용할지 고민할 수 밖에 없는 것이 Session과 JWT 같습니다. 인증 체계를 선택하려면 항상 장단점을 비교해서 선택하는게 가장 합리적일 것이라고 생각했습니다.
    
    ![스크린샷 2022-11-23 오후 1.58.47.png](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/eecd83c1-4a81-4df4-8ca9-52987d830dd3/%E1%84%89%E1%85%B3%E1%84%8F%E1%85%B3%E1%84%85%E1%85%B5%E1%86%AB%E1%84%89%E1%85%A3%E1%86%BA_2022-11-23_%E1%84%8B%E1%85%A9%E1%84%92%E1%85%AE_1.58.47.png)
    
    Session과 JWT의 장단점에 대해서는 위와 같았습니다. Session의 단점은 돈을 들이지 않고서야 해결할 수 없는 부분이기에 JWT를 채택해서 안에 들어있는 정보를 최소화 시켜서 단점을 완화하고자 했습니다.
    
- **최근 인기있는 게시글은 어떻게 위로 올릴까**
서비스 특성상 인기있는 게시글은 위로 올려서 더욱 사용자들이 많이 사용할 수 있도록 하는 것이 좋을 것 같았습니다. 하지만 엄청나게 대단한 알고리즘이 적용되어야 할 것 같은 느낌이 들었었습니다.
    
    처음에는 Querydsl의 동적 쿼리를 사용해서 이 기능을 만들지 고민을 했었는데, 자바에서 제공해주는 `ChronoUnit` 이라는 클래스가 있었습니다. 
    
    이 클래스를 활용하면 `ChronoUnit.WEEKS.between(startDateTime, endDateTime)` 와 같은 형식으로 시간 차이를 구할 수 있었고, 다행이도 게시글 데이터베이스에 생성, 수정 시간이 포함되어 있어서 쉽게 적용할 수 있었습니다. 
    

## 💡 느낀 점

- **결제 시스템.. 만들어보고 싶다.**
    
    서비스를 비즈니스화 시킬 때 기획이나 디자인이 중요하듯이, 개발에서 높은 완성도를 요구하는 파트를 결제 시스템이라고 생각합니다. 
    
    결제 시스템 또는 돈과 직결된 파트는 비즈니스 서비스에서는 너무나 중요한 부분이고 세상에 완벽한 코드란 없지만 최소한의 결점을 가지고 있으며 효율에도 집착할 수 있는 파트 같다고 느꼈습니다. 
    
    백엔드 엔지니어로써 한 번쯤 경험 해보면 좋겠다고 생각하는 파트 중 하나입니다. 이번 프로젝트에서 정말 간단하게나마 결제 파트를 찍먹해본 것이 매우 좋은 경험이었습니다.
    
- **프론트엔드 조금은 필요한가?**
    
    이번 프로젝트는 백엔드가 예상 일정보다 몇 주나 빠르게 끝이 났습니다. 하지만 프론트엔드도 그에 맞추어서 몇 주나 빠르게 개발하기는 힘들 것입니다. 
    
    그렇지만 제가 프론트엔드를 개발하는데에 있어 조금이나마 도움이 될 수 있었다면, 추후 디버깅과 리팩터링에 많은 시간을 쏟을 수 있었을 것 같습니다. 이번 기회로 프론트엔드를 많이는 아니어도 어느정도는 알고 있어야겠다고 생각했습니다.
