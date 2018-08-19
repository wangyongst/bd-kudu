package com.myweb.elasticsearch.service.impl;

import com.myweb.vo.Parameter;
import org.apache.avro.Schema;
import org.apache.avro.file.CodecFactory;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.reflect.ReflectData;
import org.apache.avro.reflect.ReflectDatumReader;
import org.apache.avro.reflect.ReflectDatumWriter;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServiceUtils {

    public static DataFileWriter<? extends Serializable> getDataFileWriter(Class<? extends Serializable> theClass, String fileName) {
        File file = new File(fileName);
        Schema schema = ReflectData.get().getSchema(theClass);
        DatumWriter<Serializable> datumWriter = new ReflectDatumWriter<Serializable>((Class<Serializable>) theClass);
        DataFileWriter<Serializable> out = null;
        try {
            if (!file.exists()) {
                out = new DataFileWriter<Serializable>(datumWriter).create(schema, file);
            } else out = new DataFileWriter<Serializable>(datumWriter).appendTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        out.setCodec(CodecFactory.snappyCodec());
        return out;
    }

    public static void closeWriter(
            DataFileWriter<? extends Serializable> dataFileWriter) {
        if (dataFileWriter != null) {
            try {
                dataFileWriter.flush();
                dataFileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static void writeToAvro(DataFileWriter dataFileWriter, Object object, Class<? extends Serializable> theClass) {
        synchronized (dataFileWriter) {
            try {
                dataFileWriter.append(theClass.cast(object));
                dataFileWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static DataFileReader<? extends Serializable> getDataFileReader(String fileName, Class<? extends Serializable> theClass) {
        DatumReader<Serializable> userDatumReader = new ReflectDatumReader<Serializable>((Class<Serializable>) theClass);
        DataFileReader<Serializable> dataFileReader = null;
        try {
            dataFileReader = new DataFileReader<Serializable>(new File(fileName), userDatumReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataFileReader;
    }

    public static void closeDataFileReader(
            DataFileReader<? extends Serializable> dataFileReader) {
        if (dataFileReader != null) {
            try {
                dataFileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String makePath(String path, Parameter parameter) {
        String subpath = String.valueOf((int) ((Long) parameter.getEndTimestamp() / (1000 * 60 * 60)));
        File file = new File(path + File.separator + subpath);
        if (!file.exists()) file.mkdir();
        return file.getAbsolutePath();
    }

    public static List<File> getFile(String path, Parameter parameter) {
        Integer start = (int) ((Long) parameter.getStartTimestamp() / (1000 * 60 * 60));
        if (start <= 426223) start = 426223;
        Integer now = (int) ((Long) new Date().getTime() / (1000 * 60 * 60));
        Integer end = (int) ((Long) parameter.getEndTimestamp() / (1000 * 60 * 60));
        if (end >= now) end = now;
        List<File> files = new ArrayList<>();
        for (int i = 0; start + i <= end; i++) {
            File file = new File(path + File.separator + String.valueOf(start + i));
            if (file.exists() && file.isDirectory()) {
                for (File f : file.listFiles()) {
                    String name = f.getName();
                    if (Long.parseLong(name.split(".")[2]) <= (Long) parameter.getStartTimestamp() && Long.parseLong(name.split(".")[3]) >= (Long) parameter.getStartTimestamp()) {
                        files.add(f);
                    } else if (Long.parseLong(name.split(".")[3]) >= (Long) parameter.getEndTimestamp() && Long.parseLong(name.split(".")[2]) <= (Long) parameter.getEndTimestamp()) {
                        files.add(f);
                    } else if (Long.parseLong(name.split(".")[3]) <= (Long) parameter.getEndTimestamp() && Long.parseLong(name.split(".")[2]) >= (Long) parameter.getStartTimestamp()) {
                        files.add(f);
                    }
                }
            }
        }
        return files;
    }
}
