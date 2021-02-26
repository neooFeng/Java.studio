package fengfei.studio.oss;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ListObjectsSample {
    private static String savePath = "C:\\Users\\fengfei\\Desktop\\list.txt";
    private static String bucketName = "chengjiaogj";

    public static void main(String[] args) {

        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAIdtT4OArP2b1t";
        String accessKeySecret = "QIpS0GZGr7rx9jp1PPV7jSrRwC9B86";
        String token = "CAIS/wF1q6Ft5B2yfSjIr5fDJdSBppFCx5GnSGv70FAmaNVGp7bg1Tz2IH1LeXJqBe0csfwzlWhZ6fsdlqRoRoReREvCKMJ865VRqeUCtyRA/Z7b16cNrbH4M1rxYkeJ4a2/SuH9S8ynDZXJQlvYlyh17KLnfDG5JTKMOoGIjpgVAbZ6WRKjP3g8R7UwHAZ5r9IAPnb8LOukNgWQ4lDdF011oAFx+wQdjK202Z+b8QGMzg+4mK03392gcsX8MpI1YcsiD4bujbJMG/CfgHIK2X9j77xriaFIwzDDs+yGDkNZixf8aLeLr4Y1cFIjOfBmSvYV86Wjj5Z/offDa1ETAq2JlooagAF9XRldpSVzRyQ2TakS1xL07mCOT7M6WE/DUBdQRmcG28/63jA5FhPKIfzxCKrIpNjU2V1eJBNtl3K1orBTIwycNa6v/4UoiZIMwfFUzuLjasrSZ+JesCyFIhq2qMbg6MGtRTaa/qYNDH6Ly2mc+l+K0DsMCMTLp9faH1dGn+TUIw==";


        OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            final String keyPrefix = "courseWareFile/2020-12";

            ObjectListing objectListing = null;
            String nextMarker = null;
            final int maxKeys = 50;
            List<OSSObjectSummary> sums = null;


            // Gets the object with specified prefix. By default it returns up to 100 entries.
            /*System.out.println("With prefix:");
            objectListing = client.listObjects(new ListObjectsRequest(bucketName).withPrefix(keyPrefix));

            sums = objectListing.getObjectSummaries();
            for (OSSObjectSummary s : sums) {
                System.out.println("\t" + s.getKey());

            }*/


            // Gets all object with specified marker by paging. Each page will have up to 100 entries.
            System.out.println("List all objects with prefix:");
            nextMarker = null;

            long size = 0;
            int count = 0;
            do {
                StringBuilder stringBuilder = new StringBuilder();

                objectListing = client.listObjects(new ListObjectsRequest(bucketName).
                        withPrefix(keyPrefix).withMarker(nextMarker).withMaxKeys(maxKeys));

                sums = objectListing.getObjectSummaries();
                for (OSSObjectSummary s : sums) {
                    size += s.getSize() /1024 /1024;
                    stringBuilder.append(s.getKey()).append("\n");
                }

                count += sums.size();
                System.out.println("reading: " + count);

                nextMarker = objectListing.getNextMarker();

                //Files.write(Paths.get(savePath), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
            } while (objectListing.isTruncated());

            System.out.println(size + "MB");

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorMessage());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            client.shutdown();
        }
    }
}
