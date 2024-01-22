import org.kohsuke.github.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GithubComment {
    private final GitHub github;

    /**
     * 토큰을 사용하여 GitHub에 연결
     * @param token
     */
    public GithubComment(String token) throws IOException {
        this.github = new GitHubBuilder().withOAuthToken(token).build();
    }

    /**
     * 해당 레포지토리의 이슈 댓글을 가져온다.
     * @param repoPath
     * @return Map<String, Integer> - key: 유저 이름, value: 댓글 개수
     * @throws IOException
     */
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
