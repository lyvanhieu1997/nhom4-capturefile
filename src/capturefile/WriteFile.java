/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package capturefile;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 *
 * @author hieu
 */
public class WriteFile {
    
    

    public void write_paths_to_file(ArrayList<String> paths, String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName);
        PrintWriter pw = new PrintWriter(writer);
        for (String str : paths) {
            pw.println(str);
        }
        writer.close();
    }

    public void create_file(String fileName) throws IOException {
        File f;
        f = new File(fileName);
        if (f.exists()) {
            f.delete();
        }
    }
    public void kt(){   //kiem tra xem file luu lai sau khi capture co ton tai chua
        File a = new File("D:\\copy\\log.raw");//duong dan kiem tra file
        if (a.exists()){
            a.delete();//neu ton tai thi xoa
        }
       
    }
    public void copyFile(File source, File dest) throws IOException {
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }
    
    public void copyToOneFile(File source, String path) throws FileNotFoundException, IOException{
        String sub_path = "D:\\copy\\log.raw";//duong dan luu file capture luu tat ca cac file quet duoc
        File dest = new File(sub_path);
        
        dest.getParentFile().mkdirs();
        
        FileInputStream input = null;
        FileOutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest, true);
            byte[] buf = new byte[1024];
            int bytesRead;
            output.write(path.getBytes());
            output.write("\n\n".getBytes());
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
            output.write("\n\n========================================\n\n".getBytes());
        } finally {
            input.close();
            output.close();
        }   
    }
    
    public ArrayList read_paths_from_file(String fileName) throws FileNotFoundException, IOException{
        ArrayList<String> paths = new ArrayList();
        File file = new File(fileName); 
        BufferedReader br = new BufferedReader(new FileReader(file)); 
        String st; 
        while ((st = br.readLine()) != null){
            paths.add(st);
        } 
        return paths;
    }
    // luu cac file quet duoc vao copy_File
    public void copyCat(String fileName) throws IOException{
        kt();
        ArrayList<String> paths = new ArrayList();
        String sub_path = "copy\\";
        new File(sub_path).mkdir();
        String[] nameOfFile;
        paths = read_paths_from_file(fileName);
        String regex = "\\\\";
        for(int i=0; i< paths.size(); i++){
            nameOfFile = paths.get(i).split(regex); 
            File fileSource = new File(paths.get(i));
            File fileDest = new File(sub_path + "copy_" + nameOfFile[nameOfFile.length-1]+".raw");// theo dang copy_filename.raw
            copyFile(fileSource, fileDest);
            copyToOneFile(fileSource, paths.get(i));
        }
    }
}