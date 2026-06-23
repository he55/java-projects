package com.example.commonservice.controller;

import com.example.commonservice.dto.req.ShortLinkCreateReqDTO;
import com.example.commonservice.dto.resp.ShortLinkCreateRespDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.time.Duration;
import java.util.UUID;

@RestController
@RequestMapping("/short")
public class ShortLinkController {

    static final String SHORT_LINK_KEY = "short_link:%s";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping
    public ShortLinkCreateRespDTO createShortLink(@Valid @RequestBody ShortLinkCreateReqDTO shortLinkCreateReqDTO) {
        String key = UUID.randomUUID().toString().replace("-", "");

        stringRedisTemplate.opsForValue()
                .set(SHORT_LINK_KEY.formatted(key), shortLinkCreateReqDTO.getUrl(), Duration.ofDays(1));

        ShortLinkCreateRespDTO shortLinkCreateRespDTO = new ShortLinkCreateRespDTO();
        shortLinkCreateRespDTO.setShortUrl("http://localhost:8080/short/" + key);
        shortLinkCreateRespDTO.setOriginUrl(shortLinkCreateReqDTO.getUrl());
        return shortLinkCreateRespDTO;
    }

    @GetMapping("/{shortLink}")
    public ResponseEntity<?> resolveShortLink(@PathVariable String shortLink) {
        String url = stringRedisTemplate.opsForValue().get(SHORT_LINK_KEY.formatted(shortLink));
        if (url == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(url))
                .build();
    }

}
