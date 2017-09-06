# hipchat_to_jandi_migration_tool

웹훅을 이용한 힙챗의 데이터를 잔디로 이전하는 툴 입니다.

* 환경 : JAVA, Gradle 이용
* 사용법
** 힙챗의 관리자로 로그인해서 채팅방의 내용을 Export 받는다.
** 잔디에 이전하고 싶은 대화방을 만든다.
** 웹훅으로 받을 수 있는 잔디 커넥션을 이용해 데이터를 이전한다.
** 커스텀하게 꾸미고 싶으면 Module 파일을 참고할것.
** 쓰레드를 이용해 여러개의 대화방을 400개씩 업로드 하게끔 해둠(차단 방지)
** 400개의 글이 올라갈때마다 각 스레드에서 별도로 10분씩 휴식함.(차단 방지)

* 도움주신 잔디의 이진화 매니저님께 감사인사드립니다. :D
