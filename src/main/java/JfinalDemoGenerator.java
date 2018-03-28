import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;

/**
 * @createTime: 2018/2/11
 * @author: HingLo
 * @description: 工具
 */
public class JfinalDemoGenerator {
    private static DruidPlugin createDruidPlugin() {
        return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }

    private static DataSource getDataSource() {
        PropKit.use("db.properties");
        DruidPlugin druidPlugin = createDruidPlugin();
        druidPlugin.start();
        return druidPlugin.getDataSource();
    }

    public static void main(String[] args) {
        // base entity 所使用的包名
        String baseModelPackageName = "com.pay.user.bean";
        // base entity 文件保存路径
        String baseModelOutputDir = PathKit.getWebRootPath() + "/src/main/java/com/pay/user/bean";

        // entity 所使用的包名 (MappingKit 默认使用的包名)
        String modelPackageName = "com.pay.user.model";
        // entity 文件保存路径 (MappingKit 与 DataDictionary 文件默认保存路径)
        String modelOutputDir = PathKit.getWebRootPath() + "/src/main/java/com/pay/user/model";

        // 创建生成器
        Generator generator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
        // 设置是否生成链式 setter 方法
        generator.setGenerateChainSetter(true);
        // 设置是否在 Model 中生成 dao 对象
        generator.setGenerateDaoInModel(true);
        // 设置是否生成链式 setter 方法
        generator.setGenerateChainSetter(true);
        // 设置是否生成字典文件
        generator.setGenerateDataDictionary(false);
        // 设置需要被移除的表名前缀用于生成modelName。例如表名 "osc_user"，移除前缀 "osc_"后生成的model名为 "User"而非 OscUser
//        generator.setRemovedTableNamePrefixes("t_");
        // 生成
        generator.generate();
    }
}
