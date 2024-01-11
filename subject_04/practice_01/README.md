# 과제 04: 제어문

- [과제 04: 제어문](#과제-04-제어문)
  - [실습 01: live-study 대시 보드를 만드는 코드를 작성하세요.](#실습-01-live-study-대시-보드를-만드는-코드를-작성하세요)
    - [Github API](#github-api)
      - [Personal Access Token](#personal-access-token)
      - [Oauth 인증](#oauth-인증)
      - [이슈 및 댓글 처리](#이슈-및-댓글-처리)

## 실습 01: live-study 대시 보드를 만드는 코드를 작성하세요.

간단하게 모든 이슈를 순회하면서 각 사용자 별로 댓글 수를 세어서 출력하는 코드를 작성했습니다.

### Github API

#### Personal Access Token

Github API를 사용하기 위해서는 먼저 Personal Access Token을 발급받아야 한다.
발급받은 토큰을 설정 파일에 저장해두고 사용하고, 노출되지 않도록 주의해야 한다.
> [Personal Access Token 발급 방법](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token)

#### Oauth 인증

다음으로 발급받은 토큰을 통해 Oauth 인증을 거친 후 `Github` 객체를 생성한다.

```java
public class GithubComment {

    private final GitHub github;

    public GithubComment(String token) throws IOException {
        this.github = new GitHubBuilder().withOAuthToken(token).build();
    }
}
```

#### 이슈 및 댓글 처리

`Github` 객체의 `getRepository()` 메서드를 통해 원하는 저장소를 가져올 수 있다.

해당 저장소에서 `getIssues()` 메서드를 통해 이슈 목록을 가져오고,

이슈 목록에서 `getComments()` 메서드를 통해 댓글 목록을 가져온다.

```java
public class GithubComment {
    // ...

    public Map<String, Integer> getIssueComments(String repoPath) throws IOException {
        GHRepository repository = github.getRepository(repoPath);
        List<GHIssue> issues = repository.getIssues(GHIssueState.ALL);
        for (GHIssue issue : issues) {
            List<GHIssueComment> comments = issue.getComments();
            // ...
        }
        // ...
    }
}
```

`GHIssueComment` 객체의 `getUser()` 메서드를 통해 댓글 작성자에 대한 정보를 담은 `GHUser` 객체를 가져온다.

`GHUser` 객체의 `getName()` 메서드를 통해 댓글 작성자의 이름을 가져올 수 있다.
만약 댓글 작성자 이름이 `null`인 경우 getLogin() 메서드를 통해 로그인 아이디를 가져온다.

댓글 작성자가 이미 존재하는 경우 댓글 수를 1 증가시키고, 존재하지 않는 경우 새로 추가한다.

```java
public class GithubComment {
    // ...

    public Map<String, Integer> getIssueComments(String repoPath) throws IOException {
        Map<String, Integer> participation = new HashMap<>();

        GHRepository repository = github.getRepository(repoPath);
        List<GHIssue> issues = repository.getIssues(GHIssueState.ALL);
        for (GHIssue issue : issues) {
            List<GHIssueComment> comments = issue.getComments();
            for (GHIssueComment comment : comments) {
                String user = comment.getUser().getName();
                if (user == null) user = comment.getUser().getLogin();
                if (participation.containsKey(user)) {
                    participation.put(user, participation.get(user) + 1);
                } else {
                    participation.put(user, 1);
                }
            }
        }
        return participation;
    }
}
```