package com.ly.job;

import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.text.ParseException;
import java.util.Properties;


@Configuration
public class SchedulerConfig {

    private static final Logger LOG = LoggerFactory.getLogger(SchedulerConfig.class);

    @Autowired
    DataSource dataSource;

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, Trigger simpleJobTrigger,Trigger lendOrderJobTrigger)
            throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setOverwriteExistingJobs(true);
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);
        factory.setQuartzProperties(quartzProperties());
        factory.setTriggers(simpleJobTrigger,lendOrderJobTrigger);

        LOG.info("starting jobs....");
        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    @Bean
    public CronTriggerFactoryBean simpleJobTrigger(@Qualifier("simpleJobDetail") JobDetail jobDetail,
                                                     @Value("${favjob.corn}") String corn) throws ParseException {
        LOG.info("simpleJobTrigger");

        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setCronExpression(corn);
        factoryBean.setName("favfixjob");
        return factoryBean;
    }

    @Bean
    public JobDetailFactoryBean simpleJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(FavFixJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("favfixjob");
        return factoryBean;
    }

    @Bean
    public CronTriggerFactoryBean lendOrderJobTrigger(@Qualifier("lendorderJobDetail") JobDetail jobDetail,
                                                   @Value("${lend.order.corn}") String corn) throws ParseException {
        LOG.info("lendOrderJobTrigger");

        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setJobDetail(jobDetail);
        factoryBean.setStartDelay(0L);
        factoryBean.setCronExpression(corn);
        factoryBean.setName("lendOrderjob");
        return factoryBean;
    }

    @Bean
    public JobDetailFactoryBean lendorderJobDetail() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(LendOrderJob.class);
        factoryBean.setDurability(true);
        factoryBean.setName("lendorderJob");
        return factoryBean;
    }

}