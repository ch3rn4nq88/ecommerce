package mx.com.ecommerce.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto <T>{
    private String status;
    private T body;

    public ResponseDto(T data, String status){
        this.body=data;
        this.status=status;

    }

    public static <T> ResponseDto<T> forSuccess(T data){
        return new ResponseDto(data,"Success");
    }
}
