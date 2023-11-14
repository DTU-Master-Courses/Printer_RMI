import java.io.Serializable;

public class PrintJob implements Serializable {

    private static final long serialVersionUID = 1L;

    private int jobNumber;
    private String file;
    private String printer;

    public PrintJob(int jobNumber, String file, String printer) {
        this.jobNumber = jobNumber;
        this.file = file;
        this.printer = printer;
    }

    public int getJobNumber() {
        return jobNumber;
    }

    public String getFile() {
        return file;
    }

    public String getPrinter() {
        return printer;
    }
}
