package com.myweb.elasticsearch.service.impl;

import org.apache.avro.Schema;
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

public class ServiceUtils {

    public static DataFileWriter<? extends Serializable> getDataFileWriter(Class<? extends Serializable> theClass, String fileName) {
        File file = new File(fileName);
        Schema schema = ReflectData.get().getSchema(theClass);
        DatumWriter<Serializable> datumWriter = new ReflectDatumWriter<Serializable>((Class<Serializable>) theClass);
        DataFileWriter<Serializable> out = null;
        try {
            out = new DataFileWriter<Serializable>(datumWriter).create(schema, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
