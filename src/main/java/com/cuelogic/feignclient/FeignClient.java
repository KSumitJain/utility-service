package com.cuelogic.feignclient;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.cloud.openfeign.FeignClient(value = "imageProcessor",
        url = "http://localhost:5000/")
public interface FeignClient {
    @PostMapping(value = "upload-video-lpr")
    void imageProcessing(@RequestParam(required = false) String sourcePath);

}
