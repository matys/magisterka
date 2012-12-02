package pl.edu.agh.mabics.platform.converters;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 01.12.12
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ConverterUtil {

   public  <T,S> List<T> convertList(List<S> inputs, IConverter<T,S> converter){
       List<T> output = new ArrayList<T>();
       for(S input : inputs){
           output.add(converter.convert(input));
       }
       return output;
   }
}
