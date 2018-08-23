package com.myweb.avro;

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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AvroUtils {

    private static final Logger logger = LogManager.getLogger(AvroUtils.class);

    public static DataFileWriter<? extends Serializable> getDataFileWriter(Class<? extends Serializable> theClass, File file) {
        Schema schema = ReflectData.get().getSchema(theClass);
        DatumWriter<Serializable> datumWriter = new ReflectDatumWriter<Serializable>((Class<Serializable>) theClass);
        DataFileWriter<Serializable> out = new DataFileWriter<Serializable>(datumWriter);
        out.setCodec(CodecFactory.snappyCodec());
        try {

                out = out.create(schema, file);

        } catch (IOException e) {
           logger.error("GetDataFileWriter Error");
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
                logger.error("CloseWriter Error");
            }

        }
    }

    public static void writeToAvro(DataFileWriter dataFileWriter, Object object, Class<? extends Serializable> theClass) {
        synchronized (dataFileWriter) {
            try {
                dataFileWriter.append(theClass.cast(object));
                dataFileWriter.flush();
            } catch (IOException e) {
                logger.error("WriteToAvro Error");
            }
        }
    }

    public static DataFileReader<? extends Serializable> getDataFileReader(File file, Class<? extends Serializable> theClass) {
        DatumReader<Serializable> userDatumReader = new ReflectDatumReader<Serializable>((Class<Serializable>) theClass);
        DataFileReader<Serializable> dataFileReader = null;
        try {
            dataFileReader = new DataFileReader<Serializable>(file, userDatumReader);
        } catch (IOException e) {
            logger.error("GetDataFileReader Error");
        }
        return dataFileReader;
    }

    public static void closeDataFileReader(
            DataFileReader<? extends Serializable> dataFileReader) {
        if (dataFileReader != null) {
            try {
                dataFileReader.close();
            } catch (IOException e) {
                logger.error("CloseDataFileReader Error");
            }
        }
    }
}
