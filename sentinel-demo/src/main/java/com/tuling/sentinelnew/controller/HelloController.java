package com.tuling.sentinelnew.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.tuling.sentinelnew.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
@Slf4j
public class HelloController {

    private static final String RESOURCE_NAME = "hello";
    private static final String USER_RESOURCE_NAME = "user";
    private static final String DEGRADE_RESOURCE_NAME = "degrade";


    // 进行sentinel流控
    @RequestMapping(value = "/hello")
    public String hello() {

        Entry entry = null;
        try {
            // 1.sentinel针对资源进行限制的
            entry = SphU.entry(RESOURCE_NAME);
            // 被保护的业务逻辑
            String str = "hello world";
            log.info("====="+str+"=====");
            return str;
        } catch (BlockException e1) {
            // 资源访问阻止，被限流或被降级
            //进行相应的处理操作
            log.info("block!");
            return "被流控了！";
        } catch (Exception ex) {
            // 若需要配置降级规则，需要通过这种方式记录业务异常
            Tracer.traceEntry(ex, entry);
        } finally {
            if (entry != null) {
                entry.exit();
            }
        }
        return null;
    }


    /**
     * 定义规则
     *
     * spring 的初始化方法
     */
    @PostConstruct  //
    private static void initFlowRules(){

        // 流控规则
        List<FlowRule> rules = new ArrayList<FlowRule>();

        // 流控
        FlowRule rule = new FlowRule();
        // 为哪个资源进行流控
        rule.setResource(RESOURCE_NAME);
        // 设置流控规则 QPS
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置受保护的资源阈值
        // Set limit QPS to 20.
        rule.setCount(1);
        rules.add(rule);



        // 通过@SentinelResource来定义资源并配置降级和流控的处理方法
        FlowRule rule2 = new FlowRule();
        //设置受保护的资源
        rule2.setResource(USER_RESOURCE_NAME);
        // 设置流控规则 QPS
        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // 设置受保护的资源阈值
        // Set limit QPS to 20.
        rule2.setCount(1);

        rules.add(rule2);

        // 加载配置好的规则
        FlowRuleManager.loadRules(rules);
    }

    @PostConstruct  // 初始化
    public void initDegradeRule(){
        /*降级规则 异常*/
        List<DegradeRule> degradeRules = new ArrayList<DegradeRule>();
        DegradeRule degradeRule = new DegradeRule();
        degradeRule.setResource(DEGRADE_RESOURCE_NAME);
        // 设置规则侧率： 异常数
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 触发熔断异常数 ： 2
        degradeRule.setCount(2);
        // 触发熔断最小请求数:2
        degradeRule.setMinRequestAmount(2);
        // 统计时长：  单位：ms    1分钟
        degradeRule.setStatIntervalMs(60*1000); //  时间太短不好测

        // 一分钟内： 执行了2次  出现了2次异常  就会触发熔断

        // 熔断持续时长 ： 单位 秒
        // 一旦触发了熔断， 再次请求对应的接口就会直接调用  降级方法。
        // 10秒过了后——半开状态： 恢复接口请求调用， 如果第一次请求就异常， 再次熔断，不会根据设置的条件进行判定
        degradeRule.setTimeWindow(10);


        degradeRules.add(degradeRule);
        DegradeRuleManager.loadRules(degradeRules);

        /*
        慢调用比率--DEGRADE_GRADE_RT
        degradeRule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        degradeRule.setCount(100);
        degradeRule.setTimeWindow(10);
        //请求总数小于minRequestAmount时不做熔断处理
        degradeRule.setMinRequestAmount(2);
        // 在这个时间段内2次请求
        degradeRule.setStatIntervalMs(60*1000*60);   //  时间太短不好测
        // 慢请求率：慢请求数/总请求数> SlowRatioThreshold ，
        // 这里要设置小于1   因为慢请求数/总请求数 永远不会大于1
        degradeRule.setSlowRatioThreshold(0.9);*/

    }


    /**
     * @SentinelResource 改善接口中资源定义和被流控降级后的处理方法
     * 怎么使用： 1.添加依赖<artifactId>sentinel-annotation-aspectj</artifactId>
     *          2.配置bean——SentinelResourceAspect
     *   value  定义资源
     *   blockHandler 设置 流控降级后的处理方法（默认该方法必须声明在同一个类）
     *      如果不想在同一个类中 blockHandlerClass 但是方法必须是static
     *   fallback 当接口出现了异常，就可以交给fallback指定的方法进行处理
     *      如果不想在同一个类中 fallbackClass 但是方法必须是static
     *
     *   blockHandler 如果和fallback同时指定了，则blockHandler优先级更高
     *      exceptionsToIgnore 排除哪些异常不处理
     * @param id
     * @return
     */
    @RequestMapping("/user")
    @SentinelResource(value = USER_RESOURCE_NAME,  fallback = "fallbackHandleForGetUser",
            /*exceptionsToIgnore = {ArithmeticException.class},*/
            /*blockHandlerClass = User.class,*/ blockHandler = "blockHandlerForGetUser")
    public User getUser(String id) {
        int a=1/0;
        return new User("xushu");
    }

    public User fallbackHandleForGetUser(String id,Throwable e) {
        e.printStackTrace();
        return new User("异常处理");
    }

    /**
     * 注意：
     *  1. 一定要public
     *  2. 返回值一定要和源方法保证一致， 包含源方法的参数。
     *  3. 可以在参数最后添加BlockException 可以区分是什么规则的处理方法
     * @param id
     * @param ex
     * @return
     */
    public User blockHandlerForGetUser(String id, BlockException ex) {
        ex.printStackTrace();
        return new User("流控！！");
    }


    @RequestMapping("/degrade")
    @SentinelResource(value = DEGRADE_RESOURCE_NAME,entryType = EntryType.IN,
            blockHandler = "blockHandlerForFb")
    public User degrade(String id) throws InterruptedException {
        // 异常数\比例
        throw new RuntimeException("异常");

        /* 慢调用比例
        TimeUnit.SECONDS.sleep(1);
        return new User("正常");*/
    }

    public User blockHandlerForFb(String id, BlockException ex) {
        return new User("熔断降级");
    }


}
