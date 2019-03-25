package org.minbox.framework.api.boot.autoconfigure.oauth;

import org.minbox.framework.api.boot.plugin.oauth2.ApiBootAuthorizationServerConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import static org.minbox.framework.api.boot.autoconfigure.oauth.ApiBootOauthProperties.API_BOOT_OAUTH_PREFIX;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-25 15:26
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
@Configuration
@ConditionalOnClass(ApiBootAuthorizationServerConfiguration.class)
@EnableConfigurationProperties(ApiBootOauthProperties.class)
@EnableAuthorizationServer
@ConditionalOnProperty(prefix = API_BOOT_OAUTH_PREFIX, name = "away", havingValue = "memory", matchIfMissing = true)
public class ApiBootAuthorizationServerMemoryAutoConfiguration extends ApiBootAuthorizationServerAutoConfiguration {

    public ApiBootAuthorizationServerMemoryAutoConfiguration(ApiBootOauthProperties apiBootOauthProperties) {
        super(apiBootOauthProperties);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(apiBootOauthProperties.getClientId())
                .authorizedGrantTypes(apiBootOauthProperties.getGrantTypes())
                .secret(passwordEncoder().encode(apiBootOauthProperties.getClientSecret()))
                .scopes(apiBootOauthProperties.getScopes());
    }

    /**
     * 配置内存方式令牌存储
     *
     * @return TokenStore
     */
    @Bean
    public TokenStore memoryTokenStore() {
        return new InMemoryTokenStore();
    }
}
