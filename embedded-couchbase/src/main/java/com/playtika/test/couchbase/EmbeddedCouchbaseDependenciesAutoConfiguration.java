package com.playtika.test.couchbase;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.playtika.test.common.spring.DependsOnPostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.playtika.test.couchbase.CouchbaseProperties.BEAN_NAME_EMBEDDED_COUCHBASE;

@Slf4j
@Configuration
@AutoConfigureOrder
@ConditionalOnExpression("${embedded.containers.enabled:true}")
@ConditionalOnProperty(name = "embedded.couchbase.enabled", matchIfMissing = true)
@AutoConfigureAfter(name = "org.springframework.boot.autoconfigure.data.couchbase.CouchbaseDataAutoConfiguration")
public class EmbeddedCouchbaseDependenciesAutoConfiguration {

    @Configuration
    @ConditionalOnClass(Bucket.class)
    public static class CouchbaseBucketDependencyContext {
        @Bean
        public static BeanFactoryPostProcessor bucketDependencyPostProcessor() {
            return new DependsOnPostProcessor(Bucket.class, new String[]{BEAN_NAME_EMBEDDED_COUCHBASE});
        }
    }

    @Configuration
    @ConditionalOnClass(AsyncBucket.class)
    public static class CouchbaseAsyncBucketDependencyContext {
        @Bean
        public static BeanFactoryPostProcessor asyncBucketDependencyPostProcessor() {
            return new DependsOnPostProcessor(AsyncBucket.class, new String[]{BEAN_NAME_EMBEDDED_COUCHBASE});
        }
    }

    @Configuration
    @ConditionalOnClass(Cluster.class)
    public static class CouchbaseClusterDependencyContext {
        @Bean
        public static BeanFactoryPostProcessor couchbaseClusterDependencyPostProcessor() {
            return new DependsOnPostProcessor(Cluster.class, new String[]{BEAN_NAME_EMBEDDED_COUCHBASE});
        }
    }

}
