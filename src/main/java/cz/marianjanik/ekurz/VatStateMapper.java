package cz.marianjanik.ekurz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VatStateMapper {

    public VatStateMap mapToObject(String body) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        VatStateMap vatStateMap = objectMapper.readValue(body,VatStateMap.class);
        return vatStateMap;
    }
}
