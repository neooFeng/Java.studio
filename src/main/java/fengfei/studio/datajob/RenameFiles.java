package fengfei.studio.datajob;

import java.io.File;

public class RenameFiles {

    public static void main(String[] args) {

        String folderPath = "C:\\Users\\fengfei\\Documents\\WXWork\\1688853491666120\\Cache\\File\\2021-02\\江苏科技大学学籍照片\\";

        File folder = new File(folderPath);

        File[] files = folder.listFiles();
        for (File file : files){
            if (file.isFile()){
                String name = file.getName();
                if (name.startsWith("Z")){
                    file.renameTo(new File(folderPath + name.replace("Z","")));
                }
            }
        }
    }
}
