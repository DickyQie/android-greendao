### Android数据库框架-----GreenDao3的相关使用和版本升级更新
  <p>GreenDAO是一款非要流行的Android平台上的数据库框架，性能优秀，代码简洁；是一个将对象映射到SQLite数据库中的轻量且快速的ORM解决方案。</p> 
<span id="OSC_h3_1"></span>
<h3>GreenDAO 优势</h3> 
<ul> 
 <li>1、一个精简的库</li> 
 <li>2、性能最大化</li> 
 <li>3、内存开销最小化</li> 
 <li>4、易于使用的 APIs</li> 
 <li>5、对 Android 进行高度优化</li> 
</ul> 
<p>首先先添加相关配置文件，依赖库</p> 
<pre><code class="language-java"> compile 'org.greenrobot:greendao:3.+'</code></pre> 
<p>并在库文件顶部添加：</p> 
<pre><code class="language-java">apply plugin: 'org.greenrobot.greendao'</code></pre> 
<p>库文件下定义路径</p> 
<pre><code class="language-java">greendao {
    schemaVersion 1//指定数据库版本号，更新操作会用到;
    daoPackage 'com.zhangqie.greendao.gen'//自动生成的dao的包名，包名默认是entity所在的包；
    targetGenDir 'src/main/java'//生成数据库文件的目录
}</code></pre> 
<p>在build.gradle文件中添加配置</p> 
<pre><code class="language-java">buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.2.3'
        classpath 'org.greenrobot:greendao-gradle-plugin:3.2.1'
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}</code></pre> 
<p>&nbsp;通过以上操作就完成我们的基本配置了；</p> 
<p>创建一个类User</p> 
<pre><code class="language-java">@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;//主键  自增长
    @NotNull   //不许为空
    private String name;
    private String age;
    private String content;
}</code></pre> 
<p>编译运行项目，User实体类会自动编译，生成get、set方法并且会在com.zhangqie.greendao.gen目录下生成三个文件；</p> 
<p>　　　　<img alt="" src="http://images2017.cnblogs.com/blog/1041439/201707/1041439-20170728142846243-640939689.png"></p> 
<p>创建Application类&nbsp; 以下是GreenDao的基本的用法&nbsp;&nbsp; 注释了的&nbsp; <span style="color:#008000"><strong>自定义Helper类包含了版本升级更新</strong></span></p> 
<pre><code class="language-java">public class GreenApplication extends Application {
 private DaoMaster.DevOpenHelper mHelper;
 //private Helper mHelper;
 private SQLiteDatabase db;
 private DaoMaster mDaoMaster;
 private DaoSession mDaoSession;
 public static GreenApplication instances;
 @Override    public void onCreate() {
     super.onCreate();
     instances = this;
     setDatabase();
 }
 public static GreenApplication getInstances(){
     return instances;
 }

/**
 * 设置greenDao
 */
private void setDatabase() {
    // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
    // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。
    // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
    // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
　　 //mHelper = new Helper(new GreenDaoUtils(this));

　　
    mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
    db = mHelper.getWritableDatabase();
    // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。 
    mDaoMaster = new DaoMaster(db); 
    mDaoSession = mDaoMaster.newSession();
}
public DaoSession getDaoSession() {
      return mDaoSession;
}
public SQLiteDatabase getDb() {
      return db;
  }
}</code></pre> 
<p>MainActivity中</p> 
<pre><code class="language-java">UserDao userDao= GreenApplication.getInstances().getDaoSession().getUserDao();
//获取对象来完成  增删改查</code></pre> 
<p><strong><span style="color:#008000">1：添加</span></strong></p> 
<pre><code class="language-java">   User user2=new User();
    user2.setName("白子画");
    user2.setAge("18");
    user2.setContent("我是");long a=userDao.insert(user2);    //添加  a&gt;0添加成功</code></pre> 
<p><strong><span style="color:#008000">2：删除</span></strong></p> 
<pre><code class="language-java">userDao.deleteByKey(1);//id</code></pre> 
<p><strong><span style="color:#008000">3：修改</span></strong></p> 
<pre><code class="language-java"> User user3=new User((long)2,"花千骨","19","你是");//修改Id=2的一列
  userDao.update(user3);</code></pre> 
<p><strong><span style="color:#008000">4：查询</span></strong></p> 
<pre><code class="language-java">List&lt;User&gt; users = userDao.loadAll();
String username = "";
for (int i = 0; i &lt; users.size(); i++) {
 username += users.get(i).getId() + "---"+users.get(i).getName() +"\n";
}
textView.setText(username);</code></pre> 
<p>相关的条件查询，可下载源码查看；运行效果如下：</p> 
<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <img alt="" src="http://images2017.cnblogs.com/blog/1041439/201707/1041439-20170728151520290-828622626.gif"></p> 
<p><span style="color:#808000"><strong>版本升级更新</strong></span></p> 
<p><span style="color:#000000">比如需要在实体类加一个字段 或者 改变字段属性等 就需要版本更新来保存以前的数据了；</span></p> 
<p><span style="color:#000000">我的案例中是加了一个&nbsp; times的字段；</span></p> 
<p><span style="color:#000000">1：需要一个<strong><span style="color:#008080">MigrationHelper.java类 </span></strong><span style="color:#008080"><span style="color:#000000">一位大神写的 直接拿来用即可 </span></span></span></p> 
<p><span style="color:#000000"><span style="color:#008080"><span style="color:#000000">地址：https://stackoverflow.com/questions/13373170/greendao-schema-update-and-data-migration/30334668#30334668</span></span></span></p> 
<p>&nbsp;</p> 
<p>2：接下来就是我们 GreenApplication 类里的 Helper类了</p> 
<pre><code class="language-java">public class Helper extends DaoMaster.OpenHelper{

    private static DaoMaster daoMaster;
    private static DaoSession daoSession;

    public static final String DBNAME = "greendao.db";

    public Helper(Context context){
        super(context,DBNAME,null);
    }


    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        Log.i("version", oldVersion + "---先前和更新之后的版本---" + newVersion);
        if (oldVersion &lt; newVersion) {
            Log.i("version", oldVersion + "---先前和更新之后的版本---" + newVersion);
            MigrationHelper.getInstance().migrate(db, UserDao.class);
            //更改过的实体类(新增的不用加)   更新UserDao文件 可以添加多个  XXDao.class 文件
//             MigrationHelper.getInstance().migrate(db, UserDao.class,XXDao.class);
        }
    }

    /**
     * 取得DaoMaster
     *
     * @param context
     * @return
     */
    public static DaoMaster getDaoMaster(Context context) {
        if (daoMaster == null) {
            DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(context,
                    DBNAME, null);
            daoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @param context
     * @return
     */
    public static DaoSession getDaoSession(Context context) {
        if (daoSession == null) {
            if (daoMaster == null) {
                daoMaster = getDaoMaster(context);
            }
            daoSession = daoMaster.newSession();
        }
        return daoSession;
    }
}</code></pre> 
<p>完成相关操作之后；还要改数据库版本</p> 
<pre><code class="language-java">greendao {
    schemaVersion 2//改版本号为2
    daoPackage 'com.zhangqie.greendao.gen'；
    targetGenDir 'src/main/java'
}</code></pre> 
<p>编译运行，可以去实体类和UserDao查看&nbsp; 增加的字段相关信息自动生成完成；</p> 
<p>当然先前的版本1 中的数据也是存在的，只不过 新添加的字段 的值全部为空而已；</p> 
<p>&nbsp;</p> 
<p>提示：如果感觉复杂可以先看看&nbsp; 别人的这篇博客&nbsp;<a href="http://www.jianshu.com/p/4986100eff90" target="_blank" rel="nofollow">GreenDao3.0简单使用</a> 先学基本使用 再来弄版本升级更新也不迟</p> 
<p><span style="color:#000000">由于代码太多，就不一一贴出来了，直接下载源码即可&nbsp;</span></p> 
