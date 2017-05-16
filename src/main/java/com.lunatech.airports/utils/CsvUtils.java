package com.lunatech.airports.utils;


import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CsvUtils {

    public static List<Map<String, String>> readCsv(String fileName) throws IOException {
        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
        CsvMapper mapper = new CsvMapper();
        // mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
        File file = new ClassPathResource(fileName).getFile();
        MappingIterator<Map<String, String>> readValues = mapper.readerFor(Map.class).with(bootstrapSchema).readValues(file);
        return readValues.readAll();
    }

    public static long l(Map<String, String> map, String key) {
        return Long.valueOf(map.get(key));
    }

    public static String s(Map<String, String> map, String key) {
        return map.get(key);
    }

}
