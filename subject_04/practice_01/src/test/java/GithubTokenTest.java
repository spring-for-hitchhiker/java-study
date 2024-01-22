import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GithubTokenTest {

    @Test
    void 토큰_테스트() {
        // given
        GithubToken githubToken = new GithubToken();

        // expected
        assertNotNull(githubToken.getToken());
    }
}