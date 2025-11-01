package state;

import model.value.StringValue;
import model.value.Value;

import java.io.BufferedReader;

public interface FileTable {
    BufferedReader getFileDescriptor(Value key);
    void addFile(Value key, BufferedReader bufferedReader);
    void removeFile(Value key);
    Boolean fileExists(Value key);
}
