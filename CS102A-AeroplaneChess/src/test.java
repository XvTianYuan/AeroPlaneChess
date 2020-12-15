import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;

public class test {
  public static void main(String[] args) throws IOException {
    JFileChooser fileChooser = new JFileChooser("Archive\\");

    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

    int returnVal = fileChooser.showOpenDialog(fileChooser);

    if (returnVal == JFileChooser.APPROVE_OPTION) {
      String filePath = fileChooser.getSelectedFile().getAbsolutePath();//这个就是你选择的文件夹的
      System.out.println(filePath);
    }
  }
}
