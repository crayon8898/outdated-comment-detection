import ch.uzh.ifi.seal.changedistiller.ChangeDistiller;
import ch.uzh.ifi.seal.changedistiller.ast.FileUtils;
import ch.uzh.ifi.seal.changedistiller.distilling.FileDistiller;
import ch.uzh.ifi.seal.changedistiller.model.classifiers.ChangeType;
import ch.uzh.ifi.seal.changedistiller.model.entities.*;
import org.eclipse.jdt.core.dom.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) throws IOException {

//        String filename = "/Users/crayon/Desktop/代码注释一致性/论文/过时注释检测Automatic Detection of Outdated Comments During Code Changes/CommitData（含论文中项目）/ejbca/3728/new/src/CA.java";
//        getCCSet(filename);
        String[] xiangmu = {"jppf","firebird"};
        for (int i = 0; i < xiangmu.length; i++) {
            filesDirs(new File("/Users/crayon/data/研究生/论文阅读/过时注释检测Automatic Detection of Outdated Comments During Code Changes/CommitData/"+xiangmu[i]));
        }
    }
    public static void filesDirs(File file) throws IOException {
        if (file != null) {
            //第二层路径不为空，判断是文件夹还是文件
            if (file.isDirectory()) {
                //进入这里说明为文件夹，此时需要获得当前文件夹下所有文件，包括目录/Users/crayon/data/研究生/论文阅读/过时注释检测Automatic Detection of Outdated Comments During Code Changes/CommitData/docfetcher/12/old/src/InputDialog.java
                File[] files = file.listFiles();
                for (File flies2 : files) {
                    filesDirs(flies2);
                }
            } else {
                //获取新旧文件名称
                String filename = file.toString();
                if(filename.endsWith(".java") && filename.contains("old/src")){
                    getCCSet(filename);
                }
            }
        } else {
            System.out.println("文件不存在");
        }
    }
    public static void getCCSet(String filename) throws IOException {
        File file= new File(filename);
        final List<List<String>> lists = comment_and_range(filename);
        int num = 0;
        for (int i = 0; i < lists.size(); i++) {
            String[] split1 = filename.split("/");
            List<String> strings = lists.get(i);
            String comment = strings.get(0);
            int startline = Integer.parseInt(strings.get(1)) + 1;
            int endline = Integer.parseInt(strings.get(2));
            String CommentType = strings.get(3);
            System.out.println(comment);
            FileInputStream inputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String str = null;
            int count = 1;
            List<String> codeSet = new ArrayList<>();
            while(count <= endline && (str = bufferedReader.readLine()) != null)
            {
                if(count >= startline) {
                    System.out.println(count+str);
                    codeSet.add(count+str+"\n");
                }
                count++;
            }
            inputStream.close();
            bufferedReader.close();
            System.out.println("-------------");
            final String[] split = filename.split("/");
            String old_or_new = split[split.length-3];
            String newfile = "" , oldfile = "";
            if(old_or_new.equals("new")){
                System.out.println("new");
                newfile = filename;
                oldfile = filename.replace("new","old");
            }else {
                System.out.println("old");
                newfile = filename.replace("old","new");
                oldfile = filename;
            }

            System.out.println(newfile);
            System.out.println(oldfile);
            System.out.println("startline:"+startline);
            System.out.println("endline:"+endline);
            FileWriter fileWriter = new FileWriter("/Users/crayon/IdeaProjects/ast_getCCSet/CCSet/"+split1[split1.length-5]+"/"+num+split1[split1.length-3]+split1[split1.length-4]+split1[split1.length-1]);
            fileWriter.write(comment+"\n");
            for (int i1 = 0; i1 < codeSet.size(); i1++) {
                fileWriter.write(codeSet.get(i1));
            }
            fileWriter.write("-----"+old_or_new.toString()+"----\n");
            fileWriter.write(newfile+"\n");
            fileWriter.write(oldfile+"\n");
            fileWriter.write("startline:"+startline+"\n");
            fileWriter.write("endline:"+endline+"\n");
            fileWriter.write("CommentType:"+CommentType);
            num++;
            fileWriter.close();
//            changedistiller(newfile, oldfile, startline, endline);
        }
    }
        /** changedistiller */
//    private static void changedistiller(String oldfile, String newfile,int startline,int endline) throws IOException {
//        File file1 = new File(oldfile);
//        File file2 = new File(newfile);
//        FileDistiller distiller = ChangeDistiller.createFileDistiller(ChangeDistiller.Language.JAVA);
//        try {
//            distiller.extractClassifiedSourceCodeChanges(file1, file2);
//        } catch(Exception e) {
//
//            System.err.println("Warning: error while change distilling. " + e.getMessage());
//        }
//        List<SourceCodeChange> changes = distiller.getSourceCodeChanges();
//        if(changes != null ) {
//            //记录start位置 和 类型
//            for(SourceCodeChange change : changes) {
//                System.out.println(change);
//            }
//            // see Javadocs for more information
//        }
//    }
    private static List<List<String>> comment_and_range(String file) throws IOException {
        int startline = 0;
        int endline = 0;
        //result = CCSet
        List<List<String>> CCSet = new ArrayList<>();
        ASTParser astParser = ASTParser.newParser(AST.JLS3);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        byte[] input = new byte[bufferedInputStream.available()];
        bufferedInputStream.read(input);
        bufferedInputStream.close();
        astParser.setKind(ASTParser.K_COMPILATION_UNIT);
        char[] text = new String(input).toCharArray();
        String sourcecode="";
        for (int i = 0; i < text.length; i++) {
            sourcecode+=text[i];
        }
        astParser.setSource(text);
        CompilationUnit cu = (CompilationUnit) astParser.createAST(null);
        List<Comment> commentLists = cu.getCommentList();
        List<TypeDeclaration> types = cu.types();

        if(types.size()>0){
            TypeDeclaration type = types.get(0);
            MethodDeclaration[] methodSet = type.getMethods();
            TypeDeclaration[] typeSet = type.getTypes();
            String strtemp = "";
            String result = "";
            for (int j = 0; j < commentLists.size(); j++) {
                if (commentLists.get(j) instanceof LineComment || commentLists.get(j) instanceof BlockComment){
                    int startPos = commentLists.get(j).getStartPosition();
                    int endPos = startPos + commentLists.get(j).getLength();
                    int commentstart = getLineNumber(file,startPos);
                    int commentend = getLineNumber(file,endPos);
                    if (j+1<commentLists.size()){
                        if(getLineNumber(file,commentLists.get(j).getStartPosition()) + 1==
                                getLineNumber(file,commentLists.get(j+1).getStartPosition())){
//                        System.out.println(j);
                            strtemp+=sourcecode.substring(startPos,endPos)+"\n";
                            continue;
                        }else {
                            if(strtemp.equals("")){
                                result = strtemp + sourcecode.substring(startPos,endPos);
                                strtemp = "";
                            }else result = sourcecode.substring(startPos,endPos);
                        }
                    }else {
                        if(strtemp.equals("")){
                            result = strtemp + sourcecode.substring(startPos,endPos);
                            strtemp = "";
                        }else result  = sourcecode.substring(startPos,endPos);
                    }
                    for (int i = 0; i < methodSet.length; i++){
                        int methodstart = getLineNumber(file,methodSet[i].getStartPosition());
                        int methodendpos = methodSet[i].getLength() + methodSet[i].getStartPosition();
                        int methodend = getLineNumber(file,methodendpos);
                        if(commentend + 1 == methodstart){
                            startline = commentend;
                            endline = methodend;
                            List<String> CC = new ArrayList<>();
                            CC.add(result);
                            CC.add(String.valueOf(startline));
                            CC.add(String.valueOf(endline));
                            CC.add("METHOD_COMMENT");
                            CCSet.add(CC);
                        }
                        if(commentend < methodend && commentend > methodstart){
                            startline = commentend;
                            if(j+1 < commentLists.size()){
                                Comment nextcomment = commentLists.get(j+1);
                                int nextcommentline = getLineNumber(file,nextcomment.getStartPosition());
                                if(nextcommentline < methodend){
                                    endline = nextcommentline-1;
                                    List<String> CC = new ArrayList<>();
                                    CC.add(result);
                                    CC.add(String.valueOf(startline));
                                    CC.add(String.valueOf(endline));
                                    CC.add("BLOCK_COMMENT");
                                    CCSet.add(CC);
//                                System.out.println(CC.getCm());
//                                System.out.println(CC.getStartLine());
//                                System.out.println(CC.getEndLine());
//                                System.out.println("2-----------------");
                                }else {
                                    endline = methodend-1;
                                    List<String> CC = new ArrayList<>();
                                    CC.add(result);
                                    CC.add(String.valueOf(startline));
                                    CC.add(String.valueOf(endline));
                                    CC.add("BLOCK_COMMENT");
                                    CCSet.add(CC);
//                                System.out.println(CC.getCm());
//                                System.out.println(CC.getStartLine());
//                                System.out.println(CC.getEndLine());
//                                System.out.println("3-----------------");
                                }
                            }else {
                                endline = methodend -1;
                                List<String> CC = new ArrayList<>();
                                CC.add(result);
                                CC.add(String.valueOf(startline));
                                CC.add(String.valueOf(endline));
                                CC.add("BLOCK_COMMENT");
                                CCSet.add(CC);

                            }
                        }
                    }

//                    for (int i = 0; i < typeSet.length; i++) {
//                        int typestart = getLineNumber(file, typeSet[i].getStartPosition());
//                        int typeend = getLineNumber(file, typeSet[i].getStartPosition() + typeSet[i].getLength());
//                        if (commentend < typeend && commentend > typestart) {
//                            startline = commentend;
//                            if (j + 1 < commentLists.size()) {
//                                Comment nextcomment = commentLists.get(j + 1);
//                                int nextcommentline = getLineNumber(file, nextcomment.getStartPosition());
//                                if (nextcommentline < typeend && endline > nextcommentline-1) {
//                                    endline = nextcommentline - 1;
//                                    List<String> CC = new ArrayList<>();
//                                    CC.add(result);
//                                    CC.add(String.valueOf(startline));
//                                    CC.add(String.valueOf(endline));
//                                    CCSet.add(CC);
//                                } else {
//                                    if(endline > typeend-1){
//                                        endline = typeend - 1;
//                                        List<String> CC = new ArrayList<>();
//                                        CC.add(result);
//                                        CC.add(String.valueOf(startline));
//                                        CC.add(String.valueOf(endline));
//                                        CCSet.add(CC);
//                                    }
//                                }
//                            }
//                            if(endline>typeend-1){
//                                endline = typeend -1;
//                                List<String> CC = new ArrayList<>();
//                                CC.add(result);
//                                CC.add(String.valueOf(startline));
//                                CC.add(String.valueOf(endline));
//                                CCSet.add(CC);
//
//                            }
//                        }
//                    }
                    //处理不在上述范围，即在class内，method外的方法
//                    if(endline == 0){
//                        startline = commentend;
//                        if(j+1 < commentLists.size()){
//                            Comment nextcomment = commentLists.get(j + 1);
//                            int nextcommentline = getLineNumber(file,nextcomment.getStartPosition());
//                            endline = nextcommentline - 1;
//                            List<String> CC = new ArrayList<>();
//                            CC.add(result);
//                            CC.add(String.valueOf(startline));
//                            CC.add(String.valueOf(endline));
//                            CCSet.add(CC);
//
//                        }else {
//                            endline = getLineNumber(file,cu.getStartPosition()+cu.getLength());
//                            List<String> CC = new ArrayList<>();
//                            CC.add(result);
//                            CC.add(String.valueOf(startline));
//                            CC.add(String.valueOf(endline));
//                            CCSet.add(CC);
//
//                        }
//                    }

                }

            }
        }
        //
        return CCSet;
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
//        return 200;
    }

}
