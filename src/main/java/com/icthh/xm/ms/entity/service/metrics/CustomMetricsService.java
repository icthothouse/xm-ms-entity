package com.icthh.xm.ms.entity.service.metrics;

import static com.icthh.xm.commons.lep.XmLepConstants.THREAD_CONTEXT_KEY_AUTH_CONTEXT;
import static com.icthh.xm.commons.lep.XmLepConstants.THREAD_CONTEXT_KEY_TENANT_CONTEXT;
import static java.util.Collections.emptyMap;

import com.icthh.xm.commons.lep.LogicExtensionPoint;
import com.icthh.xm.commons.lep.spring.LepService;
import com.icthh.xm.commons.logging.util.MdcUtils;
import com.icthh.xm.commons.security.XmAuthenticationContextHolder;
import com.icthh.xm.commons.tenant.TenantContextHolder;
import com.icthh.xm.commons.tenant.TenantContextUtils;
import com.icthh.xm.lep.api.LepManager;
import com.icthh.xm.ms.entity.service.metrics.CustomMetricsConfiguration.CustomMetric;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@LepService(group = "metrics")
@RequiredArgsConstructor
public class CustomMetricsService {

    private final Map<String, Map<String, Object>> metricsCache = new ConcurrentHashMap<>();
    @Setter(onMethod = @__(@Autowired))
    private CustomMetricsService self;

    private final TenantContextHolder tenantContextHolder;
    private final XmAuthenticationContextHolder authContextHolder;
    private final LepManager lepManager;

    public Object getMetric(String name, CustomMetric config, String tenantKey) {
        return runInTenantContext(tenantKey, () -> {
            if (config.getUpdatePeriodSeconds() == null) {
                return self.metricByName(name);
            }
            return metricsCache.getOrDefault(tenantKey, emptyMap()).get(name);
        });
    }

    public void updateMetric(String metricName, String tenant) {
        try {
            MdcUtils.putRid(MdcUtils.generateRid() + ":" + tenant);
            metricsCache.computeIfAbsent(tenant, (key) -> new ConcurrentHashMap<>());
            Object metricValue = runInTenantContext(tenant, () -> self.metricByName(metricName));
            metricsCache.get(tenant).put(metricName, metricValue);
        } catch (Throwable e) {
            log.error("Error update metric", e);
        } finally {
            MdcUtils.clear();
        }
    }

    private Object runInTenantContext(String tenant, Supplier<Object> operation) {
        try {
            init(tenant);
            return operation.get();
        } finally {
            destroy();
        }
    }

    private void init(String tenantKey) {
        TenantContextUtils.setTenant(tenantContextHolder, tenantKey);

        lepManager.beginThreadContext(threadContext -> {
            threadContext.setValue(THREAD_CONTEXT_KEY_TENANT_CONTEXT, tenantContextHolder.getContext());
            threadContext.setValue(THREAD_CONTEXT_KEY_AUTH_CONTEXT, authContextHolder.getContext());
        });
    }

    private void destroy() {
        lepManager.endThreadContext();
        tenantContextHolder.getPrivilegedContext().destroyCurrentContext();
    }

    @LogicExtensionPoint(value = "Metric", resolver = MetricKeyResolver.class)
    public Object metricByName(String metricName) {
        return self.metric(metricName);
    }

    @LogicExtensionPoint(value = "Metric")
    public Object metric(String metricName) {
        log.error("No lep for metric {} found", metricName);
        return null;
    }

}
