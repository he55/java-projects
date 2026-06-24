package com.example.commonservice.service;

import com.example.commonservice.util.Preconditions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

@Service
public class QRCodeServiceImpl implements QRCodeService {

    private static final int MAX_DIMENSION = 4096;

    @Override
    public void generate(HttpServletRequest request, HttpServletResponse response) {
        try {
            doEncode(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void doEncode(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ChartServletRequestParameters parameters;
        try {
            parameters = doParseParameters(request);
        } catch (IllegalArgumentException | NullPointerException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.toString());
            return;
        }

        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.MARGIN, parameters.getMargin());
        if (!StandardCharsets.ISO_8859_1.equals(parameters.getOutputEncoding())) {
            // Only set if not QR code default
            hints.put(EncodeHintType.CHARACTER_SET, parameters.getOutputEncoding().name());
        }
        hints.put(EncodeHintType.ERROR_CORRECTION, parameters.getEcLevel());

        BitMatrix matrix;
        try {
            matrix = new QRCodeWriter().encode(parameters.getText(),
                    BarcodeFormat.QR_CODE,
                    parameters.getWidth(),
                    parameters.getHeight(),
                    hints);
        } catch (WriterException we) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, we.toString());
            return;
        }

        String requestURI = request.getRequestURI();
        if (requestURI == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        int lastDot = requestURI.lastIndexOf('.');
        String imageFormat;
        if (lastDot > 0) {
            imageFormat = requestURI.substring(lastDot + 1).toUpperCase(Locale.ROOT);
            // Special-case jpg -> JPEG
            if ("JPG".equals(imageFormat)) {
                imageFormat = "JPEG";
            }
        } else {
            imageFormat = "PNG";
        }

        String contentType;
        switch (imageFormat) {
            case "PNG":
                contentType = "image/png";
                break;
            case "JPEG":
                contentType = "image/jpeg";
                break;
            case "GIF":
                contentType = "image/gif";
                break;
            default:
                throw new IllegalArgumentException("Unknown format " + imageFormat);
        }

        ByteArrayOutputStream imageOut = new ByteArrayOutputStream(1024);
        MatrixToImageWriter.writeToStream(matrix, imageFormat, imageOut);
        byte[] imageData = imageOut.toByteArray();

        response.setContentType(contentType);
        response.setContentLength(imageData.length);
        response.setHeader("Cache-Control", "public");
        response.getOutputStream().write(imageData);
    }

    private static ChartServletRequestParameters doParseParameters(ServletRequest request) {

        String chartType = request.getParameter("cht");
        Preconditions.checkArgument(chartType == null || "qr".equals(chartType), "Bad type");

        String widthXHeight = request.getParameter("chs");
        Preconditions.checkNotNull(widthXHeight, "No size");
        int xIndex = widthXHeight.indexOf('x');
        Preconditions.checkArgument(xIndex >= 0, "Bad size");

        int width = Integer.parseInt(widthXHeight.substring(0, xIndex));
        int height = Integer.parseInt(widthXHeight.substring(xIndex + 1));
        Preconditions.checkArgument(width > 0 && height > 0, "Bad size");
        Preconditions.checkArgument(width <= MAX_DIMENSION && height <= MAX_DIMENSION, "Bad size");

        String outputEncodingName = request.getParameter("choe");
        Charset outputEncoding;
        outputEncoding = StandardCharsets.UTF_8;

        ErrorCorrectionLevel ecLevel = ErrorCorrectionLevel.L;
        int margin = 4;

        String ldString = request.getParameter("chld");
        if (ldString != null) {
            int pipeIndex = ldString.indexOf('|');
            if (pipeIndex < 0) {
                // Only an EC level
                ecLevel = ErrorCorrectionLevel.valueOf(ldString);
            } else {
                ecLevel = ErrorCorrectionLevel.valueOf(ldString.substring(0, pipeIndex));
                margin = Integer.parseInt(ldString.substring(pipeIndex + 1));
                Preconditions.checkArgument(margin > 0, "Bad margin");
            }
        }

        String text;
        text = request.getParameter("chl");
        Preconditions.checkArgument(text != null && !text.isEmpty(), "No input");

        return new ChartServletRequestParameters(width, height, outputEncoding, ecLevel, margin, text);
    }

}
