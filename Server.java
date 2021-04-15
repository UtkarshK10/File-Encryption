import java.io.BufferedInputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

class Server {
    private static final String secretkey = "!!!terceshhhh!!!";
    private static final String serverFolderPath = "D:\\client_files\\";

    public static void main(String[] args) throws Exception {
        int bytesRead;
        int current = 0;
        ServerSocket ss = new ServerSocket(9000);
        FileOutputStream output = null;
        while (true) {
            Socket s = null;
            s = ss.accept();
            try {
                DataInputStream dis = new DataInputStream(s.getInputStream());
                String fileName = dis.readUTF();
                String ext = dis.readUTF();
                String fname = fileName.split("\\.")[0];
                File file = new File(serverFolderPath + "\\" + fname + "_" + new Date().getTime() + ".encrpyted");
                File resFile = new File(serverFolderPath + "\\" + fname + "_" + new Date().getTime() + "." + ext);
                output = new FileOutputStream(file);
                long size = dis.readLong();
                byte[] b = new byte[1024];
                while (size > 0 && (bytesRead = dis.read(b, 0, (int) Math.min(b.length, size))) != -1) {
                    output.write(b, 0, bytesRead);
                    size -= bytesRead;
                }
                CryptoUtils.decrypt(secretkey, file, resFile);
                output.close();
                // file.delete();

            } catch (EOFException e) {

                continue;
            }
        }

    }
}
