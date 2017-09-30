package com.icthh.xm.ms.entity.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Properties specific to JHipster.
 *
 * <p>Properties are configured in the application.yml file.
 */
@Component
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
@Getter
@Setter
public class ApplicationProperties {

    private final Amazon amazon = new Amazon();
    private final Retry retry = new Retry();

    private List<String> tenantIgnoredPathList;
    /**
     * Default max avatar size 1Mb
     */
    private long maxAvatarSize = 1024 * 1024;
    private boolean timelinesEnabled;
    private boolean kafkaEnabled;
    private List<String> tenantCreateServiceList;
    private Integer tenantClientConnectionTimeout;
    private Integer tenantClientReadTimeout;
    private String kafkaSystemTopic;
    private String kafkaSystemQueue;

    @Getter
    @Setter
    private String specificationPathPattern;

    @Getter
    @Setter
    private String specificationName;

    private String specificationWebappName;
    private String webappName;

    @Getter
    @Setter
    public static class Amazon {

        private final Aws aws = new Aws();
        private final S3 s3 = new S3();

        @Getter
        @Setter
        public static class Aws {

            private String endpoint;
            private String template;
            private String region;
            private String accessKeyId;
            private String accessKeySecret;
        }

        @Getter
        @Setter
        public static class S3 {

            private String bucket;
        }
    }

    @Getter
    @Setter
    private static class Retry {
        private int maxAttempts;
        private long delay;
        private int multiplier;
    }
}
