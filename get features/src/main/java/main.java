import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
    static String PATH = "/Users/crayon/Data/研究生/论文写作/Data-driven Code-Comment Consistency Detection and Analysis/实验/实验代码/OnlyCommentChange";
    static String path = "/Users/crayon/Data/研究生/论文写作/Data-driven Code-Comment Consistency Detection and Analysis/实验/实验代码/OnlyCommentChange/OnlyCommentChange_RQ3";
    public static void main(String[] args) throws IOException {
        testexcel();
    }

    public static void testexcel() throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("features");

        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("ATTRIBUTE");
        cell = row.createCell(1);
        cell.setCellValue("METHOD_DECLARATION");
        cell = row.createCell(2);
        cell.setCellValue("METHOD_RENAMING");
        cell = row.createCell(3);
        cell.setCellValue("RETURN_TYPE");
        cell = row.createCell(4);
        cell.setCellValue("PARAMETER_DELETE");
        cell = row.createCell(5);
        cell.setCellValue("PARAMETER_INSERT");
        cell = row.createCell(6);
        cell.setCellValue("PARAMETER_RENAMING");
        cell = row.createCell(7);
        cell.setCellValue("PARAMETER_TYPE_CHANGE");
        cell = row.createCell(8);
        cell.setCellValue("RETURN");
        cell = row.createCell(9);
        cell.setCellValue("CODE_NUM");
        cell = row.createCell(10);
        cell.setCellValue("CHANGED_NUM");
        cell = row.createCell(11);
        cell.setCellValue("CHANGED_PER");
        cell = row.createCell(12);
        cell.setCellValue("IF_INSERT");
        cell = row.createCell(13);
        cell.setCellValue("IF_DELETE");
        cell = row.createCell(14);
        cell.setCellValue("IF_MOVE");
        cell = row.createCell(15);
        cell.setCellValue("IF_UPDATE");
        cell = row.createCell(16);
        cell.setCellValue("FOR_INSERT");
        cell = row.createCell(17);
        cell.setCellValue("FOR_DELETE");
        cell = row.createCell(18);
        cell.setCellValue("FOR_MOVE");
        cell = row.createCell(19);
        cell.setCellValue("FOR_UPDATE");
        cell = row.createCell(20);
        cell.setCellValue("FOREACH_INSERT");
        cell = row.createCell(21);
        cell.setCellValue("FOREACH_DELETE");
        cell = row.createCell(22);
        cell.setCellValue("FOREACH_MOVE");
        cell = row.createCell(23);
        cell.setCellValue("FOREACH_UPDATE");
        cell = row.createCell(24);
        cell.setCellValue("WHILE_INSERT");
        cell = row.createCell(25);
        cell.setCellValue("WHILE_DELETE");
        cell = row.createCell(26);
        cell.setCellValue("WHILE_MOVE");
        cell = row.createCell(27);
        cell.setCellValue("WHILE_UPDATE");
        cell = row.createCell(28);
        cell.setCellValue("CATCH_INSERT");
        cell = row.createCell(29);
        cell.setCellValue("CATCH_DELETE");
        cell = row.createCell(30);
        cell.setCellValue("CATCH_MOVE");
        cell = row.createCell(31);
        cell.setCellValue("CATCH_UPDATE");
        cell = row.createCell(32);
        cell.setCellValue("TRY_INSERT");
        cell = row.createCell(33);
        cell.setCellValue("TRY_DELETE");
        cell = row.createCell(34);
        cell.setCellValue("TRY_MOVE");
        cell = row.createCell(35);
        cell.setCellValue("TRY_UPDATE");
        cell = row.createCell(36);
        cell.setCellValue("THROW_INSERT");
        cell = row.createCell(37);
        cell.setCellValue("THROW_DELETE");
        cell = row.createCell(38);
        cell.setCellValue("THROW_MOVE");
        cell = row.createCell(39);
        cell.setCellValue("THROW_UPDATE");
        cell = row.createCell(40);
        cell.setCellValue("METHOD_INVOCATION_INSERT");
        cell = row.createCell(41);
        cell.setCellValue("METHOD_INVOCATION_DELETE");
        cell = row.createCell(42);
        cell.setCellValue("METHOD_INVOCATION_MOVE");
        cell = row.createCell(43);
        cell.setCellValue("METHOD_INVOCATION_UPDATE");
        cell = row.createCell(44);
        cell.setCellValue("ASSIGNMENT_INSERT");
        cell = row.createCell(45);
        cell.setCellValue("ASSIGNMENT_DELETE");
        cell = row.createCell(46);
        cell.setCellValue("ASSIGNMENT_MOVE");
        cell = row.createCell(47);
        cell.setCellValue("ASSIGNMENT_UPDATE");
        cell = row.createCell(48);
        cell.setCellValue("COMMENT_NUM");
        cell = row.createCell(49);
        cell.setCellValue("TODO/FIXME/XXX");
        cell = row.createCell(50);
        cell.setCellValue("BUG/VERSION");
        cell = row.createCell(51);
        cell.setCellValue("COMMENT_CODE_RATIO");
//        cell = row.createCell(52);
//        cell.setCellValue("IS_BODY");
//        cell = row.createCell(53);
//        cell.setCellValue("IS_DECLARATION");
        cell = row.createCell(52);
        cell.setCellValue("VARIABLE_DECLARATION_STATEMENT_INSERT");
        cell = row.createCell(53);
        cell.setCellValue("VARIABLE_DECLARATION_STATEMENT_DELETE");
        cell = row.createCell(54);
        cell.setCellValue("VARIABLE_DECLARATION_STATEMENT_MOVE");
        cell = row.createCell(55);
        cell.setCellValue("VARIABLE_DECLARATION_STATEMENT_UPDATE");
        cell = row.createCell(56);
        cell.setCellValue("ELSE_STATEMENT_INSERT");
        cell = row.createCell(57);
        cell.setCellValue("ELSE_STATEMENT_DELETE");
        cell = row.createCell(58);
        cell.setCellValue("ELSE_STATEMENT_MOVE");
        cell = row.createCell(59);
        cell.setCellValue("ELSE_STATEMENT_UPDATE");
        cell = row.createCell(60);
        cell.setCellValue("CHANGE_NUM");
        cell = row.createCell(61);
        cell.setCellValue("RESULT");

        cell = row.createCell(62);
        cell.setCellValue("ID");

        cell = row.createCell(63);
        cell.setCellValue("M/B_ID");

        cell = row.createCell(64);
        cell.setCellValue("NOUN_OF_COMMENT");
        cell = row.createCell(65);
        cell.setCellValue("VERB_OF_COMMENT");
        cell = row.createCell(66);
        cell.setCellValue("DETERMINER_OF_COMMENT");
        cell = row.createCell(67);
        cell.setCellValue("PREP_OR_CONJ_OF_COMMENT");
        cell = row.createCell(68);
        cell.setCellValue("ADJ_OF_COMMENT");
        cell = row.createCell(69);
        cell.setCellValue("ADV_OF_COMMENT");
        cell = row.createCell(70);
        cell.setCellValue("PRONOUN_OF_COMMENT");
        cell = row.createCell(71);
        cell.setCellValue("MD_OF_COMMENT");
        cell = row.createCell(72);
        cell.setCellValue("MK_OF_COMMENT");
        cell = row.createCell(73);
        cell.setCellValue("PT_OF_COMMENT");

        cell = row.createCell(74);
        cell.setCellValue("NOUN_OF_CODE");
        cell = row.createCell(75);
        cell.setCellValue("VERB_OF_CODE");
        cell = row.createCell(76);
        cell.setCellValue("DETERMINER_OF_CODE");
        cell = row.createCell(77);
        cell.setCellValue("PREP_OR_CONJ_OF_CODE");
        cell = row.createCell(78);
        cell.setCellValue("ADJ_OF_CODE");
        cell = row.createCell(79);
        cell.setCellValue("ADV_OF_CODE");
        cell = row.createCell(80);
        cell.setCellValue("PRONOUN_OF_CODE");
        cell = row.createCell(81);
        cell.setCellValue("MD_OF_CODE");
        cell = row.createCell(82);
        cell.setCellValue("MK_OF_CODE");
        cell = row.createCell(83);
        cell.setCellValue("PT_OF_CODE");

        cell = row.createCell(84);
        cell.setCellValue("GY_WORD_NUM");

        cell = row.createCell(85);
        cell.setCellValue("COSINE");

        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                row = sheet.createRow(i+1);
//                row.createCell(i).setCellValue();
                FileReader fr = new FileReader(tempList[i].toString());
                BufferedReader bf = new BufferedReader(fr);
                String str;
//                // 按行读取字符串
                String fengexian = "-----new----";
                String fengexian1 = "-----old----";
                int num_of_code = 0;
                Set<Integer> num_of_change = new HashSet<>();
                boolean arrive_fengexian = false;
                String change_case = "";
                StringBuffer comment = new StringBuffer();
                boolean arrive_code = false;
                int row_of_comment = 0;
                int change_num = 0;
                while ((str = bf.readLine()) != null) {
//                    System.out.println(str);
                    if(str.startsWith("change_entity_type:")){
                        if(!str.contains("COMMENT")){
                            change_num++;
                        }
                    }
//                    System.out.println(str);
                    //获取注释中的单词数
                    if(str.startsWith("1") ||
                            str.startsWith("2")||
                            str.startsWith("3")||
                            str.startsWith("4")||
                            str.startsWith("5")||
                            str.startsWith("6")||
                            str.startsWith("7")||
                            str.startsWith("8")||
                            str.startsWith("9")){
                        arrive_code = true;
                    }
                    if(!arrive_code){
                        row_of_comment++;
                        comment.append(str+'\n');
                    }
                    //获取结果resule
                    if(str.startsWith("change_type")||
                    str.startsWith("change_entity_label")||
                    str.startsWith("change_entity_type")){
//                        System.out.println(str);
                        if(str.contains("COMMENT")){
                            row.createCell(61).setCellValue(1);
                        }
                    }
                    if(str.startsWith("change:")){
//                        System.out.println(str);
                        String[] split = str.split(":");
                        change_case = split[1];
                    }
//                    System.out.println(str);
                    if(str.contains("change ") && str.contains(" : ") && str.contains(",")){
//                        System.out.println(str);
                        String[] split = str.split(",");
                        int endline = Integer.parseInt(split[1]);
                        String[] split1 = split[0].split(":");
                        String trim = split1[1].trim();
                        int startline = Integer.parseInt(trim);
                        for(int l = startline;l<=endline;l++){
                            num_of_change.add(l);
                        }
                    }
                    if(str.equals(fengexian) || str.equals(fengexian1)){
                        arrive_fengexian = true;
                    }
                    if(str.contains("return") && !arrive_fengexian){
                        row.createCell(8).setCellValue(1);
                    }
                    if(!arrive_fengexian && (str.startsWith("1") ||
                            str.startsWith("2")||
                            str.startsWith("3")||
                            str.startsWith("4")||
                            str.startsWith("5")||
                            str.startsWith("6")||
                            str.startsWith("7")||
                            str.startsWith("8")||
                            str.startsWith("9"))){
                        String temp = str.replaceAll("\\d+","");
                        if(!temp.equals("")) num_of_code++;
                    }
                    if(str.startsWith("BLOCK_ID") || str.startsWith("METHOD_ID")){
                        final String[] split = str.split(":");
                        int temp = Integer.parseInt(split[split.length-1]);
                        row.createCell(63).setCellValue(temp);
                    }
                    if(str.startsWith("CMT0")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(64).setCellValue(temp);
                    }
                    if(str.startsWith("CMT1")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(65).setCellValue(temp);
                    }
                    if(str.startsWith("CMT2")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(66).setCellValue(temp);
                    }
                    if(str.startsWith("CMT3")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(67).setCellValue(temp);
                    }
                    if(str.startsWith("CMT4")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(68).setCellValue(temp);
                    }
                    if(str.startsWith("CMT5")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(69).setCellValue(temp);
                    }
                    if(str.startsWith("CMT6")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(70).setCellValue(temp);
                    }
                    if(str.startsWith("CMT7")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(71).setCellValue(temp);
                    }
                    if(str.startsWith("CMT8")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(72).setCellValue(temp);
                    }
                    if(str.startsWith("CMT9")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(73).setCellValue(temp);
                    }
                    if(str.startsWith("CD0")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(74).setCellValue(temp);
                    }
                    if(str.startsWith("CD1")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(75).setCellValue(temp);
                    }
                    if(str.startsWith("CD2")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(76).setCellValue(temp);
                    }
                    if(str.startsWith("CD3")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(77).setCellValue(temp);
                    }
                    if(str.startsWith("CD4")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(78).setCellValue(temp);
                    }
                    if(str.startsWith("CD5")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(79).setCellValue(temp);
                    }
                    if(str.startsWith("CD6")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(80).setCellValue(temp);
                    }
                    if(str.startsWith("CD7")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(81).setCellValue(temp);
                    }
                    if(str.startsWith("CD8")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(82).setCellValue(temp);
                    }
                    if(str.startsWith("CD9")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(83).setCellValue(temp);
                    }

                    if(str.startsWith("GY")){
                        final String[] split = str.split(":");
                        int temp = Integer.parseInt(split[split.length-1]);
                        row.createCell(84).setCellValue(temp);
                    }

                    if(str.startsWith("COSINE")){
                        final String[] split = str.split(":");
                        float temp = Float.parseFloat(split[split.length-1]);
                        row.createCell(85).setCellValue(temp);
                    }

                    if(str.startsWith("TOTAL_ID")){
                        final String[] split = str.split(":");
                        int temp = Integer.parseInt(split[split.length-1]);
                        row.createCell(62).setCellValue(temp);
                    }
                    if(str.startsWith("change_type:")){
                        if(str.contains("ATTRIBUTE")){
                            row.createCell(0).setCellValue(1);
                        }
                        if(str.contains("METHOD_RENAMING")){
                            row.createCell(2).setCellValue(1);
                        }
                        if(str.contains("RETURN_TYPE")){
                            row.createCell(3).setCellValue(1);
                        }
                        if(str.contains("PARAMETER_DELETE")){
                            row.createCell(4).setCellValue(1);
                        }
                        if(str.contains("PARAMETER_INSERT")){
                            row.createCell(5).setCellValue(1);
                        }
                        if(str.contains("PARAMETER_RENAMING")){
                            row.createCell(6).setCellValue(1);
                        }
                        if(str.contains("PARAMETER_TYPE_CHANGE")){
                            row.createCell(7).setCellValue(1);
                        }
                    }
                    if(str.startsWith("change_entity_")){
                        if(str.contains("METHOD_DECLARATION")){
                            row.createCell(1).setCellValue(1);
                        }
                    }
                    if(str.startsWith("change_entity_type")){
                        if(change_case.equals("Insert") && str.contains("IF_STATEMENT")){
                            row.createCell(12).setCellValue(1);
                        }
                        if(change_case.equals("Delete") && str.contains("IF_STATEMENT")){
                            row.createCell(13).setCellValue(1);
                        }
                        if(change_case.equals("Move") && str.contains("IF_STATEMENT")){
                            row.createCell(14).setCellValue(1);
                        }
                        if(change_case.equals("Update") && str.contains("IF_STATEMENT")){
                            row.createCell(15).setCellValue(1);
                        }
                        if(change_case.equals("Insert") && str.contains("FOR_STATEMENT")){
                            row.createCell(16).setCellValue(1);
                        }
                        if(change_case.equals("Delete") && str.contains("FOR_STATEMENT")){
                            row.createCell(17).setCellValue(1);
                        }
                        if(change_case.equals("Move") && str.contains("FOR_STATEMENT")){
                            row.createCell(18).setCellValue(1);
                        }
                        if(change_case.equals("Update") && str.contains("FOR_STATEMENT")){
                            row.createCell(19).setCellValue(1);
                        }
                        if(change_case.equals("Insert") && str.contains("FOREACH_STATEMENT")){
                            row.createCell(20).setCellValue(1);
                        }
                        if(change_case.equals("Delete") && str.contains("FOREACH_STATEMENT")){
                            row.createCell(21).setCellValue(1);
                        }
                        if(change_case.equals("Move") && str.contains("FOREACH_STATEMENT")){
                            row.createCell(22).setCellValue(1);
                        }
                        if(change_case.equals("Update") && str.contains("FOREACH_STATEMENT")){
                            row.createCell(23).setCellValue(1);
                        }
                        if(change_case.equals("Insert") && str.contains("WHILE_STATEMENT")){
                            row.createCell(24).setCellValue(1);
                        }
                        if(change_case.equals("Delete") && str.contains("WHILE_STATEMENT")){
                            row.createCell(25).setCellValue(1);
                        }
                        if(change_case.equals("Move") && str.contains("WHILE_STATEMENT")){
                            row.createCell(26).setCellValue(1);
                        }
                        if(change_case.equals("Update") && str.contains("WHILE_STATEMENT")){
                            row.createCell(27).setCellValue(1);
                        }
                        if(change_case.equals("Insert") && str.contains("CATCH_CLAUSE")){
                            row.createCell(28).setCellValue(1);
                        }
                        if(change_case.equals("Delete") && str.contains("CATCH_CLAUSE")){
                            row.createCell(29).setCellValue(1);
                        }
                        if(change_case.equals("Move") && str.contains("CATCH_CLAUSE")){
                            row.createCell(30).setCellValue(1);
                        }
                        if(change_case.equals("Update") && str.contains("CATCH_CLAUSE")){
                            row.createCell(31).setCellValue(1);
                        }
                        if(change_case.equals("Insert") && str.contains("TRY_STATEMENT")){
                            row.createCell(32).setCellValue(1);
                        }
                        if(change_case.equals("Delete") && str.contains("TRY_STATEMENT")){
                            row.createCell(33).setCellValue(1);
                        }
                        if(change_case.equals("Move") && str.contains("TRY_STATEMENT")){
                            row.createCell(34).setCellValue(1);
                        }
                        if(change_case.equals("Update") && str.contains("TRY_STATEMENT")){
                            row.createCell(35).setCellValue(1);
                        }
                        if(change_case.equals("Insert") && str.contains("THROW_STATEMENT")){
                            row.createCell(36).setCellValue(1);
                        }
                        if(change_case.equals("Delete") && str.contains("THROW_STATEMENT")){
                            row.createCell(37).setCellValue(1);
                        }
                        if(change_case.equals("Move") && str.contains("THROW_STATEMENT")){
                            row.createCell(38).setCellValue(1);
                        }
                        if(change_case.equals("Update") && str.contains("THROW_STATEMENT")){
                            row.createCell(39).setCellValue(1);
                        }
                        if(change_case.equals("Insert") && str.contains("METHOD_INVOCATION")){
                            row.createCell(40).setCellValue(1);
                        }
                        if(change_case.equals("Delete") && str.contains("METHOD_INVOCATION")){
                            row.createCell(41).setCellValue(1);
                        }
                        if(change_case.equals("Move") && str.contains("METHOD_INVOCATION")){
                            row.createCell(42).setCellValue(1);
                        }
                        if(change_case.equals("Update") && str.contains("METHOD_INVOCATION")){
                            row.createCell(43).setCellValue(1);
                        }
                        if(change_case.equals("Insert") && str.contains("ASSIGNMENT")){
                            row.createCell(44).setCellValue(1);
                        }
                        if(change_case.equals("Delete") && str.contains("ASSIGNMENT")){
                            row.createCell(45).setCellValue(1);
                        }
                        if(change_case.equals("Move") && str.contains("ASSIGNMENT")){
                            row.createCell(46).setCellValue(1);
                        }
                        if(change_case.equals("Update") && str.contains("ASSIGNMENT")){
                            row.createCell(47).setCellValue(1);
                        }
                        if(change_case.equals("Insert") && str.contains("VARIABLE_DECLARATION_STATEMENT")){
                            row.createCell(52).setCellValue(1);
                        }
                        if(change_case.equals("Delete") && str.contains("VARIABLE_DECLARATION_STATEMENT")){
                            row.createCell(53).setCellValue(1);
                        }
                        if(change_case.equals("Move") && str.contains("VARIABLE_DECLARATION_STATEMENT")){
                            row.createCell(54).setCellValue(1);
                        }
                        if(change_case.equals("Update") && str.contains("VARIABLE_DECLARATION_STATEMENT")){
                            row.createCell(55).setCellValue(1);
                        }
                        if(change_case.equals("Insert") && str.contains("ELSE_STATEMENT")){
                            row.createCell(56).setCellValue(1);
                        }
                        if(change_case.equals("Delete") && str.contains("ELSE_STATEMENT")){
                            row.createCell(57).setCellValue(1);
                        }
                        if(change_case.equals("Move") && str.contains("ELSE_STATEMENT")){
                            row.createCell(58).setCellValue(1);
                        }
                        if(change_case.equals("Update") && str.contains("ELSE_STATEMENT")){
                            row.createCell(59).setCellValue(1);
                        }



                    }
                }
                row.createCell(9).setCellValue(num_of_code);
                row.createCell(10).setCellValue(num_of_change.size());
                Double change_ratio = Double.valueOf(String.format("%.2f", (double)num_of_change.size()/(double) num_of_code));
                row.createCell(11).setCellValue(change_ratio);

                int num_of_comment_word = 0;
                Pattern p = Pattern.compile("\\b[a-zA-Z]+\\b");
                Matcher m = p.matcher(comment.toString());

                while(m.find()){
                    if(m.group().toUpperCase(Locale.ROOT).contains("TODO")||
                    m.group().toUpperCase(Locale.ROOT).contains("FIXME")||
                    m.group().toUpperCase(Locale.ROOT).contains("XXX")){
//                        System.out.println(m.group());
                        row.createCell(49).setCellValue(1);
                    }
                    if(m.group().toUpperCase(Locale.ROOT).contains("BUG")||
                    m.group().toUpperCase(Locale.ROOT).contains("VERSION")){
                        row.createCell(50).setCellValue(1);
                    }
                    num_of_comment_word++;
                }
                row.createCell(48).setCellValue(num_of_comment_word);
                Double comment_code_ratio = Double.valueOf(String.format("%.2f",(double)row_of_comment/(double)num_of_code));
                row.createCell(51).setCellValue(comment_code_ratio);
                row.createCell(60).setCellValue(change_num);
//                bf.close();
//                fr.close();
            }
        }


        FileOutputStream fileOutputStream = new FileOutputStream(PATH + "/onlyComment.xlsx");
        workbook.write(fileOutputStream);
        fileOutputStream.close();
    }
}
