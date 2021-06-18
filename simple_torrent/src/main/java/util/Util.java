package util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Util {

    static final int BYTE_SIZE = 2048;
    public static final String SEPLINE = "------------------------------------------------";
    static List<String> unitsM = Arrays.asList("B", "Kb", "Mb", "Gb");

    public static String getFileSize(long size) {
        double[] ans = new double[4];

        ans[0] = (double) size;
        for (int i = 1; i < 3; i++) {
            ans[i] = (double) size / 1024;
            size /= 1024;
        }
        for (int i = 3; i > 0; i--) {
            if (ans[i] > 1) {
                return String.format("%.3f %s", ans[i], unitsM.get(i));
            }
        }
        return "";
    }

    public static void progressPercentage(long remain, long total) {
        if (remain > total) {
            throw new IllegalArgumentException();
        }

        int maxBareSize = 30;
        int remainPercent = Math.round((float) remain / total * maxBareSize);
        char defaultChar = '◦';
        String icon = "▣";
        String bare = new String(new char[maxBareSize]).replace('\0', defaultChar) + "]";
        StringBuilder bareDone = new StringBuilder();

        bareDone.append("[");
        bareDone.append(icon.repeat(Math.max(0, remainPercent)));

        String bareRemain = bare.substring(remainPercent);
        System.out.print("\r" + bareDone + bareRemain + " " +
                Math.round((double) remainPercent / maxBareSize * 100) + "%");
        if (remain == total) {
            System.out.print("\n");
        }
    }


    public static long copy(InputStream in, OutputStream out, long size) throws IOException {
        byte[] buf = new byte[BYTE_SIZE];
        int bytesRead = 0;
        long totalBytes = 0;

        while (totalBytes != size) {
            bytesRead = in.read(buf);
            totalBytes += bytesRead;
            out.write(buf, 0, bytesRead);
            progressPercentage(totalBytes, size);
        }
        return totalBytes;
    }

}