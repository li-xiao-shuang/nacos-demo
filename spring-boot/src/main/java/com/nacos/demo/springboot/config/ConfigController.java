/*
 * Copyright 2021 Gypsophila open source organization.
 *
 * Licensed under the Apache License,Version2.0(the"License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nacos.demo.springboot.config;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;

import static org.springframework.core.env.StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME;
import static org.springframework.core.env.StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME;

/**
 * @author lixiaoshuang
 */
@RestController
@RequestMapping("springboot/config")
@NacosPropertySource(name = "custom", dataId = "user.info", first = true, before = SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME, after = SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME)
public class ConfigController {
    
    @NacosInjected
    private ConfigService configService;
    
    /**
     * dataId : user.info content: name = hh age = 22
     */
    @NacosValue(value = "${name}", autoRefreshed = true)
    private String name;
    
    @NacosValue(value = "${age}", autoRefreshed = true)
    private String age;
    
    @Autowired
    private UserInfoConfig userInfoConfig;
    
    @Autowired
    private UserInfoConfig2 userInfoConfig2;
    
    
    /**
     * 获取配置信息
     *
     * @param dataId
     * @param group
     * @return
     * @throws NacosException
     */
    @RequestMapping("getConfig")
    public String getConfig(@RequestParam("dataId") String dataId, @RequestParam("group") String group)
            throws NacosException {
        return configService.getConfig(dataId, group, 2000);
    }
    
    /**
     * 发布配置
     *
     * @param dataId
     * @param group
     * @param content
     * @return
     * @throws NacosException
     */
    @RequestMapping("publishConfig")
    public boolean publishConfig(@RequestParam("dataId") String dataId, @RequestParam("group") String group,
            @RequestParam("content") String content) throws NacosException {
        return configService.publishConfig(dataId, group, content);
    }
    
    /**
     * 删除配置
     *
     * @param dataId
     * @param group
     * @return
     * @throws NacosException
     */
    @RequestMapping("remoteConfig")
    public boolean remoteConfig(@RequestParam("dataId") String dataId, @RequestParam("group") String group)
            throws NacosException {
        return configService.removeConfig(dataId, group);
    }
    
    
    /**
     * 监听配置信息
     *
     * @param dataId
     * @param group
     * @throws NacosException
     */
    @RequestMapping("listener")
    public void listenerConfig(@RequestParam("dataId") String dataId, @RequestParam("group") String group)
            throws NacosException {
        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }
            
            @Override
            public void receiveConfigInfo(String configInfo) {
                System.out.println("监听配置：" + configInfo);
            }
        });
    }
    
    /**
     * 获取自动刷新配置 需要使用 @NacosPropertySource 注解配合@NacosValue来使用
     *
     * @return
     */
    @RequestMapping("userInfo")
    public void getUserInfo() {
        //方式1
        System.out.println("方式1 name: " + name);
        System.out.println("方式1 age: " + age);
        //方式2
        System.out.println("方式2 name: " + userInfoConfig.getName());
        System.out.println("方式2 age: " + userInfoConfig.getAge());
        //方式3
        System.out.println("方式3 name " + userInfoConfig2.getName());
        System.out.println("方式3 age: " + userInfoConfig2.getAge());
    }
    
}
