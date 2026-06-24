package com.example.commonservice.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface QRCodeService {

    void generate(HttpServletRequest request, HttpServletResponse response);
}
