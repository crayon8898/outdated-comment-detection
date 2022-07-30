package cdUtil;

import ch.uzh.ifi.seal.changedistiller.ChangeDistiller;
import ch.uzh.ifi.seal.changedistiller.distilling.FileDistiller;
import ch.uzh.ifi.seal.changedistiller.model.entities.SourceCodeChange;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class cdUtils {
    public static void main(String[] args) throws IOException {
        System.out.println(1);
        String filename = "/Users/crayon/Desktop/代码注释一致性/论文/过时注释检测Automatic Detection of Outdated Comments During Code Changes/CommitData（含论文中项目）/ejbca/3728/new/src/CA.java";
        String newfile = filename;
        String oldfile = filename.replace("new","old");
        changedistiller(oldfile,newfile,3,20);
    }
    private static void changedistiller(String oldfile, String newfile,int startline,int endline) throws IOException {
        File file1 = new File(oldfile);
        File file2 = new File(newfile);
        FileDistiller distiller = ChangeDistiller.createFileDistiller(ChangeDistiller.Language.JAVA);
        try {
            distiller.extractClassifiedSourceCodeChanges(file1, file2);
        } catch(Exception e) {

            System.err.println("Warning: error while change distilling. " + e.getMessage());
        }
        List<SourceCodeChange> changes = distiller.getSourceCodeChanges();
        if(changes != null ) {
            for(SourceCodeChange change : changes) {
                System.out.println(change);
            }
            // see Javadocs for more information
        }
    }

}
