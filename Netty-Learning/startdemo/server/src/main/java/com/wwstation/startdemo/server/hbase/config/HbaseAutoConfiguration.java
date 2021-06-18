package com.wwstation.startdemo.server.hbase.config;


import com.wwstation.startdemo.server.hbase.service.HbaseTemplate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * JThink@JThink
 *
 * @author JThink
 * @version 0.0.1
 * desc： hbase auto configuration
 * date： 2016-11-16 11:11:27
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(HbaseProperties.class)
@ConditionalOnClass(HbaseTemplate.class)
public class HbaseAutoConfiguration {

    private static final String HBASE_QUORUM = "hbase.zookeeper.quorum";
    private static final String HBASE_ROOTDIR = "hbase.rootdir";
    private static final String HBASE_ZNODE_PARENT = "zookeeper.znode.parent";


    @Autowired
    private HbaseProperties hbaseProperties;

    @Bean
    @ConditionalOnMissingBean(HbaseTemplate.class)
    public HbaseTemplate hbaseTemplate() {
        Configuration configuration = HBaseConfiguration.create();
//        configuration.set(HBASE_QUORUM, this.hbaseProperties.getQuorum());
//        configuration.set(HBASE_ROOTDIR, hbaseProperties.getRootDir());
//        configuration.set(HBASE_ZNODE_PARENT, hbaseProperties.getNodeParent());
        configuration.set("hbase.zookeeper.quorum", "hdp1.infiai.com");
        configuration.set("hbase.zookeeper.property.clientPort", "2181");
        configuration.set("hbase.master", "hdp1.infiai.com"); // master info
        configuration.set("hbase.master.port", "16000");
        return new HbaseTemplate(configuration);
    }
}
