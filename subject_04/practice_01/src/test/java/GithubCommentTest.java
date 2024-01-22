import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GithubCommentTest {
    private static final GithubToken githubToken = new GithubToken();

    @Test
    void 이슈_댓글_테스트() throws IOException {
        // given
        String repoPath = "spring-for-hitchhiker/java-study";
        GithubComment githubComment = new GithubComment(githubToken.getToken());

        // when
        Map<String, Integer> issueComments = githubComment.getIssueComments(repoPath);

        // then
        assertNotNull(issueComments);
        issueComments.forEach((key, value) -> System.out.println(key + " : " + value + "개"));
    }

}