import java.io.*;
import java.nio.file.Files;

public class main {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/crayon/Data/研究生/论文写作/Data-driven Code-Comment Consistency Detection and Analysis/实验/实验代码/Java三步骤旧版本/getChanges_on_CCset/CCSet_changes");
        filesDirs(file);
    }

    public static void filesDirs(File file) throws IOException {
        if (file != null) {
            //第二层路径不为空，判断是文件夹还是文件
            if (file.isDirectory()) {
                //进入这里说明为文件夹，此时需要获得当前文件夹下所有文件，包括目录
                File[] files = file.listFiles();
                for (File flies2 : files) {
                    filesDirs(flies2);
                }
            } else {
                String filename = file.getName();
                if(filename.endsWith(".java")){
//                    System.out.println(file.toString());
//                    System.out.println(filename);
                    FileReader fileReader = new FileReader(file);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String str = "";
                    int change_num = 0;
                    boolean changetype = false;
                    while((str = bufferedReader.readLine())!=null){
                        if(str.startsWith("change_num:\t")){
//                            System.out.println(str);
                            final String[] split = str.split(":");
//                            System.out.println(split[split.length-1].trim());
                            final int i = Integer.parseInt(split[split.length - 1].trim());
                            change_num = i;
                        }
                        if(str.startsWith("change_type")){
//                            System.out.println(str);
                            if(str.contains("COMMENT")||str.contains("DOC")){
                                if(str.contains("UPDATE") || str.contains("DELETE")){
                                    changetype = true;
                                }
//                                changetype = true;
                            }
                        }
                    }
                    if(changetype && change_num == 1){
//                      if(changetype ){

                            System.out.println(file.getPath());
                        final String[] split = file.getPath().split("/");
                        FileWriter fileWriter = new FileWriter("/Users/crayon/Data/研究生/论文写作/Data-driven Code-Comment Consistency Detection and Analysis/实验/实验代码/OnlyCommentChange/CommentChange/"+split[split.length-1]);
                        fileReader = new FileReader(file);
                        bufferedReader = new BufferedReader(fileReader);
                        str = "";
                        while((str = bufferedReader.readLine())!=null){
                            fileWriter.write(str+"\n");
                        }
                        fileWriter.close();
                    }
                }
            }
        } else {
            System.out.println("文件不存在");
        }
    }
}
