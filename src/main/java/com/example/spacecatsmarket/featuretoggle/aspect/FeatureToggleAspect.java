package com.example.spacecatsmarket.featuretoggle.aspect;

import com.example.spacecatsmarket.featuretoggle.FeatureToggleService;
import com.example.spacecatsmarket.featuretoggle.FeatureToggles;
import com.example.spacecatsmarket.featuretoggle.annotation.FeatureToggle;
import com.example.spacecatsmarket.featuretoggle.exception.FeatureToggleNotEnabledException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class FeatureToggleAspect {

    private final FeatureToggleService featureToggleService;

    @Before(value = "@annotation(featureToggle)")
    public void checkFeatureToggleAnnotation(FeatureToggle featureToggle) {
        FeatureToggles toggle = featureToggle.value();

        if (!featureToggleService.check(toggle.getFeatureName())) {
            log.warn("Feature toggle {} is not enabled!", toggle.getFeatureName());
            throw new FeatureToggleNotEnabledException(toggle.getFeatureName());
        }
    }
}
