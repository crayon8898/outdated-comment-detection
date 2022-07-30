import ch.uzh.ifi.seal.changedistiller.ChangeDistiller;
import ch.uzh.ifi.seal.changedistiller.ast.FileUtils;
import ch.uzh.ifi.seal.changedistiller.distilling.FileDistiller;
import ch.uzh.ifi.seal.changedistiller.model.classifiers.ChangeType;
import ch.uzh.ifi.seal.changedistiller.model.entities.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class getChanges {
    static int block_id = 0;
    static int method_id = 0;
    static int total_id = 0;
    public static void main(String[] args) throws IOException {
        String[] xiangmu = {"opennms"};
        for (int i = 0; i < xiangmu.length; i++) {
            filesDirs(new File("/Users/crayon/IdeaProjects/ast_getCCSet/CCSet/"+xiangmu[i]));

        }
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
                    get_result(file.toString());
                }
            }
        } else {
            System.out.println("文件不存在");
        }
    }

    public static void get_result(String filename) throws IOException {
        String[] split1 = filename.split("/");
        List<String> result = new ArrayList<>();
        List<String> new_old_start_end = new ArrayList<>();
        File file = new File(filename);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String str = "";
        while((str = bufferedReader.readLine())!=null){
            System.out.println(str);
            result.add(str);
            if(str.contains("new/src"))new_old_start_end.add(str);
            if(str.contains("old/src"))new_old_start_end.add(str);
            if(str.contains("startline:")){
                String[] split = str.split(":");
                new_old_start_end.add(split[split.length-1]);
            }
            if(str.contains("endline:")){
                String[] split = str.split(":");
                new_old_start_end.add(split[split.length-1]);
            }
            if(str.contains("-----new") || str.contains("-----old")){
                if(str.contains("new")){
                    new_old_start_end.add("new");
                }else new_old_start_end.add("old");
            }
        };
        fileReader.close();
        bufferedReader.close();

        String oldfile = new_old_start_end.get(2);
        String newfile = new_old_start_end.get(1);
        List<String> changes_str = changedistiller(oldfile, newfile, Integer.parseInt(new_old_start_end.get(3)), Integer.parseInt(new_old_start_end.get(4)), new_old_start_end.get(0));

        for (int i = 0; i < changes_str.size(); i++) {
            System.out.println(changes_str.get(i));
            result.add(changes_str.get(i));
        }
        if(changes_str.size()>5){ //注释范围内有变更
//            FileWriter fileWriter = new FileWriter("/Users/crayon/IdeaProjects/getChanges_on_CCset/CCSet_changes/test/"+split1[split1.length-1]);

            boolean Method = false;
            for(int i=0;i< result.size();i++){
                if(result.get(i).contains("BLOCK_COMMENT")){
                    Method = false;
                    result.add("BLOCK_ID:"+block_id);
                    result.add("TOTAL_ID:"+total_id);

                    block_id++;
                    total_id++;
                }
                if(result.get(i).contains("METHOD_COMMENT")){
                    Method = true;
                    result.add("METHOD_ID:"+method_id);
                    result.add("TOTAL_ID:"+total_id);
                    method_id++;
                    total_id++;
                }
            }

            if(Method){
                FileWriter fileWriter = new FileWriter("/Users/crayon/IdeaProjects/getChanges_on_CCset/CCSet_changes/ByType/Method_Comment/"+split1[split1.length-2]+"/"+split1[split1.length-1]);
                for (int i = 0; i < result.size(); i++) {
                    fileWriter.write(result.get(i)+"\n");
                }
                fileWriter.close();
            }
            if(!Method){
                FileWriter fileWriter = new FileWriter("/Users/crayon/IdeaProjects/getChanges_on_CCset/CCSet_changes/ByType/Block_Comment/"+split1[split1.length-2]+"/"+split1[split1.length-1]);
                for (int i = 0; i < result.size(); i++) {
                    fileWriter.write(result.get(i)+"\n");
                }
                fileWriter.close();
            }

        }
    }
    private static List<String> changedistiller(String oldfile, String newfile,int startline,int endline,String old_or_new) throws IOException {
        List<String> result = new ArrayList<>();
        File file1 = new File(oldfile);
        File file2 = new File(newfile);
        int change_num = 0;
        FileDistiller distiller = ChangeDistiller.createFileDistiller(ChangeDistiller.Language.JAVA);
        try {
            distiller.extractClassifiedSourceCodeChanges(file1, file2);
        } catch(Exception e) {
            System.err.println("Warning: error while change distilling. " + e.getMessage());
        }
        List<SourceCodeChange> changes = distiller.getSourceCodeChanges();
        if(changes != null ) {
            //对change进行细粒度操作
            //记录start位置 和 类型
            for(SourceCodeChange change : changes) {
                int change_start_line = 0;
                int change_end_line = 0;
                if(old_or_new.equals("new")){
                    change_start_line = getLineNumber(newfile,change.getChangedEntity().getStartPosition());
                    change_end_line = getLineNumber(newfile,change.getChangedEntity().getEndPosition());
                }else {
                    change_start_line = getLineNumber(oldfile,change.getChangedEntity().getStartPosition());
                    change_end_line = getLineNumber(oldfile,change.getChangedEntity().getEndPosition());
                }
                if(change_start_line >= startline && change_end_line <= endline){
                    result.add("change "+change_num+" : "+change_start_line+","+change_end_line);
                    result.add("change:"+change.toString());
                    result.add("change_getClass:"+change.getClass().toString());
                    result.add("change_type:"+change.getChangeType().toString());
                    result.add("change_entity:"+change.getChangedEntity().toString());
                    result.add("change_entity_SourceRange:"+change.getChangedEntity().getSourceRange().toString());
                    result.add("change_entity_uniqueName:"+change.getChangedEntity().getUniqueName());
                    result.add("change_entity_getClass:"+change.getChangedEntity().getClass().toString());
                    result.add("change_entity_label:"+change.getChangedEntity().getLabel());
                    result.add("change_entity_type:"+change.getChangedEntity().getType().toString());
                    result.add("isNative:"+String.valueOf(change.getChangedEntity().isNative()));
                    result.add("isPrivate:"+String.valueOf(change.getChangedEntity().isPrivate()));
                    result.add("isVolatile:"+String.valueOf(change.getChangedEntity().isVolatile()));
                    result.add("isAbstract:"+String.valueOf(change.getChangedEntity().isAbstract()));
                    result.add("isFinal:"+String.valueOf(change.getChangedEntity().isFinal()));
                    result.add("isProtected:"+String.valueOf(change.getChangedEntity().isProtected()));
                    result.add("isPublic:"+String.valueOf(change.getChangedEntity().isPublic()));
                    result.add("isStatic:"+String.valueOf(change.getChangedEntity().isStatic()));
                    result.add("isSynchronized:"+String.valueOf(change.getChangedEntity().isSynchronized()));
                    result.add("isTransient:"+String.valueOf(change.getChangedEntity().isTransient()));
                    result.add("isBodyChange:"+String.valueOf(change.getChangeType().isBodyChange()));
                    result.add("isDeclarationChange:"+String.valueOf(change.getChangeType().isDeclarationChange()));
                    result.add("\n");
                    change_num++;
                }
                // see Javadocs for more information
            }
        }
        result.add("change_num:\t"+change_num);
        return result;
    }
    public static int getLineNumber(String file, int position){
        int lineNum = 1;
        String fileContent = FileUtils.getContent(new File(file));
        char[] charArray = fileContent.toCharArray();
        for(int i=0; i<position&&i<charArray.length; i++){
            if(charArray[i]=='\n'){
                lineNum++;
            }
        }
        return lineNum;
    }


}
