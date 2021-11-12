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
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lixiaoshuang
 */
@RestController
@RequestMapping("springboot/config")
public class ConfigController {
    
    @NacosInjected
    private ConfigService configService;
    
    
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
}
