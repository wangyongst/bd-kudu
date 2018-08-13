package com.myweb.elasticsearch.service.impl;

import com.myweb.vo.Parameter;
import com.myweb.vo.Result;
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

    public static Result checkParameter(Parameter parameter) {
        Result result = new Result();
        if (parameter.getStartTimestamp() == null || parameter.getEndTimestamp() == null) {
            result.setStatus(2001);
            result.setMessage("Both of startTimestamp and endTimestamp can not be empty");
        }
        return result;
    }


    public static DataFileWriter<? extends Serializable> getDataFileWriter(Class<? extends Serializable> theClass, String fileName) throws IOException {
        File file = new File(fileName);
        Schema schema = ReflectData.get().getSchema(theClass);
        DatumWriter<Serializable> datumWriter = new ReflectDatumWriter<Serializable>((Class<Serializable>) theClass);
        DataFileWriter<Serializable> out = new DataFileWriter<Serializable>(datumWriter).create(schema, file);

        return out;
    }

    public static void closeWriter(
            DataFileWriter<? extends Serializable> dataFileWriter)
            throws IOException {
        if (dataFileWriter != null) {
            dataFileWriter.flush();
            dataFileWriter.close();
        }
    }

    public static void writeToAvro(DataFileWriter dataFileWriter, Object object, Class<? extends Serializable> theClass) throws IOException {
        synchronized (dataFileWriter) {
            dataFileWriter.append(theClass.cast(object));
            dataFileWriter.flush();
        }
    }

    public static DataFileReader<? extends Serializable> getDataFileReader(String fileName, Class<? extends Serializable> theClass) throws IOException {
        DatumReader<Serializable> userDatumReader = new ReflectDatumReader<Serializable>((Class<Serializable>) theClass);
        DataFileReader<Serializable> dataFileReader = new DataFileReader<Serializable>(new File(fileName), userDatumReader);
        return dataFileReader;
    }

    public static void closeDataFileReader(
            DataFileReader<? extends Serializable> dataFileReader) throws IOException {
        if (dataFileReader != null) {
            dataFileReader.close();
        }
    }
}
