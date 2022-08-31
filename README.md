## 补充

### 注意

- appium java-client和selenium-java需要排除依赖

```xml
<!-- selenium-->
      <dependency>
          <groupId>org.seleniumhq.selenium</groupId>
          <artifactId>selenium-java</artifactId>
          <version>${selenium.version}</version>
          <exclusions>
              <exclusion>
                  <groupId>net.bytebuddy</groupId>
                  <artifactId>byte-buddy</artifactId>
              </exclusion>
          </exclusions>
          <scope>compile</scope>
      </dependency>
      <!-- appium -->
      <dependency>
          <groupId>io.appium</groupId>
          <artifactId>java-client</artifactId>
          <version>${appium.version}</version>
          <exclusions>
              <exclusion>
                  <groupId>net.bytebuddy</groupId>
                  <artifactId>byte-buddy</artifactId>
              </exclusion>
          </exclusions>
          <scope>compile</scope>
      </dependency>
```

### 测试

- 打包 mvn clean install -U -DskipTests
- 切换到sample目录 cd E:\cebaSource\aitest2022\junit-pass-tests-agent\sample
- 执行测试 mvn test -Dtest=geoand.junit.sample.*

- 命令执行结果：无失败，无错误
![img.png](img.png)

- IDEA中执行结果
![img_1.png](img_1.png)

### 可用命令

- mvn test -Dtest=com.hogwarts.demo.strs.StrTest
- mvn test -Dtest=com.hogwarts.demo.nums.IntTest
- mvn test -Dtest=com.hogwarts.demo.nums.DoubleTest
- mvn test -Dtest=geoand.junit.sample.SampleTest

执行失败也不报错

- mvn test -DfailIfNoTests=false -Dtest=geoand.junit.sample.SampleTest

### 其他mvn test命令

指定具体的某个环境的某个包下的所用例
mvn test -Denv=环境名 -Dtest=包的路径.*
比如：mvn test -Denv=xxx -Dtest=com.xx.test.testCases.openApi.xxx.*

指定任何一个不同包下相同子包的所用测试用例
mvn test -Dtest=包的路径.*.相同子包的路径.*
比如：mvn test -Dtest=com.xxx.test.testcase.*.openApi.*
指定testcase下的所有的包中包含openApi子包下的所有用例

运行某个包下的所有用例（包括子包中的用例）
mvn test -Dtest=包的路径.*.*
比如：mvn test -Dtest=com.xx.test.testCases.openApi.xxx.*.*

指定具体某个类的测试用例
mvn test -Dtest=包的路径.类名
比如：mvn test -Dtest=com.xx.test.testCases.openApi.CreateExpireTest
指定类名为CreateExpireTest的测试用例

指定模糊匹配包含某个类名的测试用例
mvn test -Dtest=包的路径.*类名
比如：mvn test -Dtest=com.xx.test.testCases.openApi.*Test
包含Test结尾的类的所有测试用例

指定所有包下的某个类名为xxx的测试用例
mvn test -Dtest=xxx
比如：mvn test -Dtest=CreateExpireTest
指定所有包下的名字为CreateExpireTest类的测试用例

指定所有包下的某个类名xxx开头的测试用例
mvn test -Dtest=xxx*
比如：mvn test -Dtest=CreateExpire*
指定所有包下的名字为CreateExpireTest类开头的测试用例

指定部分测试用例的测试用例
mvn test -Dtest=test1,test2
mvn tset -Dtest=CreateExpireTest,com.xxx.xxx.*,*Test

指定某个测试用例名的测试用例
mvn test -Dtest=xxx#xx*
mvn test -Dtest=com.xx.test.testCases.openApi.*Test#test*
指定以Test结尾的类名下的方法名中包括test的所用的用例

指定某个测试组里面的测试用例
mvn test -Dgroups=xxx -Dtest=xxx
mvn test -Dgroups=test1 -Dtest=com.xx.test.testCases.openApi.*Test2#test3*
执行方法名标记的test1组里面的所有的test2结尾的类中方法名test3开始的用例



mvn test -Dtest=geoand.junit.sample.*