import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GithubToken {

    /**
     * application.properties 파일의 경로
     */
    private static final String propertyPath = "src/main/resources/application.properties";

    private final String token;

    /**
     * application.properties에서 token을 가져온다
     */
    public GithubToken() {
        try {
            FileInputStream input = new FileInputStream(propertyPath);
            Properties properties = new Properties();
            properties.load(input);
            token = properties.getProperty("token");
            if (token == null) throw new RuntimeException("Token not found in application.properties");
        } catch (IOException e) {
            System.out.println("Error reading token from application.properties");
            throw new RuntimeException(e);
        }
    }

    public String getToken() {
        return token;
    }
}
