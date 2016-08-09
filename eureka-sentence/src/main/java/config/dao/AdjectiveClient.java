package config.dao;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("ADJECTIVE")
public interface AdjectiveClient {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String getWord();

}
