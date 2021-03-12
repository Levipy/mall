package com.levi.mall.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;

/**
 * Created with IntelliJ IDEA.
 *
 * @auther Levi
 * @Date: 2021/03/10/14:28
 * @Description:
 */
public class QRCoderGenerator {

    public static void genertorQrCodeImage(String text,int width,int height,String filePath) throws WriterException,
            IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        //生成的二维码存放的地址
        Path path = FileSystems.getDefault().getPath(filePath);
        //把矩阵生成图片
        MatrixToImageWriter.writeToPath(bitMatrix,"PNG",path);
    }

    public static void main(String[] args) throws IOException, WriterException {
        genertorQrCodeImage("hello spring",350,350,"E:/IdeaProjects/mall/src/main/java/pictures/qrCodTest.png");
    }
}
