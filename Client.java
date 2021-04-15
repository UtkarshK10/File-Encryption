import java.io.BufferedInputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

class Client {
    private static final String secretkey = "!!!terceshhhh!!!";

    public static void main(String[] args) throws Exception {
        Socket s = new Socket("127.0.0.1", 9000);
        Console c = System.console();
        System.out.print("Enter absoulte path of the file.");
        String inputFile = c.readLine();
        try {

            File file = new File(inputFile);
            int idx = inputFile.lastIndexOf(".");
            String extension = null;
            if (idx >= 0) {
                extension = inputFile.substring(idx + 1);
            }
            String fname = file.getName().split("\\.")[0];
            File encryptedFile = new File("D:\\NIS\\" + fname + ".encrypted");
            CryptoUtils.encrypt(secretkey, file, encryptedFile);
            byte[] b = new byte[(int) encryptedFile.length()];
            FileInputStream fis = new FileInputStream(encryptedFile);
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);
            dis.readFully(b, 0, b.length);
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeUTF(encryptedFile.getName());
            dos.writeUTF(extension);
            dos.writeLong(b.length);
            dos.write(b, 0, b.length);
            dos.flush();
            fis.close();
            encryptedFile.delete();
            System.out.println("File uploaded successfully on ftp server");
            s.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }
}
