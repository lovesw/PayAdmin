import com.jfinal.core.JFinal;

/**
 * @createTime: 2018/1/22
 * @author: HingLo
 * @description: 启动应用程序
 */
public class PayApplication {

    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 8080, "/");
    }
}


