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
package com.nacos.demo.springboot.naming;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lixiaoshuang
 */
@RestController
@RequestMapping("springboot/discovery")
public class DiscoveryController {

    @NacosInjected
    private NamingService namingService;

    /**
     * 注册一个服务
     *
     * @param serviceName
     * @param ip
     * @param port
     * @return
     * @throws NacosException
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String get(@RequestParam("serviceName") String serviceName, @RequestParam("ip") String ip,
                      @RequestParam("port") int port) throws NacosException {
        namingService.registerInstance(serviceName, ip, port);
        return "注册成功";
    }

    /**
     * 获取服务所有实例
     *
     * @param serviceName
     * @return
     * @throws NacosException
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<Instance> get(@RequestParam("serviceName") String serviceName) throws NacosException {
        return namingService.getAllInstances(serviceName);
    }


}
