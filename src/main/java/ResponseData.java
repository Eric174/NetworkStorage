import java.io.File;

public class ResponseData {
    private File file;
    private long length;
    private String fileName;

    public File getFile() {
        return file;
    }

    public long getLength() {
        return length;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
