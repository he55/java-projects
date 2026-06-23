package com.example.commonservice.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ShortLinkCreateReqDTO {

    @NotBlank(message = "url 不能为空")
    private String url;

}
