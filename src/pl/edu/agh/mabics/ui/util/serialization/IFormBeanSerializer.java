package pl.edu.agh.mabics.ui.util.serialization;

import pl.edu.agh.mabics.ui.datamodel.beans.FormBean;

/**
 * Created with IntelliJ IDEA.
 * User: Mateusz
 * Date: 27.08.13
 * Time: 20:51
 */
public interface IFormBeanSerializer {

    public void serialize(final FormBean form, String filePath);

    public FormBean deserialize(String filePath);
}
