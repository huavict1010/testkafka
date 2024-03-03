import com.example.message.Message;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String message = "{    \"data\": [        {            \"dbTrafficId\": \"186370029\",            \"cluster\": \"jdbc:mysql//xxx\",             \"sql\": \"SELECT * FROM x_xxx_t\",             \"columns\": [                {                    \"databaseName\": \"damd\",                     \"tableName\": \"x_xxx_t\",                     \"fieldName\": \"id\"                 },                {                    \"databaseName\": \"dsp_xxx_t\",                    \"tableName\": \"api\",                    \"fieldName\": \"/damd/api/sss\"                }            ],            \"rows\": 1000,             \"errorMsg\": \"\"         }    ],    \"tenant_id\": \"00000000000000000000000000000000\",    \"sub_tenant_id\": \"\",    \"app_id\": \"app_000000035422\",    \"agent_id\": \"11892\"}";
        Message msg = reader(message);
        System.out.println(msg);
    }

    public static Message reader(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        try {
            Message msg = objectMapper.readValue(message, Message.class);
            return msg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
