package pl.edu.agh.mabics.platform.converters;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 01.12.12
 * Time: 16:42
 * To change this template use File | Settings | File Templates.
 */
public interface IConverter<T, S> {

    public T convert(S input);
}
